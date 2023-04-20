package com.acikek.pt.core.impl.signature;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.core.signature.ElementSignature;
import com.acikek.pt.core.signature.ElementSignatureEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class ElementSignatureImpls {

    public record Unit(ElementSignatureEntry entry) implements ElementSignature {

        @Override
        public List<ElementSignatureEntry> all() {
            return Collections.singletonList(entry);
        }

        @Override
        public List<ElementSignatureEntry> get(World world) {
            return all();
        }

        @Override
        public Text getDisplayText() {
            return entry.getQuantifiedText();
        }
    }

    public record Random(List<ElementSignatureEntry> entries, int amount) implements ElementSignature {

        @Override
        public List<ElementSignatureEntry> all() {
            return entries.stream()
                    .map(entry -> entry.withAmount(amount))
                    .toList();
        }

        @Override
        public List<ElementSignatureEntry> get(World world) {
            var all = all();
            int choice = world.random.nextBetween(0, all.size() - 1);
            return Collections.singletonList(all.get(choice));
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

    public record Wrapped(List<ElementSignatureEntry> entries, int multiplier) implements ElementSignature {

        @Override
        public List<ElementSignatureEntry> all() {
            return entries.stream()
                    .map(entry -> entry.withAmount(amt -> amt * multiplier))
                    .toList();
        }

        @Override
        public List<ElementSignatureEntry> get(World world) {
            return all();
        }

        @Override
        public Text getDisplayText() {
            MutableText result = Text.literal("(");
            for (ElementSignatureEntry entry : entries) {
                result.append(entry.getQuantifiedText());
            }
            return result.append(")" + PTApi.subscriptChecked(multiplier));
        }
    }

    public record Hydration(int amount) implements ElementSignature {

        private ElementSignatureEntry hydrogen() {
            return new ElementSignatureEntry(PeriodicTable.HYDROGEN, amount * 2);
        }

        private ElementSignatureEntry oxygen() {
            return new ElementSignatureEntry(PeriodicTable.OXYGEN, amount);
        }

        @Override
        public List<ElementSignatureEntry> all() {
            return List.of(hydrogen(), oxygen());
        }

        @Override
        public List<ElementSignatureEntry> get(World world) {
            return all();
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
        public int sort(ElementSignature other) {
            return 1;
        }
    }
}
