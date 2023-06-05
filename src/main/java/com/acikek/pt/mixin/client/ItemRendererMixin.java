package com.acikek.pt.mixin.client;

import com.acikek.pt.client.PTClient;
import com.acikek.pt.core.api.signature.SignatureAcceptor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    private ItemStack pt$renderingStack = null;

    @Inject(
            method = "renderGuiItemOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            locals = LocalCapture.CAPTURE_FAILHARD, at = @At("HEAD")
    )
    private void pt$captureRenderingStack(MatrixStack matrices, TextRenderer textRenderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo ci) {
        pt$renderingStack = stack;
    }

    @ModifyVariable(
            method = "renderGuiItemOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getCount()I", ordinal = 0), argsOnly = true, index = 6
    )
    private String pt$renderSymbol(String value) {
        if (PTClient.showSymbol) {
            var acceptor = (SignatureAcceptor) pt$renderingStack.getItem();
            if (!acceptor.hasSignature()) {
                return value;
            }
            var units = acceptor.signature().units();
            if (units.isEmpty()) {
                return value;
            }
            var unit = units.size() == 1
                    ? units.get(0)
                    : units.get((PTClient.showSymbolTicks / 20) % units.size());
            return unit.element().getSymbolText().getString();
        }
        return value;
    }
}
