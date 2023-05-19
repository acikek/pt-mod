package com.acikek.pt.core.impl.refined;

import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.api.request.RequestTypes;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.content.PhasedContent;
import com.acikek.pt.core.api.element.Element;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateData;
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

    protected Element parent;

    public BaseRefinedState(Identifier id, PhasedContent<Item> item, PhasedContent<Item> miniItem, PhasedContent<Block> block, RefinedStateType type) {
        Stream.of(id, item, miniItem, block).forEach(Objects::requireNonNull);
        if (!block.canExist()) {
            throw new IllegalStateException("refined state block must exist");
        }
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
    public void register(PTRegistry registry, ElementIds<String> ids, ContentContext.State context, FeatureRequests.Single features) {
        parent = context.parent();
        if (!features.contains(RequestTypes.CONTENT)) {
            return;
        }
        item.create(item -> registry.registerItem(ids.getItemId(), item));
        miniItem.create(item -> registry.registerItem(ids.getMiniItemId(), item));
        block.create(block -> registry.registerBlock(ids.getBlockId(), block));
    }

    @Override
    public void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        String name = parent.display().englishName();
        block.require(block -> builder.add(block, type.getBlockName(name)));
        item.require(item -> builder.add(item, type.getItemName(name)));
        miniItem.require(item -> builder.add(item, type.getMiniItemName(name)));
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
    public void buildBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider) {
        block.require(block -> {
            var id = Registries.BLOCK.getId(block);
            type.buildRefinedBlockTags(provider, id);
            provider.apply(parent.getConventionalBlockTag("%s_blocks")).addOptional(id);
        });
    }

    @Override
    public void buildLootTables(FabricBlockLootTableProvider provider) {
        block.require(block ->
            provider.withConditions(DefaultResourceConditions.itemsRegistered(block)).addDrop(block)
        );
    }

    @Override
    public void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider) {
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
