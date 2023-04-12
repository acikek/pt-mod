package com.acikek.pt.datagen;

import com.acikek.pt.block.ElementBlocks;
import com.acikek.pt.core.Element;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class PTDatagen implements DataGeneratorEntrypoint {

    public static Element ANTIMONY = new Element(
            new ElementBlocks(
                    ElementBlocks.createSourceBlock("antimony", 3.0f, "antimony* :2 sulfur :3"),
                    ElementBlocks.createElementalBlock("antimony", ElementBlocks.ElementalType.METAL, 3.0f, 0)
            ),
            new Item(new FabricItemSettings()),
            new Item(new FabricItemSettings()),
            new Item(new FabricItemSettings())
    );

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack()
                .addProvider(PTModelGenerator::new);
    }
}
