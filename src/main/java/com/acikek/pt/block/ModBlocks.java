package com.acikek.pt.block;

import com.acikek.pt.PT;
import com.acikek.pt.core.refined.RefinedStateTypes;
import net.minecraft.block.Block;

public class ModBlocks {

    public static final Block EMPTY_SACK = new Block(RefinedStateTypes.POWDER.getBaseSettings());

    public static void register() {
        PT.REGISTRY.registerBlock("empty_sack", EMPTY_SACK);
    }
}
