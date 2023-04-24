package com.acikek.pt.core.lang;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public interface DisplayHolder<T extends CompoundDisplay> {

    @NotNull T display();

    default String id() {
        return display().id();
    }

    default Identifier getConventionalId(String suffix) {
        return new Identifier("c", id() + suffix);
    }

    default TagKey<Block> getConventionalBlockTag(String suffix) {
        return TagKey.of(RegistryKeys.BLOCK, getConventionalId(suffix));
    }

    default TagKey<Item> getConventionalItemTag(String suffix) {
        return TagKey.of(RegistryKeys.ITEM, getConventionalId(suffix));
    }

    default TagKey<Fluid> getConventionalFluidTag(String suffix) {
        return TagKey.of(RegistryKeys.FLUID, getConventionalId(suffix));
    }
}
