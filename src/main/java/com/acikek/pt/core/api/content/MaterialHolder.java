package com.acikek.pt.core.api.content;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.api.signature.SignatureHolder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;

public interface MaterialHolder {

    List<Block> getBlocks();

    List<Item> getItems();

    default void addSignatures(SignatureHolder holder) {
        for (Item item : getItems()) {
            PTApi.addSignature(item, holder.signature());
        }
        for (Block block : getBlocks()) {
            PTApi.addSignature(block, holder.signature());
        }
    }
}
