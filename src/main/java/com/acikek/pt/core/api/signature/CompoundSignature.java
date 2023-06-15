package com.acikek.pt.core.api.signature;

import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.world.World;

import java.util.List;

public interface CompoundSignature {

    List<SignatureComponent> components();

    List<SignatureEntry> all();

    default List<SignatureEntry> getResults(World world) {
        return components().stream()
                .flatMap(result -> result.get(world).stream())
                .toList();
    }

    List<SignatureEntry> primaries();

    List<SignatureEntry> units();

    MutableText text();
}
