package com.acikek.pt.api.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class PTRecipeProvider extends FabricRecipeProvider {

    private Consumer<RecipeJsonProvider> exporter;

    public PTRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    public Consumer<RecipeJsonProvider> withConditions(ConditionJsonProvider... conditions) {
        Objects.requireNonNull(exporter);
        return super.withConditions(exporter, conditions);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        this.exporter = exporter;
    }
}
