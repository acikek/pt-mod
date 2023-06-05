package com.acikek.pt.mixin.impl;

import com.acikek.pt.core.api.signature.CompoundSignature;
import com.acikek.pt.core.api.signature.SignatureAcceptor;
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
public class ItemSignatureAcceptor implements SignatureAcceptor {

    private CompoundSignature pt$signature;
    private Text pt$signatureTooltip;

    @Override
    public CompoundSignature signature() {
        return pt$signature;
    }

    @Override
    public void setSignature(CompoundSignature signature) {
        pt$signature = signature;
        pt$signatureTooltip = getSignatureText();
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void pt$injectTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (pt$signatureTooltip != null && context.isAdvanced()) {
            tooltip.add(pt$signatureTooltip);
        }
    }
}
