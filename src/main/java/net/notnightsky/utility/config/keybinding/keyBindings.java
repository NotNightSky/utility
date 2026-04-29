package net.notnightsky.utility.config.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
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

    public static void registerKeybindings() {
        KeyMapping.Category skyutilsCategory = KeyMapping.Category.register(Identifier.parse("skyutils"));

        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "Zoom",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        toggleFullBright = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "Toggle Fullbright",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        incrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "Fullbright Increment",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        decrementFullBright = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "Fullbright Decrement",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        cyclePreset = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "Preset Forward",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));

        previousPreset = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "Preset Previous",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
        ));
    }
}

