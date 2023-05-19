package com.acikek.pt.datagen;

import com.acikek.pt.api.datagen.PTDatagenApi;
import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.api.PeriodicTable;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.registry.RegistryWrapper;

import java.io.IOException;
import java.util.function.Consumer;

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

        pack.addProvider((FabricDataOutput output) -> PTDatagenApi.createLanguageProvider(
                output, PeriodicTable.INSTANCE,
                "assets/pt/lang/en_us.existing.json")
        );

        pack.addProvider((FabricDataOutput output) -> PTDatagenApi.createLootTableProvider(output, PeriodicTable.INSTANCE));
        pack.addProvider((FabricDataOutput output) -> PTDatagenApi.createRecipeProvider(output, PeriodicTable.INSTANCE));
        pack.addProvider((output, lookup) -> PTDatagenApi.createBlockTagProvider(output, lookup, PeriodicTable.INSTANCE));
        pack.addProvider((output, lookup) -> PTDatagenApi.createItemTagProvider(output, lookup, PeriodicTable.INSTANCE));
        pack.addProvider((output, lookup) -> PTDatagenApi.createFluidTagProvider(output, lookup, PeriodicTable.INSTANCE));
    }
}
