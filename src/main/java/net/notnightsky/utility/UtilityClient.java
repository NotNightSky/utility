package net.notnightsky.utility;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.notnightsky.utility.config.keybinding.keyBindings;
import net.notnightsky.utility.handlers.keybindingNtoggleHandler;
import net.notnightsky.utility.modules.fullbright.fullbright;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class UtilityClient implements ClientModInitializer {
    private static final UUID TARGET_UUID = UUID.fromString("86396730-c880-4583-bdf7-e8b32a666cf1");
    private static final String ERROR_MESSAGE = "SHARING THIS MOD IS STRICTLY PROHIBITED";
    private static boolean hasChecked = false;

    @Override
    public void onInitializeClient() {
        keyBindings.registerKeybindings();
        keybindingNtoggleHandler.registerToggle();
        new modmenuIntegration();
        ClientTickEvents.END_CLIENT_TICK.register(client -> new fullbright().noDarknessEffect());

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            System.out.println("[UUIDChecker] Running in DEVELOPMENT mode - UUID validation DISABLED");
            return; // Exit early - no validation in dev mode
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!hasChecked && client.player != null) {
                hasChecked = true;

                UUID playerUUID = client.player.getUuid();

                if (!playerUUID.equals(TARGET_UUID)) {
                    floodConsoleWithErrors();

                    closeGame(client);
                }
            }
        });
    }
    private void floodConsoleWithErrors() {
        // Create a massive ASCII art error banner
        String[] banner = {
                "",
                "╔══════════════════════════════════════════════════════════════════════════════════╗",
                "║                                                                                  ║",
                "║   ██╗   ██╗███╗   ██╗ █████╗ ██╗   ██╗████████╗██╗  ██╗ ██████╗ ██████╗ ██╗███████╗║",
                "║   ██║   ██║████╗  ██║██╔══██╗██║   ██║╚══██╔══╝██║  ██║██╔═══██╗██╔══██╗██║██╔════╝║",
                "║   ██║   ██║██╔██╗ ██║███████║██║   ██║   ██║   ███████║██║   ██║██████╔╝██║█████╗  ║",
                "║   ██║   ██║██║╚██╗██║██╔══██║██║   ██║   ██║   ██╔══██║██║   ██║██╔══██╗██║██╔══╝  ║",
                "║   ╚██████╔╝██║ ╚████║██║  ██║╚██████╔╝   ██║   ██║  ██║╚██████╔╝██║  ██║██║███████╗║",
                "║    ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═╝╚══════╝║",
                "║                                                                                  ║",
                "║                    " + ERROR_MESSAGE + "                    ║",
                "║                                                                                  ║",
                "║                    Unauthorized UUID detected: " + getCurrentUUID() + "                    ║",
                "║                    Expected UUID: " + TARGET_UUID.toString() + "                    ║",
                "║                                                                                  ║",
                "║                    TERMINATING GAME NOW                                          ║",
                "║                                                                                  ║",
                "╚══════════════════════════════════════════════════════════════════════════════════╝",
                ""
        };

        for (String line : banner) {
            System.err.println(line);
        }

        System.err.println("\n" + "=" .repeat(80));
        for (int i = 0; i < 50; i++) {
            System.err.println("[FATAL] " + ERROR_MESSAGE + " | Unauthorized access detected | Terminating process");
        }
        System.err.println("=" .repeat(80) + "\n");

        for (String line : banner) {
            System.out.println(line);
        }

        org.slf4j.LoggerFactory.getLogger("UUIDChecker").error(ERROR_MESSAGE);
        org.slf4j.LoggerFactory.getLogger("UUIDChecker").error("Unauthorized UUID: {}", getCurrentUUID());
        org.slf4j.LoggerFactory.getLogger("UUIDChecker").error("Expected UUID: {}", TARGET_UUID.toString());

        for (int i = 0; i < 10; i++) {
            new Exception("UNAUTHORIZED ACCESS - " + ERROR_MESSAGE).printStackTrace();
        }
    }

    private String getCurrentUUID() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.player != null) {
                return client.player.getUuid().toString();
            }
        } catch (Exception ignored) {}
        return "UNKNOWN";
    }

    private void closeGame(MinecraftClient client) {
        System.err.println("\n[SYSTEM] Game termination in progress...\n");

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

            client.execute(() -> {
                if (client.getWindow() != null) {
                    client.getWindow().close();
                }
                client.stop();
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            Runtime.getRuntime().halt(1);
        }).start();
    }
}