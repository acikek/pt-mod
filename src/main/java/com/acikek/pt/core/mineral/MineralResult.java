package com.acikek.pt.core.mineral;

import com.acikek.pt.core.element.Element;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public interface MineralResult {

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

    Text displayText();

    default int sort(MineralResult other) {
        return 0;
    }
}
