package com.acikek.pt.core.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public record ElementRegistry(String key) {

    private <T> T register(Registry<? super T> registry, String path, T entry) {
        Identifier id = new Identifier(key, path);
        return Registry.register(registry, id, entry);
    }

    public Item registerItem(String path, Item item) {
        register(Registries.ITEM, path, item);
        return item;
    }

    public Block registerBlock(String path, Block block) {
        register(Registries.BLOCK, path, block);
        registerItem(path, new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public Fluid registerFluid(String path, Fluid fluid) {
        register(Registries.FLUID, path, fluid);
        return fluid;
    }
}
