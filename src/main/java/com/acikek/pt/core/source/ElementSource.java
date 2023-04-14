package com.acikek.pt.core.source;

import com.acikek.pt.PT;
import com.acikek.pt.core.id.ElementIds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ElementSource {

    Block sourceBlock();

    default boolean hasSourceBlock() {
        return sourceBlock() != null;
    }

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

    Range<Integer> atmosphericRange();

    default boolean hasAtmosphericSource() {
        return atmosphericRange() != null;
    }

    default void register(ElementIds<String> ids) {
        if (hasSourceBlock()) {
            PT.registerBlock(ids.getSourceBlockId(), sourceBlock());
        }
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

    default List<Block> getBlocks() {
        List<Block> result = new ArrayList<>();
        if (hasSourceBlock()) {
            result.add(sourceBlock());
        }
        if (hasDeepslateSourceBlock()) {
            result.add(deepslateSourceBlock());
        }
        if (hasClusterSourceBlock()) {
            result.add(clusterSourceBlock());
        }
        if (hasRawSourceBlock()) {
            result.add(rawSourceBlock());
        }
        return result;
    }

    default List<Item> getItems() {
        return hasRawSourceItem()
                ? Collections.singletonList(rawSourceItem())
                : Collections.emptyList();
    }
}
