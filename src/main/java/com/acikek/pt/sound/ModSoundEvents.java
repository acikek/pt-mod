package com.acikek.pt.sound;

import com.acikek.pt.PT;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class ModSoundEvents {

    private static final List<SoundEvent> sounds = new ArrayList<>();

    public static final SoundEvent FLUID_BASIN_PLACED = create("block.fluid_basin_placed");
    public static final SoundEvent FLUID_BASIN_BROKEN = create("block.fluid_basin_broken");

    public static SoundEvent create(String name) {
        SoundEvent event = SoundEvent.of(PT.id(name));
        sounds.add(event);
        return event;
    }

    public static void register() {
        for (SoundEvent event : sounds) {
            Registry.register(Registries.SOUND_EVENT, event.getId(), event);
        }
    }
}
