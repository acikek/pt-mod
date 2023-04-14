package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.core.mineral.MineralResult;
import net.minecraft.world.World;

import java.util.List;

public class MineralResultImpls {

    public static class Single implements MineralResult {
        
        private final Pair pair;

        public Single(Pair pair) {
            this.pair = pair;
        }

        @Override
        public Pair get(World world) {
            return pair;
        }
    }

    public static class Random implements MineralResult {

        private final List<Pair> pairs;

        public Random(List<Pair> pairs) {
            this.pairs = pairs;
        }

        @Override
        public Pair get(World world) {
            int choice = world.random.nextBetween(0, pairs.size() - 1);
            return pairs.get(choice);
        }
    }
}
