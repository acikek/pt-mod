package com.acikek.pt.core.impl.refined;

import com.acikek.pt.api.datagen.provider.tag.PTTagProviders;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.element.ContentContext;
import com.acikek.pt.core.api.content.element.ContentIdentifier;
import com.acikek.pt.core.api.content.phase.PhasedContent;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateData;
import com.acikek.pt.core.api.refined.RefinedStateType;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.registry.PTRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class BaseRefinedState<D> implements ElementRefinedState<D> {

    public static class Type extends BaseRefinedState<RefinedStateData.Base> {

        public Type(Identifier id, PhasedContent<Item> item, PhasedContent<Item> miniItem, PhasedContent<Block> block, RefinedStateType type) {
            super(id, item, miniItem, block, type);
        }

        @Override
        public RefinedStateData.Base getData() {
            return new RefinedStateData.Base(block.get(), item.get(), miniItem.get());
        }
    }

    private final Identifier id;
    protected final PhasedContent<Item> item;
    protected final PhasedContent<Item> miniItem;
    protected final PhasedContent<Block> block;
    protected final RefinedStateType type;

    private ContentContext.State context;

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
    public Identifier id() {
        return id;
    }

    @Override
    public @NotNull ContentIdentifier typeId() {
        return RefinedStates.BASE;
    }

    @Override
    public ContentContext.State context() {
        return context;
    }

    @Override
    public void setContext(ContentContext.State context) {
        ElementRefinedState.super.setContext(context);
        this.context = context;
    }

    @Override
    public void register(PTRegistry registry, FeatureRequests.Single features) {
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        item.create(item -> registry.registerItem(contentIds().getItemId(), item));
        miniItem.create(item -> registry.registerItem(contentIds().getMiniItemId(), item));
        block.create(block -> registry.registerBlock(contentIds().getBlockId(), block));
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        String name = parent().display().englishName();
        block.require(block -> builder.add(block, type.getBlockName(name)));
        item.require(item -> builder.add(item, type.getItemName(name)));
        miniItem.require(item -> builder.add(item, type.getMiniItemName(name)));
        boolean powder = type == RefinedStateType.POWDER;
        if (isMain()) {
            builder.add(parent().getConventionalBlockKey("%s_blocks"), name + " Blocks");
            for (String format : (powder ? List.of("%s_dusts", "%ss") : List.of("%s_ingots", "%s"))) {
                builder.add(parent().getConventionalItemKey(format), name + (powder ? " Dusts" : ""));
            }
            for (String format : (powder ? List.of("%s_small_dusts", "%s_tiny_dusts") : List.of("%s_nuggets", "%s_mini"))) {
                String itemName = powder ? "Small " + name + " Dusts" : "Miniature " + name;
                builder.add(parent().getConventionalItemKey(format), itemName);
            }
        }
    }

    @Override
    public void buildBlockModels(BlockStateModelGenerator generator) {
        block.require(block -> type.buildRefinedBlockModel(generator, block));
    }

    @Override
    public void buildItemModels(ItemModelGenerator generator) {
        for (var content : PhasedContent.filterByCreation(item, miniItem)) {
            content.require(c -> generator.register(c, Models.GENERATED));
        }
    }

    @Override
    public void buildBlockTags(PTTagProviders.BlockTagProvider provider) {
        block.ifCreated((block, content) -> {
            if (content.isInternal()) {
                type.buildRefinedBlockTags(provider, block);
            }
            provider.add(parent().getConventionalBlockTag("%s_blocks"), block);
        });
    }

    @Override
    public void buildLootTables(FabricBlockLootTableProvider provider) {
        block.require(block ->
            provider.withConditions(DefaultResourceConditions.itemsRegistered(block)).addDrop(block)
        );
    }

    @Override
    public void buildItemTags(PTTagProviders.ItemTagProvider provider) {
        boolean powder = type == RefinedStateType.POWDER;
        item.ifCreated(item -> {
            for (String format : (powder ? List.of("%s_dusts", "%ss") : List.of("%s_ingots", "%s"))) {
                provider.add(parent().getConventionalItemTag(format), item);
            }
        });
        miniItem.ifCreated(item -> {
            for (String format : (powder ? List.of("%s_small_dusts", "%s_tiny_dusts") : List.of("%s_nuggets", "%s_mini"))) {
                provider.add(parent().getConventionalItemTag(format), item);
            }
        });
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
        return PhasedContent.getByCreation(item, miniItem);
    }
}
