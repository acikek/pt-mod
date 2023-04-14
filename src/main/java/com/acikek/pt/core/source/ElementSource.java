package com.acikek.pt.core.source;

import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.ElementRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ElementSource {

    enum Type {
        ORE,
        MINERAL,
        ATMOSPHERE
    }

    Type getType();

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

    default void register(ElementRegistry registry, ElementIds<String> ids) {
        if (hasSourceBlock()) {
            registry.registerBlock(ids.getSourceBlockId(), sourceBlock());
        }
        if (hasDeepslateSourceBlock()) {
            registry.registerBlock(ids.getDeepslateSourceBlockId(), deepslateSourceBlock());
        }
        if (hasClusterSourceBlock()) {
            registry.registerBlock(ids.getClusterSourceBlockId(), clusterSourceBlock());
        }
        if (hasRawSourceItem()) {
            registry.registerItem(ids.getRawSourceItemId(), rawSourceItem());
        }
        if (hasRawSourceBlock()) {
            registry.registerBlock(ids.getRawSourceBlockId(), rawSourceBlock());
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
