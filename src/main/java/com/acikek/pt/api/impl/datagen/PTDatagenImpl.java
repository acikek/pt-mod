package com.acikek.pt.api.impl.datagen;

import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.block.ModBlocks;
import com.acikek.pt.core.api.AbstractPeriodicTable;
import com.acikek.pt.core.api.element.Element;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class PTDatagenImpl {

    public static <T> void delegate(BiConsumer<DatagenDelegator, T> fn, T context, AbstractPeriodicTable table) {
        table.forEachMineral(mineral -> fn.accept(mineral, context));
        table.forEachElement(element -> fn.accept(element, context));
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
