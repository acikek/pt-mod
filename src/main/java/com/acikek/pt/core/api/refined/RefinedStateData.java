package com.acikek.pt.core.api.refined;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public class RefinedStateData {

    public static class Base {

        private final Block block;
        private final Item item;
        private final Item miniItem;

        public Base(Block block, Item item, Item miniItem) {
            this.block = block;
            this.item = item;
            this.miniItem = miniItem;
        }

        public Block block() {
            return block;
        }

        public Item item() {
            return item;
        }

        public Item miniItem() {
            return miniItem;
        }
    }

    public static class HasFluid extends Base {

        private final Fluid fluid;

        public HasFluid(Block block, Item item, Item miniItem, Fluid fluid) {
            super(block, item, miniItem);
            this.fluid = fluid;
        }

        public Fluid fluid() {
            return fluid;
        }
    }
}
