package com.acikek.pt.datagen;

import com.acikek.pt.core.PeriodicTable;
import com.acikek.pt.core.element.Element;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

public class PTModelProvider extends FabricModelProvider {

    public PTModelProvider(FabricDataOutput output) {
        super(output);
    }

    public static void generateBlocksForElement(BlockStateModelGenerator generator, Element element) {
        element.state().buildBlockModel(generator);
        element.forEachSource(source -> {
            for (Block block : source.getBlocks()) {
                generator.registerSimpleCubeAll(block);
            }
        });
    }

    public static void generateItemsForElement(ItemModelGenerator itemModelGenerator, Element element) {
        for (Item item : element.getItems()) {
            itemModelGenerator.register(item, Models.GENERATED);
        }
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (Element element : PeriodicTable.INSTANCE.getElements()) {
            generateBlocksForElement(blockStateModelGenerator, element);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Element element : PeriodicTable.INSTANCE.getElements()) {
            generateItemsForElement(itemModelGenerator, element);
        }
    }
}
