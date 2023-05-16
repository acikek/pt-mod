package com.acikek.pt.core.api.element;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.ContentBase;
import com.acikek.pt.core.api.content.ContentContext;
import com.acikek.pt.core.api.content.SourceStateMapper;
import com.acikek.pt.core.api.display.DisplayHolder;
import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.mineral.MineralResultHolder;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.refined.RefinedStates;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.ElementSignature;
import com.acikek.pt.core.api.signature.SignatureHolder;
import com.acikek.pt.core.api.signature.Signatures;
import com.acikek.pt.core.api.source.ElementSource;
import com.acikek.pt.core.api.source.MaterialHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public interface Element extends DisplayHolder<ElementDisplay>, SourceStateMapper, SignatureHolder, MaterialHolder {

    ElementIds<String> elementIds();

    void afterRegister();

    @Override
    default List<ElementSignature> signature() {
        return Collections.singletonList(Signatures.unit(this, display().signatureAmount()));
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

    default Item getMineralResultItem(World world) {
        var source = MineralResultHolder.filterAndGet(getSources(), world);
        return source != null
                ? source.mineralResultItem()
                : MineralResultHolder.filterAndGet(getRefinedStates(), world).mineralResultItem(); // empty case covered by exception
    }

    default void mapContent(BiConsumer<ElementRefinedState<?>, ContentContext.State> stateFn, BiConsumer<ElementSource<?>, ContentContext.Source> sourceFn) {
        for (var entry : sourceStateMap().entrySet()) {
            stateFn.accept(entry.getKey(), new ContentContext.State(this));
            for (var source : entry.getValue()) {
                sourceFn.accept(source, new ContentContext.Source(this, entry.getKey()));
            }
        }
    }

    // Ensure uniqueness between allotrope IDs
    private ElementIds<String> getElementIdsForState(ElementRefinedState<?> state) {
        var suffix = state.isMain() ? "" : "_" + state.getId().getPath();
        return elementIds().append(suffix);
    }

    default void register(PTRegistry registry, FeatureRequests.Content stateRequests, FeatureRequests.Content sourceRequests) {
        mapContent(
                (state, ctx) -> state.register(registry, getElementIdsForState(state), ctx, stateRequests.getContent(state.getTypeId())),
                (source, ctx) -> source.register(registry, getElementIdsForState(ctx.state()), ctx, sourceRequests.getContent(source.getTypeId()))
        );
        afterRegister();
    }

    @Environment(EnvType.CLIENT)
    default void initClient() {
        mapContent(ContentBase::initClient, ContentBase::initClient);
    }

    private <T> List<T> getValues(Function<ContentBase<?, ?>, List<T>> mapper) {
        return getAllContent().stream()
                .flatMap(content -> mapper.apply(content).stream())
                .toList();
    }

    @Override
    default List<Block> getBlocks() {
        return getValues(ContentBase::getBlocks);
    }

    @Override
    default List<Item> getItems() {
        return getValues(ContentBase::getItems);
    }
}
