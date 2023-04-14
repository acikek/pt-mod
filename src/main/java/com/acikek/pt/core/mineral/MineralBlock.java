package com.acikek.pt.core.mineral;

import net.minecraft.block.Block;

import java.util.List;
import java.util.function.Supplier;

public class MineralBlock extends Block implements Mineral {

    private final String name;
    private Supplier<List<MineralResult>> resultSupplier;
    private List<MineralResult> results;

    public MineralBlock(Settings settings, String name, Supplier<List<MineralResult>> resultSupplier) {
        super(settings);
        this.name = name;
        this.resultSupplier = resultSupplier;
    }

    @Override
    public String englishName() {
        return name;
    }

    @Override
    public void init() {
        results = resultSupplier.get();
        resultSupplier = null;
    }

    @Override
    public List<MineralResult> results() {
        return results;
    }
}
