package com.acikek.pt.api.datagen;

import com.acikek.pt.api.impl.datagen.PTDatagenImpl;
import com.acikek.pt.core.element.Element;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class PTDatagenApi {

    public static void buildEnglishTranslationsForElement(FabricLanguageProvider.TranslationBuilder builder, Element element) {
        PTDatagenImpl.buildTranslationsForElement(builder, element);
    }

    public static void generateBlocksModelsForElement(BlockStateModelGenerator generator, Element element) {
        PTDatagenImpl.generateBlocksModelsForElement(generator, element);
    }

    public static void generateItemModelsForElement(ItemModelGenerator generator, Element element) {
        PTDatagenImpl.generateItemModelsForElement(generator, element);
    }
}
