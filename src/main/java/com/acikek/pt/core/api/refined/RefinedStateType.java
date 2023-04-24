package com.acikek.pt.core.api.refined;

import com.acikek.pt.core.api.element.Element;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface RefinedStateType {

    /**
     * @return the English name of the refined block
     */
    String getBlockName(String elementName);

    /**
     * @return the English name of the refined item
     */
    String getItemName(String elementName);

    /**
     * @return the English name of the miniature refined item
     */
    String getMiniItemName(String elementName);

    /**
     * Creates the refined block instance. This <bold>does not</bold> register the block, and instead should be called
     * when building an element's refined state, such as with the {@link com.acikek.pt.core.impl.refined.RefinedStateBuilder} implementation.
     *
     * @param blockStrength the requested strength of the refined block. Should not be used if unnecessary. If necessary and {@code null}, should throw an error.
     */
    Block createBlock(@Nullable Float blockStrength);

    /**
     * Generates the block model for the {@link ElementRefinedState#refinedBlock()}.
     * @param block the refined block
     */
    void buildRefinedBlockModel(BlockStateModelGenerator generator, Element parent, Block block);

    /**
     * Generates specific block tags for the {@link ElementRefinedState#refinedBlock()}.
     * The block is already added to the conventional tag {@code c:<element_name>_blocks}.
     * @param block the refined block
     */
    void buildRefinedBlockTags(Function<TagKey<Block>, FabricTagProvider<Block>.FabricTagBuilder> provider, Element parent, Block block);

    /**
     * Generates additional conventional item tags for the {@link ElementRefinedState#refinedItem()} and {@link ElementRefinedState#miniRefinedItem()}.<br>
     * The items are already added to the conventional tags {@code c:<element_name>_refined} and {@code c:<element_name>_mini} respectively.
     * @param state the parent refined state
     * @see Element#getRefinedItemTag()
     * @see Element#getMiniRefinedItemTag()
     */
    void buildItemTags(Function<TagKey<Item>, FabricTagProvider<Item>.FabricTagBuilder> provider, Element parent, ElementRefinedState state);
}
