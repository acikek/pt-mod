package com.acikek.pt.datagen;

import com.acikek.pt.api.datagen.PTDatagenApi;
import com.acikek.pt.core.PeriodicTable;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

import java.io.IOException;

public class PTDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider((FabricDataOutput output) -> new FabricModelProvider(output) {
            @Override
            public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
                PeriodicTable.INSTANCE.forEachElement(element ->
                        PTDatagenApi.generateBlocksModelsForElement(blockStateModelGenerator, element)
                );
            }

            @Override
            public void generateItemModels(ItemModelGenerator itemModelGenerator) {
                PeriodicTable.INSTANCE.forEachElement(element ->
                        PTDatagenApi.generateItemModelsForElement(itemModelGenerator, element)
                );
            }
        });

        pack.addProvider((FabricDataOutput output) -> new FabricLanguageProvider(output) {
            @Override
            public void generateTranslations(TranslationBuilder translationBuilder) {
                PeriodicTable.INSTANCE.forEachElement(element ->
                        PTDatagenApi.buildEnglishTranslationsForElement(translationBuilder, element)
                );
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
    }
}
