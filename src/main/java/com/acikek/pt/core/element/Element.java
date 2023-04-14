package com.acikek.pt.core.element;

import com.acikek.pt.core.lang.NameHolder;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.refined.ElementRefinedState;
import com.acikek.pt.core.refined.RefinedStateHolder;
import com.acikek.pt.core.registry.ElementRegistry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.SourceHolder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public interface Element extends NameHolder, SourceHolder, RefinedStateHolder {

    ElementIds<String> elementIds();

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

    default String getSourceBlockName() {
        return switch (source().getType()) {
            case ORE -> naming().englishName() + " Ore";
            case MINERAL -> naming().englishName() + " Source Block"; // TODO
            default -> null;
        };
    }

    default String getDeepslateSourceBlockName() {
        return source().getType() == ElementSource.Type.ORE
                ? "Deepslate " + naming().englishName() + " Ore"
                : null;
    }

    default String getClusterSourceBlockName() {
        return source().hasClusterSourceBlock()
                ? naming().englishName() + " Cluster Source Block" // TODO
                : null;
    }

    default String getRawSourceItemName() {
        return switch (source().getType()) {
            case ORE -> "Raw " + naming().englishName();
            case MINERAL -> source().hasRawSourceItem() ? naming().englishName() + " Source Item" : null; // TODO
            default -> null;
        };
    }

    default String getRawSourceBlockName() {
        return source().getType() == ElementSource.Type.ORE
                ? "Block of Raw " + naming().englishName()
                : null;
    }

    default String getRefinedItemName() {
        return state().getType().formatItem(naming().englishName());
    }

    default String getMiniRefinedItemName() {
        return state().getType().formatMiniItem(naming().englishName());
    }

    default String getRefinedBlockName() {
        return state().getType().formatBlock(naming().englishName());
    }

    default void register(ElementRegistry registry) {
        if (hasSource()) {
            source().register(registry, elementIds());
        }
        state().register(registry, elementIds());
    }

    private <T> List<T> getValues(Function<ElementSource, List<T>> sourceList, Function<ElementRefinedState, List<T>> stateList) {
        List<T> sourceBlocks = hasSource() ? sourceList.apply(source()) : Collections.emptyList();
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
