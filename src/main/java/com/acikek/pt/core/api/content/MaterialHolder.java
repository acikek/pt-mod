package com.acikek.pt.core.api.content;

import com.acikek.pt.api.PTApi;
import com.acikek.pt.core.api.signature.SignatureHolder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface MaterialHolder {

    List<Block> getBlocks();

    List<Item> getItems();

    default void addSignatures(SignatureHolder holder) {
        for (Item item : getItems()) {
            PTApi.setSignature(item, holder.signature());
        }
        for (Block block : getBlocks()) {
            PTApi.setSignature(block, holder.signature());
        }
    }

    static <H extends MaterialHolder, M> List<M> getAllMaterials(Collection<H> holders, Function<H, List<M>> mapper) {
        return holders.stream()
                .flatMap(content -> mapper.apply(content).stream())
                .toList();
    }

    static <H extends MaterialHolder> List<Block> getAllBlocks(Collection<H> holders) {
        return getAllMaterials(holders, MaterialHolder::getBlocks);
    }

    static <H extends MaterialHolder> List<Item> getAllItems(Collection<H> holders) {
        return getAllMaterials(holders, MaterialHolder::getItems);
    }
}
