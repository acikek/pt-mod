package com.acikek.pt.core.mineral;

import com.acikek.pt.core.lang.MineralNaming;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public interface Mineral {

    MineralNaming naming();

    void init();

    List<MineralResult> results();

    default List<MineralResult.Entry> getResultElements(World world) {
        return results().stream()
                .flatMap(result -> result.get(world).stream())
                .toList();
    }

    default List<ItemStack> getResultStacks(World world) {
        return getResultElements(world).stream()
                .map(entry -> entry.getResultStack(world))
                .toList();
    }
}
