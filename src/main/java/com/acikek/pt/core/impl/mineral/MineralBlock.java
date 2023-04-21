package com.acikek.pt.core.impl.mineral;

import com.acikek.pt.core.mineral.Mineral;
import com.acikek.pt.core.registry.PTRegistry;
import com.acikek.pt.core.signature.ElementSignature;
import com.acikek.pt.core.lang.MineralNaming;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class MineralBlock extends Block implements Mineral {

    private final MineralNaming naming;
    private final Block cluster;
    private final Item rawMineral;

    private Supplier<List<ElementSignature>> resultSupplier;
    private List<ElementSignature> results;
    private Text tooltip;

    public MineralBlock(Settings settings, MineralNaming naming, Supplier<List<ElementSignature>> resultSupplier) {
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
        tooltip = createTooltip().copy().formatted(Formatting.GRAY);
    }

    @Override
    public void register(PTRegistry registry) {
        registry.registerBlock(naming.englishName(), this);
        if (cluster != null) {
            registry.registerBlock(ids.getClusterSourceBlockId(), cluster);
        }
        if (rawMineral != null) {
            registry.registerItem(ids.getRawSourceItemId(), rawMineral);
        }
    }

    @Override
    public List<ElementSignature> signature() {
        return results;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (this.tooltip != null && options.isAdvanced()) {
            tooltip.add(this.tooltip);
        }
    }

    @Override
    public List<Block> getBlocks() {
        return cluster != null
                ? List.of(this, cluster)
                : Collections.singletonList(this);
    }

    @Override
    public List<Item> getItems() {
        return rawMineral != null
                ? Collections.singletonList(rawMineral)
                : Collections.emptyList();
    }
}
