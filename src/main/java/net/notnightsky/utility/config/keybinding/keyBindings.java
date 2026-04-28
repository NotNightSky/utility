package net.notnightsky.utility.config.keybinding;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class keyBindings {
    public static KeyBinding toggleFullBright;
    public static KeyBinding incrementFullBright;
    public static KeyBinding decrementFullBright;
    public static KeyBinding zoomKey;
    public static boolean loaded = false;

    public static void registerKeybindings(){
    KeyBinding.Category skyutilsCategory = KeyBinding.Category.create(Identifier.of("skyutils"));

    zoomKey =KeyBindingHelper.registerKeyBinding(new
    KeyBinding(
                "key.skyutils.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
                ));

    toggleFullBright =KeyBindingHelper.registerKeyBinding(new
    KeyBinding(
                "key.skyutils.toggleFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
                ));

    incrementFullBright =KeyBindingHelper.registerKeyBinding(new
    KeyBinding(
                "key.skyutils.incrementFullBright",
                InputUtil.Type.KEYSYM,
                 GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
                ));

    decrementFullBright =KeyBindingHelper.registerKeyBinding(new
    KeyBinding(
                "key.skyutils.decrementFullBright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                skyutilsCategory
                ));
    }
}
