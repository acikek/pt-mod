package com.acikek.pt.core.refined;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface RefinedStateType {

    String getBlockName(String elementName);

    String getItemName(String elementName);

    String getMiniItemName(String elementName);

    Block createBlock(@Nullable Float blockStrength);

    void buildBlockModel(BlockStateModelGenerator generator, Block block);

    void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Block block);
}
