package com.acikek.pt.core.api.display;

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

    default Identifier getConventionalTagId(String formatter) {
        return new Identifier("c", formatter.formatted(id()));
    }

    default String getConventionalTagKey(String formatter, String type) {
        return "tag." + type + ".c." + formatter.formatted(id());
    }

    default TagKey<Block> getConventionalBlockTag(String formatter) {
        return TagKey.of(RegistryKeys.BLOCK, getConventionalTagId(formatter));
    }

    default String getConventionalBlockKey(String formatter) {
        return getConventionalTagKey(formatter, "block");
    }

    default TagKey<Item> getConventionalItemTag(String formatter) {
        return TagKey.of(RegistryKeys.ITEM, getConventionalTagId(formatter));
    }

    default String getConventionalItemKey(String formatter) {
        return getConventionalTagKey(formatter, "item");
    }

    default TagKey<Fluid> getConventionalFluidTag(String formatter) {
        return TagKey.of(RegistryKeys.FLUID, getConventionalTagId(formatter));
    }

    default String getConventionalFluidKey(String formatter) {
        return getConventionalTagKey(formatter, "fluid");
    }
}
