package net.notnightsky.utility.handlers;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.notnightsky.utility.config.config;

import static net.notnightsky.utility.config.keybinding.keyBindings.*;
import static net.notnightsky.utility.modules.fullbright.fullbright.decrementFullBright;
import static net.notnightsky.utility.modules.fullbright.fullbright.disableFullbright;
import static net.notnightsky.utility.modules.fullbright.fullbright.enableFullbright;
import static net.notnightsky.utility.modules.fullbright.fullbright.incrementFullBright;

public class keybindingNtoggleHandler {
    public static void registerToggle(){
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if(!loaded && MinecraftClient.getInstance().options != null) {
                loaded = true;
                if(config.fullBright) {
                    enableFullbright();
                }
            }

            if (incrementFullBright.wasPressed()){
                incrementFullBright();
            }

            if (decrementFullBright.wasPressed()){
                decrementFullBright();
            }

            if (toggleFullBright.wasPressed()) {
                toggleFullbright();
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
