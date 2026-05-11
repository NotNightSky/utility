package net.notnightsky.utility.config.presetSystem;

import net.minecraft.client.Minecraft;

public class VanillaOptions {
    public Double gamma;
    public Integer renderDistance;
    public Double sensitivity;
    public Integer fov;
    public Double mouseWheelSensitivity;
    public Boolean enableVsync;
    public Boolean smoothCamera;
    public Boolean bobView;
    public Boolean autoJump;
    public Boolean rawMouseInput;
    public Double entityDistanceScaling;
    public Integer framerateLimit;
    public Double chatScale;
    public Double chatOpacity;
    public Double chatLineSpacing;
    public Double textBackgroundOpacity;
    public Integer guiScale;
    public Boolean forceUnicodeFont;

    public VanillaOptions() {}

    public static VanillaOptions capture() {
        VanillaOptions opts = new VanillaOptions();
        try {
            var options = Minecraft.getInstance().options;

            opts.gamma = (Double) options.gamma().get();
            opts.renderDistance = (Integer) options.renderDistance().get();
            opts.sensitivity = (Double) options.sensitivity().get();
            opts.fov = (Integer) options.fov().get();
            opts.mouseWheelSensitivity = (Double) options.mouseWheelSensitivity().get();
            opts.enableVsync = (Boolean) options.enableVsync().get();
            opts.smoothCamera = options.smoothCamera;
            opts.bobView = (Boolean) options.bobView().get();
            opts.autoJump = (Boolean) options.autoJump().get();
            opts.rawMouseInput = (Boolean) options.rawMouseInput().get();
            opts.entityDistanceScaling = (Double) options.entityDistanceScaling().get();
            opts.framerateLimit = (Integer) options.framerateLimit().get();
            opts.chatScale = (Double) options.chatScale().get();
            opts.chatOpacity = (Double) options.chatOpacity().get();
            opts.chatLineSpacing = (Double) options.chatLineSpacing().get();
            opts.textBackgroundOpacity = (Double) options.textBackgroundOpacity().get();
            opts.guiScale = (Integer) options.guiScale().get();
            opts.forceUnicodeFont = (Boolean) options.forceUnicodeFont().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return opts;
    }

    public void apply() {
        try {
            var options = Minecraft.getInstance().options;

            if (gamma != null) options.gamma().set(gamma);
            if (renderDistance != null) options.renderDistance().set(renderDistance);
            if (sensitivity != null) options.sensitivity().set(sensitivity);
            if (fov != null) options.fov().set(fov);
            if (mouseWheelSensitivity != null) options.mouseWheelSensitivity().set(mouseWheelSensitivity);
            if (enableVsync != null) options.enableVsync().set(enableVsync);
            if (smoothCamera != null) options.smoothCamera = smoothCamera;
            if (bobView != null) options.bobView().set(bobView);
            if (autoJump != null) options.autoJump().set(autoJump);
            if (rawMouseInput != null) options.rawMouseInput().set(rawMouseInput);
            if (entityDistanceScaling != null) options.entityDistanceScaling().set(entityDistanceScaling);
            if (framerateLimit != null) options.framerateLimit().set(framerateLimit);
            if (chatScale != null) options.chatScale().set(chatScale);
            if (chatOpacity != null) options.chatOpacity().set(chatOpacity);
            if (chatLineSpacing != null) options.chatLineSpacing().set(chatLineSpacing);
            if (textBackgroundOpacity != null) options.textBackgroundOpacity().set(textBackgroundOpacity);
            if (guiScale != null) options.guiScale().set(guiScale);
            if (forceUnicodeFont != null) options.forceUnicodeFont().set(forceUnicodeFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}