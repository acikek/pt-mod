package com.acikek.pt.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class ModSoundGroups {

    public static final BlockSoundGroup FLUID_BASIN = new BlockSoundGroup(
            1.0f, 1.0f,
            ModSoundEvents.FLUID_BASIN_BROKEN,
            SoundEvents.BLOCK_METAL_STEP,
            ModSoundEvents.FLUID_BASIN_PLACED,
            SoundEvents.BLOCK_METAL_HIT,
            SoundEvents.BLOCK_METAL_FALL
    );
}
