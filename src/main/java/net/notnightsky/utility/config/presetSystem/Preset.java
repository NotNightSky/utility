package net.notnightsky.utility.config.presetSystem;

import net.notnightsky.utility.config.config;
import net.notnightsky.utility.modules.zoom.InterpolationMode;
import java.awt.Color;

public class Preset {
    public String name;
    public boolean fullBright;
    public boolean nodarkness;
    public double gamma;
    public double defaultGamma;
    public double increment;
    public double decrement;
    public double defaultZoom;
    public double zoomSpeed;
    public InterpolationMode interpolationType;
    public boolean reachCrosshair;
    public boolean reachHitbox;
    public int crosscolor;
    public int hitboxcolor;
    public VanillaOptions vanillaOptions;

    public Preset() {}

    public Preset(String name) {
        this.name = name;
        this.fullBright = config.fullBright;
        this.nodarkness = config.nodarkness;
        this.gamma = config.gamma;
        this.defaultGamma = config.defaultGamma;
        this.increment = config.increment;
        this.decrement = config.decrement;
        this.defaultZoom = config.defaultZoom;
        this.zoomSpeed = config.zoomSpeed;
        this.interpolationType = config.interpolationType;
        this.reachCrosshair = config.reachCrosshair;
        this.reachHitbox = config.reachHitbox;
        this.crosscolor = config.crosscolor.getRGB();
        this.hitboxcolor = config.hitboxcolor.getRGB();
        this.vanillaOptions = VanillaOptions.capture();
    }

    public Color getCrossColor() {
        return new Color(crosscolor, true);
    }

    public Color getHitboxColor() {
        return new Color(hitboxcolor, true);
    }
}

