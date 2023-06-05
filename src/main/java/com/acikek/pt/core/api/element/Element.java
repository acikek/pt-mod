package com.acikek.pt.core.api.element;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.api.datagen.DatagenDelegator;
import com.acikek.pt.api.datagen.provider.PTRecipeProvider;
import com.acikek.pt.api.datagen.provider.tag.PTTagProviders;
import com.acikek.pt.api.request.FeatureRequests;
import com.acikek.pt.core.api.content.*;
import com.acikek.pt.core.api.display.DisplayHolder;
import com.acikek.pt.core.api.display.ElementDisplay;
import com.acikek.pt.core.api.mineral.MineralResultHolder;
import com.acikek.pt.core.api.refined.ElementRefinedState;
import com.acikek.pt.core.api.registry.ElementIds;
import com.acikek.pt.core.api.registry.PTRegistry;
import com.acikek.pt.core.api.signature.CompoundSignature;
import com.acikek.pt.core.api.signature.SignatureHolder;
import com.acikek.pt.core.api.signature.Signatures;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Element extends DisplayHolder<ElementDisplay>, SourceStateMapper, SignatureHolder, DatagenDelegator, MaterialHolder {

    ElementIds<String> elementIds();

    void afterRegister();

    @Override
    default CompoundSignature signature() {
        return Signatures.unit(this, display().signatureAmount()).build();
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

    // Ensure uniqueness between allotrope IDs
    private ElementIds<String> getElementIdsForState(ElementRefinedState<?> state) {
        return state.isMain()
                ? elementIds()
                : elementIds().append("_" + state.id().getPath());
    }


    default ContentContext.State getStateContext(ElementRefinedState<?> state) {
        return new ContentContext.State(this, getElementIdsForState(state));
    }

    default ContentContext.Source getSourceContext(ElementRefinedState<?> parentState) {
        return new ContentContext.Source(this, getElementIdsForState(parentState), parentState);
    }

    default void register(PTRegistry registry, FeatureRequests.Content stateRequests, FeatureRequests.Content sourceRequests) {
        forEachRefinedState(state -> state.register(registry, stateRequests.getContent(state.typeId())));
        forEachSource(source -> source.register(registry, sourceRequests.getContent(source.typeId())));
        afterRegister();
    }


    List<ContentIdentifier> contentBuildPass();

    private <T> void forEachContentPass(Consumer<ElementContentBase<?, ?>> fn) {
        forEachContent(content -> {
            fn.accept(content);
            contentBuildPass().add(content.typeId());
        });
        contentBuildPass().clear();
    }

    default boolean hasBuiltContentPass(ContentIdentifier contentId) {
        return contentBuildPass().contains(contentId);
    }

    @Override
    default void buildTranslations(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(getNameKey(), display().englishName());
        builder.add(getSymbolKey(), display().symbol());
        forEachContentPass(content -> content.buildTranslations(builder));
    }

    @Override
    default void buildBlockModels(BlockStateModelGenerator generator) {
        forEachContentPass(content -> content.buildBlockModels(generator));
    }

    @Override
    default void buildItemModels(ItemModelGenerator generator) {
        forEachContentPass(content -> content.buildItemModels(generator));
    }

    @Override
    default void buildLootTables(FabricBlockLootTableProvider provider) {
        forEachContentPass(content -> content.buildLootTables(provider));
    }

    @Override
    default void buildRecipes(PTRecipeProvider provider) {
        forEachContentPass(content -> content.buildRecipes(provider));
    }

    @Override
    default void buildBlockTags(PTTagProviders.BlockTagProvider provider) {
        forEachContentPass(content -> content.buildBlockTags(provider));
    }

    @Override
    default void buildItemTags(PTTagProviders.ItemTagProvider provider) {
        forEachContentPass(content -> content.buildItemTags(provider));
    }

    @Override
    default void buildFluidTags(PTTagProviders.FluidTagProvider provider) {
        forEachContentPass(content -> content.buildFluidTags(provider));
    }

    @Environment(EnvType.CLIENT)
    default void initClient() {
        forEachContent(ElementContentBase::initClient);
    }

    private <T> List<T> getValues(Function<ElementContentBase<?, ?>, List<T>> mapper) {
        return getAllContent().stream()
                .flatMap(content -> mapper.apply(content).stream())
                .toList();
    }

    @Override
    default List<Block> getBlocks() {
        return getValues(ElementContentBase::getBlocks);
    }

    @Override
    default List<Item> getItems() {
        return getValues(ElementContentBase::getItems);
    }
}
