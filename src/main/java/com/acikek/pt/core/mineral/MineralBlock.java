package com.acikek.pt.core.mineral;

import com.acikek.pt.core.lang.MineralNaming;
import net.minecraft.block.Block;

import java.util.List;
import java.util.function.Supplier;

public class MineralBlock extends Block implements Mineral {

    private final MineralNaming naming;
    private Supplier<List<MineralResult>> resultSupplier;
    private List<MineralResult> results;

    public MineralBlock(Settings settings, MineralNaming naming, Supplier<List<MineralResult>> resultSupplier) {
        super(settings);
        this.naming = naming;
        this.resultSupplier = resultSupplier;
    }

    @Override
    public MineralNaming naming() {
        return naming;
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
