package com.acikek.pt.core.mineral;

import com.acikek.pt.core.impl.mineral.MineralBlock;
import com.acikek.pt.core.signature.ElementSignature;
import com.acikek.pt.core.lang.MineralNaming;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import java.util.List;
import java.util.function.Supplier;

public class Minerals {

    private static final AbstractBlock.Settings MINERAL_SETTINGS = FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .sounds(BlockSoundGroup.TUFF)
            .strength(3.0f);

    public static MineralBlock block(Object naming, Supplier<List<ElementSignature>> supplier) {
        return new MineralBlock(MINERAL_SETTINGS, MineralNaming.forObject(naming), supplier);
    }
}
