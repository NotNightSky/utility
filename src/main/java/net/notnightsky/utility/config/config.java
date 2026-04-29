package net.notnightsky.utility.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.notnightsky.utility.config.presetSystem.Preset;
import net.notnightsky.utility.config.presetSystem.PresetManager;
import net.notnightsky.utility.handlers.keybindingNtoggleHandler;
import net.notnightsky.utility.modules.zoom.InterpolationMode;

import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class config {
    public static ConfigClassHandler<config> HANDLER = ConfigClassHandler.createBuilder(config.class)
            .id(Identifier.parse("utility"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("utilitybysky.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry public static boolean reachHitbox = false;
    @SerialEntry public static boolean reachCrosshair = false;
    @SerialEntry public static Color hitboxcolor = new Color(255, 255, 255, 255);
    @SerialEntry public static Color crosscolor = new Color(255, 255, 255, 255);
    @SerialEntry public static InterpolationMode interpolationType = InterpolationMode.LOGARITHMIC;
    @SerialEntry public static double zoomSpeed = 12.0;
    @SerialEntry public static double defaultZoom = 3.0;
    @SerialEntry public static boolean fullBright = false;
    @SerialEntry public static double defaultGamma = 100.0;
    @SerialEntry public static double gamma = 500.0;
    @SerialEntry public static double increment = 50.0;
    @SerialEntry public static double decrement = 50.0;
    @SerialEntry public static boolean nodarkness = false;

    public static Screen yaclScreen(Screen parent) {

        Option<Boolean> fullBrightOpt = Option.<Boolean>createBuilder()
                .name(Component.literal("Fullbright"))
                .description(OptionDescription.of(Component.literal("Enabled or Disabled")))
                .binding(false, () -> config.fullBright, keybindingNtoggleHandler::setFullbright)
                .controller(opt -> BooleanControllerBuilder.create(opt)
                        .trueFalseFormatter()
                        .coloured(true))
                .build();

        Option<Boolean> nodarkOpt = Option.<Boolean>createBuilder()
                .name(Component.literal("No Darkness effect"))
                .description(OptionDescription.of(Component.literal("Removes darkness effect")))
                .binding(false, () -> config.nodarkness, newVal -> config.nodarkness = newVal)
                .controller(opt -> BooleanControllerBuilder.create(opt)
                        .trueFalseFormatter()
                        .coloured(true))
                .build();

        Option<Double> gammaOpt = Option.<Double>createBuilder()
                .name(Component.literal("Brightness percentage"))
                .description(OptionDescription.of(Component.literal("Brightness percentage when fullbright is enabled")))
                .binding(1000.0, () -> config.gamma, newVal -> config.gamma = newVal)
                .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                        .range(100.0, 1000.0)
                        .step(100.0)
                        .formatValue(val -> Component.literal(val.intValue() + "%")))
                .build();

        Option<Double> defaultGammaOpt = Option.<Double>createBuilder()
                .name(Component.literal("Default Brightness"))
                .description(OptionDescription.of(Component.literal("Brightness when fullbright is disabled")))
                .binding(100.0, () -> config.defaultGamma, newVal -> config.defaultGamma = newVal)
                .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                        .range(0.0, 100.0)
                        .step(10.0)
                        .formatValue(val -> Component.literal(val.intValue() + "%")))
                .build();

        Option<Double> incrementOpt = Option.<Double>createBuilder()
                .name(Component.literal("Increment"))
                .description(OptionDescription.of(Component.literal("Brightness increased per keybind press")))
                .binding(50.0, () -> config.increment, newVal -> config.increment = newVal)
                .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                        .range(10.0, 100.0)
                        .step(10.0)
                        .formatValue(val -> Component.literal(val.intValue() + "%")))
                .build();

        Option<Double> decrementOpt = Option.<Double>createBuilder()
                .name(Component.literal("Decrement"))
                .description(OptionDescription.of(Component.literal("Brightness decreased per keybind press")))
                .binding(50.0, () -> config.decrement, newVal -> config.decrement = newVal)
                .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                        .range(10.0, 100.0)
                        .step(10.0)
                        .formatValue(val -> Component.literal(val.intValue() + "%")))
                .build();

        Option<Double> defaultZoomOpt = Option.<Double>createBuilder()
                .name(Component.literal("Default Zoom Level"))
                .description(OptionDescription.of(Component.literal("Zoom level when keybind is pressed")))
                .binding(3.0, () -> config.defaultZoom, newVal -> config.defaultZoom = newVal)
                .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(1.0, 50.0))
                .build();

        Option<Double> zoomSpeedOpt = Option.<Double>createBuilder()
                .name(Component.literal("Zoom Speed"))
                .description(OptionDescription.of(Component.literal("Speed of the zoom animation")))
                .binding(12.0, () -> config.zoomSpeed, newVal -> config.zoomSpeed = newVal)
                .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(1.0, 50.0))
                .build();

        Option<InterpolationMode> interpolationOpt = Option.<InterpolationMode>createBuilder()
                .name(Component.literal("InterpolationType"))
                .description(OptionDescription.of(Component.literal("Animation type used for zoom")))
                .binding(InterpolationMode.LOGARITHMIC, () -> config.interpolationType, newVal -> config.interpolationType = newVal)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(InterpolationMode.class)
                        .formatValue(v -> Component.literal(v.name().toLowerCase())))
                .build();

        Option<Boolean> reachCrosshairOpt = Option.<Boolean>createBuilder()
                .name(Component.literal("Reachable Crosshair"))
                .description(OptionDescription.of(Component.literal("Changes crosshair color when aimed at a living entity")))
                .binding(false, () -> config.reachCrosshair, newVal -> config.reachCrosshair = newVal)
                .controller(opt -> BooleanControllerBuilder.create(opt)
                        .trueFalseFormatter()
                        .coloured(true))
                .build();

        Option<Boolean> reachHitboxOpt = Option.<Boolean>createBuilder()
                .name(Component.literal("Reachable Hitbox"))
                .description(OptionDescription.of(Component.literal("Changes hitbox color when aimed at a living entity")))
                .binding(false, () -> config.reachHitbox, newVal -> config.reachHitbox = newVal)
                .controller(opt -> BooleanControllerBuilder.create(opt)
                        .trueFalseFormatter()
                        .coloured(true))
                .build();

        Option<Color> crosscolorOpt = Option.<Color>createBuilder()
                .name(Component.literal("Crosshair color"))
                .binding(new Color(255, 255, 255, 255), () -> config.crosscolor, newVal -> config.crosscolor = newVal)
                .controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(true))
                .build();

        Option<Color> hitboxcolorOpt = Option.<Color>createBuilder()
                .name(Component.literal("Hitbox color"))
                .binding(new Color(255, 255, 255, 255), () -> config.hitboxcolor, newVal -> config.hitboxcolor = newVal)
                .controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(true))
                .build();

        Consumer<Preset> applyPreset = preset -> {
            if (preset == null) return;
            fullBrightOpt.requestSet(preset.fullBright);
            nodarkOpt.requestSet(preset.nodarkness);
            gammaOpt.requestSet(preset.gamma);
            defaultGammaOpt.requestSet(preset.defaultGamma);
            incrementOpt.requestSet(preset.increment);
            decrementOpt.requestSet(preset.decrement);
            defaultZoomOpt.requestSet(preset.defaultZoom);
            zoomSpeedOpt.requestSet(preset.zoomSpeed);
            interpolationOpt.requestSet(preset.interpolationType);
            reachCrosshairOpt.requestSet(preset.reachCrosshair);
            reachHitboxOpt.requestSet(preset.reachHitbox);
            crosscolorOpt.requestSet(preset.getCrossColor());
            hitboxcolorOpt.requestSet(preset.getHitboxColor());
        };

        Function<String, Preset> snapshotPending = name -> {
            Preset p = new Preset();
            p.name = name;
            p.fullBright = fullBrightOpt.pendingValue();
            p.nodarkness = nodarkOpt.pendingValue();
            p.gamma = gammaOpt.pendingValue();
            p.defaultGamma = defaultGammaOpt.pendingValue();
            p.increment = incrementOpt.pendingValue();
            p.decrement = decrementOpt.pendingValue();
            p.defaultZoom = defaultZoomOpt.pendingValue();
            p.zoomSpeed = zoomSpeedOpt.pendingValue();
            p.interpolationType = interpolationOpt.pendingValue();
            p.reachCrosshair = reachCrosshairOpt.pendingValue();
            p.reachHitbox = reachHitboxOpt.pendingValue();
            p.crosscolor = crosscolorOpt.pendingValue().getRGB();
            p.hitboxcolor = hitboxcolorOpt.pendingValue().getRGB();
            return p;
        };

        List<Preset> presets = PresetManager.load();

        List<String> presetNames = presets.stream()
                .map(p -> p.name)
                .collect(Collectors.toList());

        final String placeholder = "§7No presets saved";
        if (presetNames.isEmpty()) presetNames.add(placeholder);

        String currentName = presetNames.contains(PresetManager.currentPresetName)
                ? PresetManager.currentPresetName
                : presetNames.get(0);

        Option<String> presetSelectorOpt = Option.<String>createBuilder()
                .name(Component.literal("Active Preset"))
                .description(OptionDescription.of(Component.literal(
                        "Cycle through saved presets with ◀ ▶ or via keybind.\nClick Apply to load the selected preset.")))
                .binding(currentName, () -> currentName, v -> PresetManager.currentPresetName = v)
                .controller(opt -> CyclingListControllerBuilder.create(opt)
                        .values(presetNames)
                        .valueFormatter(name -> name.equals(placeholder)
                                ? Component.literal(placeholder)
                                : Component.literal("§e" + name)))
                .build();

        ButtonOption applyPresetBtn = ButtonOption.createBuilder()
                .name(Component.literal("▶  Apply Selected Preset"))
                .description(OptionDescription.of(Component.literal(
                        "Loads the selected preset into all settings below.\nYou still need to save to make it permanent.")))
                .action((screen, opt) -> {
                    String selected = presetSelectorOpt.pendingValue();
                    if (selected.equals(placeholder)) return;
                    presets.stream()
                            .filter(p -> p.name.equals(selected))
                            .findFirst()
                            .ifPresent(applyPreset);
                    PresetManager.currentPresetName = selected;
                })
                .build();

        ButtonOption deletePresetBtn = ButtonOption.createBuilder()
                .name(Component.literal("✕  Delete Selected Preset"))
                .description(OptionDescription.of(Component.literal(
                        "Permanently removes the selected preset from disk.")))
                .action((screen, opt) -> {
                    String selected = presetSelectorOpt.pendingValue();
                    if (selected.equals(placeholder)) return;
                    PresetManager.delete(selected);
                    if (PresetManager.currentPresetName.equals(selected)) {
                        PresetManager.currentPresetName = "";
                    }
                    Minecraft.getInstance().setScreen(yaclScreen(parent));
                })
                .build();

        Option<String> presetNameOpt = Option.<String>createBuilder()
                .name(Component.literal("New Preset Name"))
                .description(OptionDescription.of(Component.literal(
                        "Type a name for your preset, then click Save.")))
                .binding("", () -> "", v -> {})
                .controller(StringControllerBuilder::create)
                .build();

        ButtonOption savePresetBtn = ButtonOption.createBuilder()
                .name(Component.literal("💾  Save Current as Preset"))
                .description(OptionDescription.of(Component.literal(
                        "Saves all current pending values as a named preset.\nCaptures unsaved changes too.")))
                .action((screen, opt) -> {
                    String name = presetNameOpt.pendingValue().trim();
                    if (name.isEmpty() || name.equals(placeholder)) return;
                    PresetManager.add(snapshotPending.apply(name));
                    PresetManager.currentPresetName = name;
                    Minecraft.getInstance().setScreen(yaclScreen(parent));
                })
                .build();

        OptionGroup presetGroup = OptionGroup.createBuilder()
                .name(Component.literal("Presets"))
                .description(OptionDescription.of(Component.literal(
                        "Save and cycle through full config presets.\nUse keybinds to cycle without opening the menu.")))
                .collapsed(true)
                .option(presetSelectorOpt)
                .option(applyPresetBtn)
                .option(deletePresetBtn)
                .option(presetNameOpt)
                .option(savePresetBtn)
                .build();

        var screen = YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Utilities"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Settings"))
                        .group(presetGroup)
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Fullbright"))
                                .collapsed(true)
                                .option(fullBrightOpt)
                                .option(nodarkOpt)
                                .option(gammaOpt)
                                .option(defaultGammaOpt)
                                .option(incrementOpt)
                                .option(decrementOpt)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Zoom"))
                                .collapsed(true)
                                .option(defaultZoomOpt)
                                .option(zoomSpeedOpt)
                                .option(interpolationOpt)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Reach"))
                                .description(OptionDescription.of(Component.literal("Reach based options")))
                                .collapsed(true)
                                .option(reachCrosshairOpt)
                                .option(reachHitboxOpt)
                                .option(crosscolorOpt)
                                .option(hitboxcolorOpt)
                                .build())
                        .build())
                .build();

        return screen.generateScreen(parent);
    }
}
