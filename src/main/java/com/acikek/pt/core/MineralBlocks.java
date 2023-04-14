package com.acikek.pt.core;

import com.acikek.pt.core.mineral.MineralBlock;
import com.acikek.pt.core.mineral.Minerals;

import java.util.List;

public class MineralBlocks {

    public static final MineralBlock STIBNITE = Minerals.block("Stibnite",
            () -> List.of(Minerals.single(PeriodicTable.ANTIMONY, 2), Minerals.single(PeriodicTable.SULFUR, 3)));
}
