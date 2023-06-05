package com.acikek.pt.core.api.signature;

import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.world.World;

import java.util.List;

public interface CompoundSignature {

    List<SignatureComponent> components();

    List<SignatureEntry> all();

    default List<SignatureEntry> elements(World world) {
        return components().stream()
                .flatMap(result -> result.get(world).stream())
                .toList();
    }

    default List<ItemStack> stacks(World world) {
        return elements(world).stream()
                .map(entry -> entry.getResultStack(world))
                .toList();
    }

    List<SignatureEntry> primaries();

    List<SignatureEntry> units();

    MutableText text();
}
