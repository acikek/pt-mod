package com.acikek.pt.core.source;

import com.acikek.pt.PT;
import com.acikek.pt.core.id.ElementIds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

public interface ElementSource {

    @NotNull Block sourceBlock();

    Block deepslateSourceBlock();

    default boolean hasDeepslateSourceBlock() {
        return deepslateSourceBlock() != null;
    }

    Block clusterSourceBlock();

    default boolean hasClusterSourceBlock() {
        return clusterSourceBlock() != null;
    }

    Item rawSourceItem();

    default boolean hasRawSourceItem() {
        return rawSourceItem() != null;
    }

    Block rawSourceBlock();

    default boolean hasRawSourceBlock() {
        return rawSourceBlock() != null;
    }

    default void register(ElementIds<String> ids) {
        PT.registerBlock(ids.getSourceBlockId(), sourceBlock());
        if (hasDeepslateSourceBlock()) {
            PT.registerBlock(ids.getDeepslateSourceBlockId(), deepslateSourceBlock());
        }
        if (hasClusterSourceBlock()) {
            PT.registerBlock(ids.getClusterSourceBlockId(), clusterSourceBlock());
        }
        if (hasRawSourceItem()) {
            PT.registerItem(ids.getRawSourceItemId(), rawSourceItem());
        }
        if (hasRawSourceBlock()) {
            PT.registerBlock(ids.getRawSourceBlockId(), rawSourceBlock());
        }
    }
}
