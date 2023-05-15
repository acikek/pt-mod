package com.acikek.pt.core.api.mineral;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public record DefaultMineralData(Block block, Block cluster, Item rawForm) {
}
