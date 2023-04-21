package com.acikek.pt.core.source;

import com.acikek.pt.core.element.Element;
import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.ElementRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public interface ElementSource {

    @NotNull Identifier getId();

    default boolean isType(Identifier id) {
        return getId().equals(id);
    }

    Mineral mineral();

    default boolean hasMineral() {
        return mineral() != null;
    }

    Item mineralResultItem();

    default boolean hasMineralResult() {
        return mineralResultItem() != null;
    }

    void register(ElementRegistry registry, ElementIds<String> ids);

    default void buildTranslations(FabricLanguageProvider.TranslationBuilder builder, Element parent) {
        // Empty
    }

    default void buildLootTables(BlockLootTableGenerator generator, Element parent) {
        // Empty
    }

    default void buildAdditionalBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent) {
        // Empty
    }

    List<Block> getBlocks();

    List<Item> getItems();

    @SuppressWarnings("unchecked")
    static List<ElementSource> forObject(Object obj) {
        if (obj instanceof ElementSource source) {
            return List.of(source);
        }
        if (obj instanceof List<?> list) {
            return (List<ElementSource>) list;
        }
        throw new IllegalStateException("ElementSource list can only be derived from an actual list or a single instance");
    }
}
