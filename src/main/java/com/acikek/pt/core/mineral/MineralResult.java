package com.acikek.pt.core.mineral;

import com.acikek.pt.core.element.Element;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface MineralResult {

    record Pair(Element element, int amount) {
        public ItemStack toResultStack(World world) {
            return element.getMineralResultItem(world).getDefaultStack().copyWithCount(amount);
        }
    }

    Pair get(World world);
}
