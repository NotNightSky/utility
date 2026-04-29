package net.notnightsky.utility.config.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class keyBindings {
    public static KeyMapping toggleFullBright;
    public static KeyMapping incrementFullBright;
    public static KeyMapping decrementFullBright;
    public static KeyMapping zoomKey;
    public static KeyMapping cyclePreset;
    public static KeyMapping previousPreset;
    public static boolean loaded = false;

    public static void registerKeyMappings() {
        KeyMapping.Category skyutilsCategory = KeyMapping.Category.register(Identifier.parse("skyutils"));

        zoomKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "Zoom",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        toggleFullBright = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "Toggle Fullbright",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        incrementFullBright = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "Fullbright Increment",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        decrementFullBright = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "Fullbright Decrement",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        cyclePreset = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "Preset Forward",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        previousPreset = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "Preset Previous",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));
    }
}

