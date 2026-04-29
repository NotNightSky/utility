package net.notnightsky.utility.handlers;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.notnightsky.utility.config.presetSystem.PresetManager;
import net.notnightsky.utility.config.config;

import static net.notnightsky.utility.config.keybinding.keyBindings.*;
import static net.notnightsky.utility.modules.fullbright.fullbright.decrementFullBright;
import static net.notnightsky.utility.modules.fullbright.fullbright.disableFullbright;
import static net.notnightsky.utility.modules.fullbright.fullbright.enableFullbright;
import static net.notnightsky.utility.modules.fullbright.fullbright.incrementFullBright;

public class keybindingNtoggleHandler {
    public static void registerToggle() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (!loaded && Minecraft.getInstance().options != null) {
                loaded = true;
                if (config.fullBright) {
                    enableFullbright();
                }
            }

            if (incrementFullBright.consumeClick()) {
                incrementFullBright();
            }

            if (decrementFullBright.consumeClick()) {
                decrementFullBright();
            }

            if (toggleFullBright.consumeClick()) {
                toggleFullbright();
            }

            if (cyclePreset.consumeClick()) {
                PresetManager.cycleNext();
            }

            if (previousPreset.consumeClick()) {
                PresetManager.cyclePrevious();
            }
        });
    }

    public static void toggleFullbright() {
        if (config.fullBright) {
            disableFullbright();
        } else {
            enableFullbright();
        }
        config.HANDLER.save();
    }

    public static void setFullbright(boolean fullbright) {
        if (fullbright) {
            enableFullbright();
        } else {
            disableFullbright();
        }
        config.HANDLER.save();
    }

}
