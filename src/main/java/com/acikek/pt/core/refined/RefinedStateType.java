package com.acikek.pt.core.refined;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import org.jetbrains.annotations.Nullable;

public interface RefinedStateType {

    String getBlockName(String elementName);

    String getItemName(String elementName);

    String getMiniItemName(String elementName);

    Block createBlock(@Nullable Float blockStrength);

    void buildBlockModel(BlockStateModelGenerator generator, Block block);
}
