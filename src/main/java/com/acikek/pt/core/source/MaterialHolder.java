package com.acikek.pt.core.source;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;

public interface MaterialHolder {

    Item mineralResultItem();

    List<Block> getBlocks();

    List<Item> getItems();
}
