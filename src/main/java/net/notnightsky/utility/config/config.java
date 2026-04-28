package net.notnightsky.utility.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.utility.handlers.keybindingNtoggleHandler;
import net.notnightsky.utility.modules.zoom.InterpolationMode;

public class config {
    public static ConfigClassHandler<config> HANDLER = ConfigClassHandler.createBuilder(config.class)
            .id(Identifier.of("utility"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("utilitybysky.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public static InterpolationMode interpolationType = InterpolationMode.LOGARITHMIC;

    @SerialEntry
    public static double zoomSpeed = 12.0;

    @SerialEntry
    public static double defaultZoom = 3.0;
    
    @SerialEntry
    public static boolean fullBright = false;

    @SerialEntry
    public static double defaultGamma = 100.0;

    @SerialEntry
    public static double gamma = 500.0;

    @SerialEntry
    public static double increment = 50.0;

    @SerialEntry
    public static double decrement = 50.0;

    @SerialEntry
    public static boolean nodarkness  = false;

    public static Screen yaclScreen(Screen parent){
        var screen = YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Utilities"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Settings"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Fullbright"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("Fullbright"))
                                        .description(OptionDescription.of(Text.literal("Enabled or Disabled")))
                                        .binding(false, () -> config.fullBright, keybindingNtoggleHandler::setFullbright)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.literal("No Darkness effect"))
                                        .description(OptionDescription.of(Text.literal("Removes darkness effect")))
                                        .binding(false, () -> config.nodarkness, newVal -> config.nodarkness = newVal)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .trueFalseFormatter()
                                                .coloured(true))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.literal("Brightness percentage"))
                                        .description(OptionDescription.of(Text.literal("Brightness percentage when fullbright is enabled")))
                                        .binding(1000.0, () -> config.gamma, newVal -> config.gamma = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(100.0, 1000.0)
                                                .step(100.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.literal("Default Brightness"))
                                        .description(OptionDescription.of(Text.literal("The brightness percentage the brightness setting is set to when fullbright is disabled")))
                                        .binding(100.0, () -> config.defaultGamma, newVal -> config.defaultGamma = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(0.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.literal("Increment"))
                                        .description(OptionDescription.of(Text.literal("The amount of brightness percent increased by pressing the keybind")))
                                        .binding(50.0, () -> config.increment, newVal -> config.increment = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(10.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.literal("Decrement"))
                                        .description(OptionDescription.of(Text.literal("The amount of brightness percent decreased by pressing the keybind")))
                                        .binding(50.0, () -> config.decrement, newVal -> config.decrement = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(10.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .collapsed(true)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Zoom"))
                                .collapsed(true)
                                .option(Option.<Double>createBuilder()
                                        .name(Text.literal("Default Zoom Level"))
                                        .description(OptionDescription.of(Text.literal("The zoom level set to when the keybind is pressed")))
                                        .binding(3.0, () -> config.defaultZoom, newVal -> config.defaultZoom = newVal)
                                        .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(1.0,50.0))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.literal("Zoom Speed"))
                                        .description(OptionDescription.of(Text.literal("Zoom speed for the zooming animation")))
                                        .binding(12.0, () -> config.zoomSpeed, newVal -> config.zoomSpeed = newVal)
                                        .controller(opt -> DoubleFieldControllerBuilder.create(opt).range(1.0,50.0))
                                        .build())
                                .option(Option.<InterpolationMode>createBuilder()
                                        .name(Text.literal("InterpolationType"))
                                        .description(OptionDescription.of(Text.literal("The type of animation used")))
                                        .binding(InterpolationMode.LOGARITHMIC, () -> config.interpolationType, newVal -> config.interpolationType = newVal)
                                        .controller(opt -> EnumControllerBuilder.create(opt)
                                                .enumClass(InterpolationMode.class)
                                                .formatValue(v -> Text.literal(v.name().toLowerCase())))
                                        .build())
                                .build())
                        .build())
                .build();
        return screen.generateScreen(parent);
    }
}
