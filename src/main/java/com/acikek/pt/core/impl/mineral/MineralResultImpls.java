package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.mineral.MineralResult;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class MineralResultImpls {

    public record Single(Entry entry) implements MineralResult {

        @Override
        public List<Entry> get(World world) {
            return Collections.singletonList(entry);
        }

        @Override
        public Text displayText() {
            return entry.getQuantifiedText();
        }
    }

    public record Random(List<Element> elements, int amount) implements MineralResult {

        @Override
        public List<Entry> get(World world) {
            int choice = world.random.nextBetween(0, elements.size() - 1);
            Entry entry = new Entry(elements.get(choice), amount);
            return Collections.singletonList(entry);
        }

        @Override
        public Text displayText() {
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

    public record Wrapped(List<Entry> entries, int multiplier) implements MineralResult {

        @Override
        public List<Entry> get(World world) {
            return entries.stream()
                    .map(entry -> entry.withAmount(entry.amount() * multiplier))
                    .toList();
        }

        @Override
        public Text displayText() {
            MutableText result = Text.literal("(");
            for (Entry entry : entries) {
                result.append(entry.getQuantifiedText());
            }
            return result.append(")" + PTApi.subscriptChecked(multiplier));
        }
    }

    public record Hydration(int amount) implements MineralResult {

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
        public Text displayText() {
            String amtStr = amount > 1
                    ? String.valueOf(amount)
                    : "";
            return Text.literal("·" + amtStr)
                    .append(hydrogen().element().getQuantifiedText(2))
                    .append(oxygen().element().getSymbolText());
        }

        @Override
        public int sort(MineralResult other) {
            return 1;
        }
    }
}
