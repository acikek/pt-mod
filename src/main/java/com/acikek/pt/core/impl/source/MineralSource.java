package com.acikek.pt.core.impl.source;

import com.acikek.pt.core.impl.mineral.MineralBlock;
import com.acikek.pt.core.registry.ElementIds;
import com.acikek.pt.core.registry.PTRegistry;
import com.acikek.pt.core.source.ElementSource;
import com.acikek.pt.core.source.ElementSources;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MineralSource implements ElementSource {

    private final MineralBlock mineral;

    public MineralSource(MineralBlock mineral) {
        Objects.requireNonNull(mineral);
        this.mineral = mineral;
    }
    
    @Override
    public @NotNull Identifier getId() {
        return ElementSources.MINERAL;
    }

    @Override
    public Item mineralResultItem() {
        return mineral.rawMineral;
    }

    @Override
    public void register(PTRegistry registry, ElementIds<String> ids) {
        // TODO worldgen goes here
    }

    @Override
    public List<Block> getBlocks() {
        return mineral.getBlocks();
    }

    @Override
    public List<Item> getItems() {
        return mineral.getItems();
    }
}
