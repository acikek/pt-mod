package com.acikek.pt.core.api.refined;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
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

public interface ElementRefinedState extends DatagenDelegator {

    @NotNull RefinedStateType getType();

    @NotNull Item refinedItem();

    @NotNull Item miniRefinedItem();

    @NotNull Block refinedBlock();

    Fluid refinedFluid();

    default boolean hasRefinedFluid() {
        return refinedFluid() != null;
    }

    @Override
    default void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        if (hasRefinedFluid()) {
            builder.add(refinedFluid().getDefaultState().getBlockState().getBlock(), parent.display().englishName());
        }
        builder.add(refinedBlock(), parent.getRefinedBlockName());
        builder.add(refinedItem(), parent.getRefinedItemName());
        builder.add(miniRefinedItem(), parent.getMiniRefinedItemName());
    }

    @Override
    default void buildBlockModels(BlockStateModelGenerator generator, Element parent) {
        getType().buildRefinedBlockModel(generator, parent, refinedBlock());
    }

    @Override
    default void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        getType().buildRefinedBlockTags(provider, parent, refinedBlock());
        provider.apply(parent.getRefinedBlockTag()).add(refinedBlock());
    }

    @Override
    default void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent) {
        getType().buildItemTags(provider, parent, this);
        provider.apply(parent.getRefinedItemTag()).add(refinedItem());
        provider.apply(parent.getMiniRefinedItemTag()).add(miniRefinedItem());
    }

    @Override
    default void buildFluidTags(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, Element parent) {
        if (hasRefinedFluid()) {
            provider.apply(parent.getRefinedFluidTag()).add(refinedFluid());
        }
    }

    default void register(PTRegistry registry, ElementIds<String> ids) {
        registry.registerItem(ids.getItemId(), refinedItem());
        registry.registerItem(ids.getMiniItemId(), miniRefinedItem());
        registry.registerBlock(ids.getBlockId(), refinedBlock());
        if (hasRefinedFluid()) {
            registry.registerFluid(ids.getFluidId(), refinedFluid());
        }
    }

    @Override
    default List<Block> getBlocks() {
        return Collections.singletonList(refinedBlock());
    }

    @Override
    default List<Item> getItems() {
        return List.of(refinedItem(), miniRefinedItem());
    }
}
