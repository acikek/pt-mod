package com.acikek.pt;

import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.core.registry.ElementRegistry;
import com.acikek.pt.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class PT implements ModInitializer {

    public static final String ID = "pt";
    public static final ElementRegistry REGISTRY = new ElementRegistry(ID);

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    public static final ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroup.builder(PT.id("blocks"))
            .icon(() -> Blocks.DIRT.asItem().getDefaultStack())
            .build();

    public static final ItemGroup ITEM_GROUP_ITEMS = FabricItemGroup.builder(PT.id("items"))
            .icon(Items.APPLE::getDefaultStack)
            .build();

    @Override
    public void onInitialize() {
        ModSoundEvents.register();
        PeriodicTable.INSTANCE.register();
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_BLOCKS).register(entries -> PeriodicTable.INSTANCE.getBlocks().forEach(entries::add));
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_ITEMS).register(entries -> PeriodicTable.INSTANCE.getItems().forEach(entries::add));
    }
}
