package com.acikek.pt.core.element;

import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public interface ElementSignature {

    record Entry(Element element, int amount) {

        public Entry withAmount(int newAmount) {
            return new Entry(element, newAmount);
        }

        public ItemStack getResultStack(World world) {
            return element.getMineralResultItem(world).getDefaultStack().copyWithCount(amount);
        }

        public Text getQuantifiedText() {
            return element.getQuantifiedText(amount);
        }
    }

    List<Entry> get(World world);

    Text getDisplayText();

    default int sort(ElementSignature other) {
        return 0;
    }

    static Text createTooltip(List<ElementSignature> signatures) {
        var sorted = signatures.stream()
                .sorted(ElementSignature::sort)
                .toList();
        MutableText result = Text.empty();
        for (ElementSignature entry : sorted) {
            result.append(entry.getDisplayText());
        }
        return result;
    }
}
