package com.acikek.pt.core.mineral;

import com.acikek.pt.core.element.ElementSignature;
import com.acikek.pt.core.lang.MineralNaming;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MineralBlock extends Block implements Mineral {

    private final MineralNaming naming;
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
        tooltip = ElementSignature.createTooltip(results).copy().formatted(Formatting.GRAY);
    }

    @Override
    public List<ElementSignature> results() {
        return results;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (this.tooltip != null && options.isAdvanced()) {
            tooltip.add(this.tooltip);
        }
    }
}
