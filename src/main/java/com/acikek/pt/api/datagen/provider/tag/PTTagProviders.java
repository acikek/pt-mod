package com.acikek.pt.api.datagen.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class PTTagProviders {

    public abstract static class BlockTagProvider extends FabricTagProvider.BlockTagProvider implements PTTagProvider<Block> {

        public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void add(TagKey<Block> tag, Stream<Block> objects) {
            var builder = getOrCreateTagBuilder(tag);
            objects.forEach(b -> builder.addOptional(reverseLookup(b)));
        }

        @Override
        public FabricTagProvider<Block>.FabricTagBuilder getOrCreateTagBuilder(TagKey<Block> tag) {
            return super.getOrCreateTagBuilder(tag);
        }
    }

    public abstract static class ItemTagProvider extends FabricTagProvider.ItemTagProvider implements PTTagProvider<Item> {

        public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void add(TagKey<Item> tag, Stream<Item> items) {
            var builder = getOrCreateTagBuilder(tag);
            items.forEach(i -> builder.addOptional(reverseLookup(i)));
        }

        @Override
        public FabricTagProvider<Item>.FabricTagBuilder getOrCreateTagBuilder(TagKey<Item> tag) {
            return super.getOrCreateTagBuilder(tag);
        }
    }

    public abstract static class FluidTagProvider extends FabricTagProvider.FluidTagProvider implements PTTagProvider<Fluid> {

        public FluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void add(TagKey<Fluid> tag, Stream<Fluid> fluids) {
            var builder = getOrCreateTagBuilder(tag);
            fluids.forEach(b -> builder.addOptional(reverseLookup(b)));
        }

        @Override
        public FabricTagProvider<Fluid>.FabricTagBuilder getOrCreateTagBuilder(TagKey<Fluid> tag) {
            return super.getOrCreateTagBuilder(tag);
        }
    }
}
