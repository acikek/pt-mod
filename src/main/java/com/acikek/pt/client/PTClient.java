package com.acikek.pt.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class PTClient implements ClientModInitializer {

    public static final KeyBinding SHOW_SYMBOL = new KeyBinding(
            "key.pt.show_symbol",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            "category.pt.main"
    );

    public static boolean showSymbol = false;
    public static int showSymbolTicks = 0;

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(SHOW_SYMBOL);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen == null) {
                showSymbol = SHOW_SYMBOL.isPressed();
            }
            if (showSymbol) {
                showSymbolTicks++;
            }
        });
    }
}
