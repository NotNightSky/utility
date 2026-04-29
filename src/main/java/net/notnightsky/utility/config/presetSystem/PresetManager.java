package net.notnightsky.utility.config.presetSystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.notnightsky.utility.config.config;
import net.notnightsky.utility.handlers.keybindingNtoggleHandler;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PresetManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance()
            .getConfigDir().resolve("utilitybysky_presets.json");

    public static String currentPresetName = "";

    public static List<Preset> load() {
        if (!FILE.toFile().exists()) return new ArrayList<>();
        try (Reader r = new FileReader(FILE.toFile())) {
            Type type = new TypeToken<List<Preset>>() {}.getType();
            List<Preset> list = GSON.fromJson(r, type);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void save(List<Preset> presets) {
        try (Writer w = new FileWriter(FILE.toFile())) {
            GSON.toJson(presets, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add(Preset preset) {
        List<Preset> list = load();
        list.removeIf(p -> p.name.equalsIgnoreCase(preset.name));
        list.add(preset);
        save(list);
    }

    public static void delete(String name) {
        List<Preset> list = load();
        list.removeIf(p -> p.name.equalsIgnoreCase(name));
        save(list);
    }

    public static void applyToConfig(Preset preset) {
        if (preset == null) return;
        config.nodarkness = preset.nodarkness;
        config.gamma = preset.gamma;
        config.defaultGamma = preset.defaultGamma;
        config.increment = preset.increment;
        config.decrement = preset.decrement;
        config.defaultZoom = preset.defaultZoom;
        config.zoomSpeed = preset.zoomSpeed;
        config.interpolationType = preset.interpolationType;
        config.reachCrosshair = preset.reachCrosshair;
        config.reachHitbox = preset.reachHitbox;
        config.crosscolor = preset.getCrossColor();
        config.hitboxcolor = preset.getHitboxColor();
        keybindingNtoggleHandler.setFullbright(preset.fullBright);

        config.HANDLER.save();
    }

    public static void applyByName(String name) {
        List<Preset> presets = load();
        presets.stream()
                .filter(p -> p.name.equals(name))
                .findFirst()
                .ifPresent(preset -> {
                    applyToConfig(preset);
                    sendPresetMessage(preset.name);
                });
    }


    public static void cycleNext() {
        List<Preset> presets = load();
        if (presets.isEmpty()) return;
        int idx = indexOfCurrent(presets);
        currentPresetName = presets.get((idx + 1) % presets.size()).name;
        applyByName(currentPresetName);
    }

    public static void cyclePrevious() {
        List<Preset> presets = load();
        if (presets.isEmpty()) return;
        int idx = indexOfCurrent(presets);
        currentPresetName = presets.get((idx - 1 + presets.size()) % presets.size()).name;
        applyByName(currentPresetName);
    }

    private static int indexOfCurrent(List<Preset> presets) {
        for (int i = 0; i < presets.size(); i++) {
            if (presets.get(i).name.equals(currentPresetName)) return i;
        }
        return -1;
    }

    private static void sendPresetMessage(String name) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.player.displayClientMessage(
                    Component.literal("Preset: §e" + name),
                    true
            );
        }
    }
}

