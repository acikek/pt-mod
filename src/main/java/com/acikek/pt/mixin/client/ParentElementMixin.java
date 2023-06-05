package com.acikek.pt.mixin.client;

import com.acikek.pt.client.PTClient;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ParentElement.class)
public interface ParentElementMixin {

    @Inject(method = "keyPressed", locals = LocalCapture.CAPTURE_FAILHARD, at = @At("HEAD"))
    private void pt$captureScreenPress(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen && keyCode == 80) {
            PTClient.showSymbol = true;
        }
    }

    @Inject(method = "keyReleased", locals = LocalCapture.CAPTURE_FAILHARD, at = @At("HEAD"))
    private void pt$captureScreenRelease(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Screen && keyCode == 80) {
            PTClient.showSymbol = false;
        }
    }
}
