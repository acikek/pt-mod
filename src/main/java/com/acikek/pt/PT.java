package com.acikek.pt;

import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PT implements ModInitializer {

    public static final String ID = "pt";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    public static final ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroup.builder(PT.id("blocks"))
            .icon(() -> Blocks.DIRT.asItem().getDefaultStack())
            .build();

    public static final ItemGroup ITEM_GROUP_ITEMS = FabricItemGroup.builder(PT.id("items"))
            .icon(Items.APPLE::getDefaultStack)
            .build();

    public static <T> T register(Registry<? super T> registry, String path, T entry) {
        return Registry.register(registry, id(path), entry);
    }

    public static Item.Settings defaultSettings() {
        return new FabricItemSettings();
    }

    public static Item registerItem(String path, Item item) {
        register(Registries.ITEM, path, item);
        return item;
    }

    public static Block registerBlock(String path, Block block) {
        register(Registries.BLOCK, path, block);
        registerItem(path, new BlockItem(block, defaultSettings()));
        return block;
    }

    public static Fluid registerFluid(String path, Fluid fluid) {
        register(Registries.FLUID, path, fluid);
        return fluid;
    }

    @Override
    public void onInitialize() {
        ModSoundEvents.register();
        PeriodicTable.register();
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_BLOCKS).register(entries -> PeriodicTable.getBlocks().forEach(entries::add));
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_ITEMS).register(entries -> PeriodicTable.getItems().forEach(entries::add));
    }
}
