package com.acikek.pt.core.source;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;

public interface MaterialHolder {

    List<Block> getBlocks();

    List<Item> getItems();
}
