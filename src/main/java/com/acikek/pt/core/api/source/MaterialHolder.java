package com.acikek.pt.core.api.source;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.api.element.Element;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;

public interface MaterialHolder {

    List<Block> getBlocks();

    List<Item> getItems();

    default void injectSignature(Element element) {
        var tooltip = element.createTooltip();
        for (Item item : getItems()) {
            PTApi.injectSignature(item, tooltip);
        }
        for (Block block : getBlocks()) {
            PTApi.injectSignature(block, tooltip);
        }
    }
}
