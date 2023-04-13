package com.acikek.pt;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.element.Elements;
import com.acikek.pt.core.refined.RefinedStates;
import com.acikek.pt.core.source.ElementSources;
import com.acikek.pt.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PT implements ModInitializer {

    public static final String ID = "pt";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

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
        Element oxygen = Elements.noSource("oxygen", RefinedStates.chamber());
        Element antimony = Elements.full("antimony", ElementSources.mineralBuilder().addCluster().addRawMineral().build(), RefinedStates.metal(4.0f));
        Element gallium = Elements.full("gallium", ElementSources.mineral(), RefinedStates.basin());
        Element osmium = Elements.full("osmium", ElementSources.ore(), RefinedStates.metal(8.0f));

        oxygen.register();
        antimony.register();
        gallium.register();
        osmium.register();
    }
}
