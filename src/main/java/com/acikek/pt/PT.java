package com.acikek.pt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PT implements ModInitializer {

    public static final String ID = "pt";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    public static JsonObject getElementData() {
        Gson gson = new Gson();
        try (InputStream stream = PT.class.getResourceAsStream("/elements.json")) {
            return stream != null
                    ? gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class)
                    : null;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onInitialize() {

    }
}
