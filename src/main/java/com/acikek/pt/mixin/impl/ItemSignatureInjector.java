package com.acikek.pt.mixin.impl;

import com.acikek.pt.core.signature.ElementSignature;
import com.acikek.pt.core.signature.SignatureInjector;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemSignatureInjector implements SignatureInjector {

    private List<ElementSignature> pt$signature;
    private Text pt$signatureTooltip;

    @Override
    public List<ElementSignature> signature() {
        return pt$signature;
    }

    @Override
    public void setSignature(List<ElementSignature> signature) {
        pt$signature = signature;
        setSignatureTooltip(createTooltip());
        pt$signature = null;
    }

    @Override
    public void setSignatureTooltip(Text tooltip) {
        pt$signatureTooltip = tooltip;
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void pt$injectTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (pt$signatureTooltip != null && context.isAdvanced()) {
            tooltip.add(pt$signatureTooltip);
        }
    }
}
