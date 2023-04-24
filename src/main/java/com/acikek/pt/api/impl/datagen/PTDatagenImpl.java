package com.acikek.pt.api.impl.datagen;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.api.element.Element;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.Function;

public class PTDatagenImpl {

    public static <T> void delegate(TriConsumer<DatagenDelegator, T, Element> fn, T context, Element element) {
        fn.accept(element.state(), context, element);
        element.forEachSource(source -> fn.accept(source, context, element));
    }

    public static void buildTranslationsForElement(FabricLanguageProvider.TranslationBuilder builder, Element element) {
        builder.add(element.getNameKey(), element.display().englishName());
        builder.add(element.getSymbolKey(), element.display().symbol());
        delegate(DatagenDelegator::buildTranslations, builder, element);
    }

    public static void buildBlocksModelsForElement(BlockStateModelGenerator generator, Element element) {
        delegate(DatagenDelegator::buildBlockModels, generator, element);
    }

    public static void buildItemModelsForElement(ItemModelGenerator itemModelGenerator, Element element) {
        delegate(DatagenDelegator::buildItemModels, itemModelGenerator, element);
    }

    public static void buildLootTablesForElement(BlockLootTableGenerator generator, Element element) {
        delegate(DatagenDelegator::buildLootTables, generator, element);
    }

    public static void buildBlockTagsForElement(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element element) {
        delegate(DatagenDelegator::buildBlockTags, provider, element);
    }

    public static void buildItemTagsForElement(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element element) {
        delegate(DatagenDelegator::buildItemTags, provider, element);
    }

    public static void buildFluidTagsForElement(Function<TagKey<Fluid>, FabricTagProvider<Fluid>.FabricTagBuilder> provider, Element element) {
        delegate(DatagenDelegator::buildFluidTags, provider, element);
    }

    public static TextureMap getPowderTextureMap(Block powderBlock) {
        Identifier top = TextureMap.getSubId(powderBlock, "_top");
        return new TextureMap()
                .put(TextureKey.SIDE, TextureMap.getSubId(powderBlock, "_side"))
                .put(TextureKey.FRONT, TextureMap.getSubId(powderBlock, "_front"))
                .put(TextureKey.TOP, top)
                .put(TextureKey.PARTICLE, top)
                .put(TextureKey.BOTTOM, TextureMap.getSubId(ModBlocks.EMPTY_SACK, "_bottom"));
    }
}
