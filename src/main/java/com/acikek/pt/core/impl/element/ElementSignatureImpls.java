package com.acikek.pt.core.impl.element;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.ElementSignature;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class ElementSignatureImpls {

    public record Single(Entry entry) implements ElementSignature {

        @Override
        public List<Entry> get(World world) {
            return Collections.singletonList(entry);
        }

        @Override
        public Text getDisplayText() {
            return entry.getQuantifiedText();
        }
    }

    public record Random(List<Element> elements, int amount) implements ElementSignature {

        @Override
        public List<Entry> get(World world) {
            int choice = world.random.nextBetween(0, elements.size() - 1);
            Entry entry = new Entry(elements.get(choice), amount);
            return Collections.singletonList(entry);
        }

        @Override
        public Text getDisplayText() {
            MutableText result = Text.literal("(");
            for (int i = 0; i < elements.size(); i++) {
                result.append(elements.get(i).getSymbolText());
                if (i < elements.size() - 1) {
                    result.append(", ");
                }
            }
            return result.append(")" + PTApi.subscript(amount));
        }
    }

    public record Wrapped(List<Entry> entries, int multiplier) implements ElementSignature {

        @Override
        public List<Entry> get(World world) {
            return entries.stream()
                    .map(entry -> entry.withAmount(entry.amount() * multiplier))
                    .toList();
        }

        @Override
        public Text getDisplayText() {
            MutableText result = Text.literal("(");
            for (Entry entry : entries) {
                result.append(entry.getQuantifiedText());
            }
            return result.append(")" + PTApi.subscriptChecked(multiplier));
        }
    }

    public record Hydration(int amount) implements ElementSignature {

        private Entry hydrogen() {
            return new Entry(PeriodicTable.HYDROGEN, amount * 2);
        }

        private Entry oxygen() {
            return new Entry(PeriodicTable.OXYGEN, amount);
        }

        @Override
        public List<Entry> get(World world) {
            return List.of(hydrogen(), oxygen());
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
