package com.acikek.pt;

import com.acikek.pt.api.impl.PTLoading;
import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.api.PeriodicTable;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PT implements ModInitializer {

    public static final String ID = "pt";
    public static final PTRegistry REGISTRY = new PTRegistry(ID);

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
        PTLoading.load();

        ModSoundEvents.register();
        ModBlocks.register();

        registerArtPack();

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_BLOCKS).register(entries -> {
            PeriodicTable.INSTANCE.getBlocks().forEach(entries::add);
            entries.add(ModBlocks.EMPTY_SACK);
        });
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_ITEMS).register(entries -> PeriodicTable.INSTANCE.getItems().forEach(entries::add));
    }

    private void registerArtPack() {
        FabricLoader.getInstance().getModContainer(ID).ifPresent(mod ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        PT.id("pt-art"), mod,
                        Text.translatable("resourcepack.pt.pt-art"),
                        ResourcePackActivationType.ALWAYS_ENABLED
                )
        );
    }
}
