package com.acikek.pt.datagen;

import com.acikek.pt.api.datagen.PTDatagenApi;
import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.api.PeriodicTable;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.registry.RegistryWrapper;

import java.io.IOException;

public class PTDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider((FabricDataOutput output) -> new FabricModelProvider(output) {
            @Override
            public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
                PTDatagenApi.buildBlockModels(blockStateModelGenerator, PeriodicTable.INSTANCE);
                blockStateModelGenerator.registerSingleton(ModBlocks.EMPTY_SACK, TexturedModel.CUBE_BOTTOM_TOP);
            }

            @Override
            public void generateItemModels(ItemModelGenerator itemModelGenerator) {
                PTDatagenApi.buildItemModels(itemModelGenerator, PeriodicTable.INSTANCE);
            }
        });

        pack.addProvider((FabricDataOutput output) -> new FabricLanguageProvider(output) {
            @Override
            public void generateTranslations(TranslationBuilder translationBuilder) {
                PTDatagenApi.buildEnglishTranslations(translationBuilder, PeriodicTable.INSTANCE);
                try {
                    var existing = output.getModContainer().findPath("assets/pt/lang/en_us.existing.json");
                    if (existing.isPresent()) {
                        translationBuilder.add(existing.get());
                    }
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pack.addProvider((FabricDataOutput output) -> new FabricBlockLootTableProvider(output) {
            @Override
            public void generate() {
                PTDatagenApi.buildLootTables(this, PeriodicTable.INSTANCE);
            }
        });

        pack.addProvider((output, lookup) -> new FabricTagProvider.BlockTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                PTDatagenApi.buildBlockTags(this::getOrCreateTagBuilder, PeriodicTable.INSTANCE);
            }
        });

        pack.addProvider((output, lookup) -> new FabricTagProvider.ItemTagProvider(output, lookup) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                PTDatagenApi.buildItemTags(this::getOrCreateTagBuilder, PeriodicTable.INSTANCE);
            }
        });
    }
}
