package com.acikek.pt.core.api.mineral;

import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

public interface MineralResultHolder {

    @Nullable Item mineralResultItem();

    default boolean hasMineralResult() {
        return mineralResultItem() != null;
    }
}
