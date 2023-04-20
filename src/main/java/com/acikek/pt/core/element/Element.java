package com.acikek.pt.core.element;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.lang.NameHolder;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.refined.RefinedStateHolder;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.ElementRegistry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.SourceHolder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Element extends NameHolder, SourceHolder, RefinedStateHolder {

    ElementIds<String> elementIds();

    void afterRegister();

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
        return state().getType().getItemName(naming().englishName());
    }

    default String getMiniRefinedItemName() {
        return state().getType().getMiniItemName(naming().englishName());
    }

    default String getRefinedBlockName() {
        return state().getType().getBlockName(naming().englishName());
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

    default void register(ElementRegistry registry) {
        if (hasSources()) {
            for (ElementSource source : sources()) {
                source.register(registry, elementIds());
            }
        }
        state().register(registry, elementIds());
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

    default List<Block> getBlocks() {
        return getValues(ElementSource::getBlocks, ElementRefinedState::getBlocks);
    }

    default List<Item> getItems() {
        return getValues(ElementSource::getItems, ElementRefinedState::getItems);
    }
}
