package com.acikek.pt.core.refined;

import net.minecraft.block.AbstractBlock;

public interface RefinedStateType {

    String getBlockName(String elementName);

    String getItemName(String elementName);

    String getMiniItemName(String elementName);

    AbstractBlock.Settings getBlockSettings(float strengthInput);
}
