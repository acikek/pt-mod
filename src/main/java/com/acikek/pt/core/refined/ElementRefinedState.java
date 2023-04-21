package com.acikek.pt.core.refined;

import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.ElementRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public interface ElementRefinedState {

    @NotNull RefinedStateType getType();

    @NotNull Item refinedItem();

    @NotNull Item miniRefinedItem();

    @NotNull Block refinedBlock();

    Fluid refinedFluid();

    default boolean hasRefinedFluid() {
        return refinedFluid() != null;
    }

    default void buildBlockModel(BlockStateModelGenerator generator) {
        getType().buildBlockModel(generator, refinedBlock());
    }

    default void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Block block) {
        getType().buildBlockTags(provider, block);
    }

    default void register(ElementRegistry registry, ElementIds<String> ids) {
        registry.registerItem(ids.getItemId(), refinedItem());
        registry.registerItem(ids.getMiniItemId(), miniRefinedItem());
        registry.registerBlock(ids.getBlockId(), refinedBlock());
        if (hasRefinedFluid()) {
            registry.registerFluid(ids.getFluidId(), refinedFluid());
        }
    }

    default List<Block> getBlocks() {
        return Collections.singletonList(refinedBlock());
    }

    default List<Item> getItems() {
        return List.of(refinedItem(), miniRefinedItem());
    }
}
