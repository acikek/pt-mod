package com.acikek.pt.core.api.source;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class SourceData {

    public record Ore(Block ore, Block deepslateOre, Item rawItem, Block rawBlock) {};
}
