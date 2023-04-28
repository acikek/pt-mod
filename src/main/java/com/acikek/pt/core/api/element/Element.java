package com.acikek.pt.core.api.element;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.display.DisplayHolder;
import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStateHolder;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignature;
import com.acikek.pt.core.api.signature.SignatureHolder;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.api.source.MaterialHolder;
import com.acikek.pt.core.api.source.SourceHolder;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Element extends DisplayHolder<ElementDisplay>, SourceHolder, RefinedStateHolder, SignatureHolder, MaterialHolder {

    ElementIds<String> elementIds();

    void afterRegister();

    @Override
    default List<ElementSignature> signature() {
        return Collections.singletonList(Elements.unit(this, display().signatureAmount()));
    }

    default String getTextKey(String path) {
        return "element.pt." + id() + "." + path;
    }

    default String getNameKey() {
        return getTextKey("name");
    }

    default String getSymbolKey() {
        return getTextKey("symbol");
    }

    default Text getNameText() {
        return Text.translatable(getNameKey());
    }

    default Text getSymbolText() {
        return Text.translatable(getSymbolKey());
    }

    default Text getQuantifiedText(int amount, boolean bold) {
        MutableText result = Text.empty();
        MutableText text = getSymbolText().copy();
        if (bold) {
            text.formatted(Formatting.BOLD);
        }
        result.append(text);
        if (amount > 1) {
            result.append(PTApi.subscript(amount));
        }
        return result;
    }

    default Text getQuantifiedText(int amount) {
        return getQuantifiedText(amount, false);
    }

    default String getRefinedItemName() {
        return state().getType().getItemName(display().englishName());
    }

    default String getMiniRefinedItemName() {
        return state().getType().getMiniItemName(display().englishName());
    }

    default String getRefinedBlockName() {
        return state().getType().getBlockName(display().englishName());
    }

    default TagKey<Block> getRefinedBlockTag() {
        return getConventionalBlockTag("%s_blocks");
    }

    default TagKey<Item> getRefinedItemTag() {
        return getConventionalItemTag("%s");
    }

    default TagKey<Item> getMiniRefinedItemTag() {
        return getConventionalItemTag("%s_mini");
    }

    default TagKey<Fluid> getRefinedFluidTag() {
        return getConventionalFluidTag("%s");
    }

    default void forEachSource(Consumer<ElementSource> fn) {
        if (hasSources()) {
            for (ElementSource source : sources()) {
                fn.accept(source);
            }
        }
    }

    default Item getMineralResultItem(World world) {
        var sources = sources().stream()
                .filter(ElementSource::hasMineralResult)
                .toList();
        return sources.isEmpty()
                ? state().refinedItem()
                : sources.get(world.random.nextInt(sources.size())).mineralResultItem();
    }

    default void register(PTRegistry registry, FeatureRequests.Content requests, FeatureRequests.Sources sourceRequests) {
        if (hasSources()) {
            for (ElementSource source : sources()) {
                source.register(registry, elementIds(), sourceRequests.getContent(source.getId()));
            }
        }
        state().register(registry, elementIds(), requests);
        afterRegister();
    }

    private <T> List<T> getValues(Function<ElementSource, List<T>> sourceList, Function<ElementRefinedState, List<T>> stateList) {
        List<T> sourceBlocks = hasSources()
                ? sources().stream().flatMap(source -> sourceList.apply(source).stream()).toList()
                : Collections.emptyList();
        List<T> result = new ArrayList<>(sourceBlocks);
        result.addAll(stateList.apply(state()));
        return result;
    }

    @Override
    default List<Block> getBlocks() {
        return getValues(ElementSource::getBlocks, ElementRefinedState::getBlocks);
    }

    @Override
    default List<Item> getItems() {
        return getValues(ElementSource::getItems, ElementRefinedState::getItems);
    }
}
