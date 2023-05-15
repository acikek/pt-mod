package com.acikek.pt.core.impl.refined;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateType;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class BaseRefinedState implements ElementRefinedState {

    private final Identifier id;
    private final PhasedContent<Item> item;
    private final PhasedContent<Item> miniItem;
    private final PhasedContent<Block> block;
    protected final RefinedStateType type;

    public BaseRefinedState(Identifier id, PhasedContent<Item> item, PhasedContent<Item> miniItem, PhasedContent<Block> block, RefinedStateType type) {
        Stream.of(id, item, miniItem, block).forEach(Objects::requireNonNull);
        this.id = id;
        this.item = item;
        this.miniItem = miniItem;
        this.block = block;
        this.type = type;
    }

    @NotNull
    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public @NotNull Identifier getTypeId() {
        return RefinedStates.BASE;
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        String name = parent.display().englishName();
        block.require(block -> builder.add(block, type.getBlockName(name)));
        item.require(item -> builder.add(item, type.getItemName(name)));
        miniItem.require(item -> builder.add(item, type.getMiniItemName(name)));
    }

    @Override
    public void buildBlockModels(BlockStateModelGenerator generator, Element parent) {
        block.require(block -> type.buildRefinedBlockModel(generator, block));
    }

    @Override
    public void buildItemModels(ItemModelGenerator generator, Element parent) {
        for (var content : List.of(item, miniItem)) {
            content.require(c -> generator.register(c, Models.GENERATED));
        }
    }

    @Override
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        block.require(block -> {
            var id = Registries.BLOCK.getId(block);
            type.buildRefinedBlockTags(provider, id);
            provider.apply(parent.getConventionalBlockTag("%s_blocks")).addOptional(id);
        });
    }

    @Override
    public void buildLootTables(FabricBlockLootTableProvider provider, Element parent) {
        block.require(block ->
            provider.withConditions(DefaultResourceConditions.itemsRegistered(block)).addDrop(block)
        );
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent) {
        boolean powder = type == RefinedStateType.POWDER;
        item.require(item -> {
            for (String format : (powder ? List.of("%s_dusts", "%ss") : List.of("%s_ingots", "%s"))) {
                provider.apply(parent.getConventionalItemTag(format)).addOptional(Registries.ITEM.getId(item));
            }
        });
        miniItem.require(item -> {
            for (String format : (powder ? List.of("%s_small_dusts", "%s_tiny_dusts") : List.of("%s_nuggets", "%s_mini"))) {
                provider.apply(parent.getConventionalItemTag(format)).addOptional(Registries.ITEM.getId(item));
            }
        });
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids, ContentContext.State context, FeatureRequests.Single features) {
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        item.create(item -> registry.registerItem(ids.getItemId(), item));
        miniItem.create(item -> registry.registerItem(ids.getMiniItemId(), item));
        block.create(block -> registry.registerBlock(ids.getBlockId(), block));
    }

    @Override
    public @Nullable Item mineralResultItem() {
        return item.get();
    }

    @Override
    public List<Block> getBlocks() {
        return block.isCreated()
                ? Collections.singletonList(block.require())
                : Collections.emptyList();
    }

    @Override
    public List<Item> getItems() {
        return item.isCreated()
                ? List.of(item.require(), miniItem.require())
                : Collections.emptyList();
    }
}
