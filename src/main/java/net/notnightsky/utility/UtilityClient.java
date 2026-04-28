package net.notnightsky.utility;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.notnightsky.utility.config.keybinding.keyBindings;
import net.notnightsky.utility.handlers.keybindingNtoggleHandler;
import net.notnightsky.utility.modules.fullbright.fullbright;

public class UtilityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        keyBindings.registerKeybindings();
        keybindingNtoggleHandler.registerToggle();
        new modmenuIntegration();
        ClientTickEvents.END_CLIENT_TICK.register(client -> new fullbright().noDarknessEffect());
    }
}