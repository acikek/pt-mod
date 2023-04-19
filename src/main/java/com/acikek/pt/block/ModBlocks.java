package com.acikek.pt.block;

import com.acikek.pt.PT;
import com.acikek.pt.core.refined.RefinedStateTypes;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {

    public static final Block EMPTY_SACK = new Block(RefinedStateTypes.POWDER.getBaseSettings().sounds(BlockSoundGroup.WOOL));

    public static void register() {
        PT.REGISTRY.registerBlock("empty_sack", EMPTY_SACK);
    }
}
