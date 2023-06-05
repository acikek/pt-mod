package com.acikek.pt.core.impl.signature;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.signature.SignatureComponent;
import com.acikek.pt.core.api.signature.SignatureEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class ElementSignatureImpls {

    public record Unit(SignatureEntry entry) implements SignatureComponent {

        @Override
        public List<SignatureEntry> all() {
            return Collections.singletonList(entry);
        }

        @Override
        public List<SignatureEntry> get(World world) {
            return all();
        }

        @Override
        public SignatureEntry getUnit() {
            return entry;
        }

        @Override
        public Text getDisplayText() {
            return entry.getQuantifiedText();
        }
    }

    public record Random(List<SignatureEntry> entries, int amount) implements SignatureComponent {

        @Override
        public List<SignatureEntry> all() {
            return entries.stream()
                    .map(entry -> entry.withAmount(amount))
                    .toList();
        }

        @Override
        public List<SignatureEntry> get(World world) {
            var all = all();
            int choice = world.random.nextBetween(0, all.size() - 1);
            return Collections.singletonList(all.get(choice));
        }

        @Override
        public SignatureEntry getUnit() {
            return null;
        }

        @Override
        public Text getDisplayText() {
            MutableText result = Text.literal("(");
            for (int i = 0; i < entries.size(); i++) {
                result.append(entries.get(i).getQuantifiedText());
                if (i < entries.size() - 1) {
                    result.append(", ");
                }
            }
            return result.append(")" + PTApi.subscript(amount));
        }
    }

    public record Wrapped(List<SignatureComponent> signatures, int multiplier) implements SignatureComponent {

        @Override
        public List<SignatureEntry> all() {
            return signatures.stream()
                    .flatMap(sig -> sig.all().stream())
                    .map(entry -> entry.withAmount(amt -> amt * multiplier))
                    .toList();
        }

        @Override
        public List<SignatureEntry> get(World world) {
            return all();
        }

        @Override
        public SignatureEntry getUnit() {
            return null;
        }

        @Override
        public Text getDisplayText() {
            MutableText result = Text.literal("(");
            for (SignatureComponent signature : signatures) {
                result.append(signature.getDisplayText());
            }
            return result.append(")" + PTApi.subscriptChecked(multiplier));
        }
    }

    public record Hydration(int amount) implements SignatureComponent {

        private SignatureEntry hydrogen() {
            return new SignatureEntry(PeriodicTable.HYDROGEN, amount * 2);
        }

        private SignatureEntry oxygen() {
            return new SignatureEntry(PeriodicTable.OXYGEN, amount);
        }

        @Override
        public List<SignatureEntry> all() {
            return List.of(hydrogen(), oxygen());
        }

        @Override
        public List<SignatureEntry> get(World world) {
            return all();
        }

        @Override
        public SignatureEntry getUnit() {
            return null;
        }

        @Override
        public Text getDisplayText() {
            String amtStr = amount > 1
                    ? String.valueOf(amount)
                    : "";
            return Text.literal("·" + amtStr)
                    .append(hydrogen().element().getQuantifiedText(2))
                    .append(oxygen().element().getSymbolText());
        }

        @Override
        public int sort(SignatureComponent other) {
            return 1;
        }
    }
}
