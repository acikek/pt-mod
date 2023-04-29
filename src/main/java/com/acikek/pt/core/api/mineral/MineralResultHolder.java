package com.acikek.pt.core.api.mineral;

import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MineralResultHolder {

    @Nullable Item mineralResultItem();

    default boolean hasMineralResult() {
        return mineralResultItem() != null;
    }

    static <T extends MineralResultHolder> List<T> filter(List<T> holders) {
        return holders.stream()
                .filter(MineralResultHolder::hasMineralResult)
                .toList();
    }

    static <T extends MineralResultHolder> T filterAndGet(List<T> holders, World world) {
        var list = filter(holders);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(world.random.nextInt(list.size()));
    }
}
