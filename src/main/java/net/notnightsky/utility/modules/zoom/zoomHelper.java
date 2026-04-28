package net.notnightsky.utility.modules.zoom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.notnightsky.utility.config.config;
import net.notnightsky.utility.config.keybinding.keyBindings;


public class zoomHelper {

    private static final double defaultZoom = config.defaultZoom;
    private static final double lerpSpeed = config.zoomSpeed;

    private static double baseZoom = 1.0;
    private static double scrollZoom = 1.0;
    private static double scrollVelocity = 0.0;

    private static double currentLevel = 1.0;
    private static double targetLevel  = 1.0;

    private static Double defaultMouseSensitivity;

    public static float changeFovOnZoom(float fov) {
        SimpleOption<Double> mouseSensitivitySetting = MinecraftClient.getInstance().options.getMouseSensitivity();
        baseZoom = keyBindings.zoomKey.isPressed() ? defaultZoom : 1.0;
        if(keyBindings.zoomKey.isPressed()){

            baseZoom = defaultZoom;
        } else {
            baseZoom = 1.0;
            if(defaultMouseSensitivity != null)
            {
                mouseSensitivitySetting.setValue(defaultMouseSensitivity);
                defaultMouseSensitivity = null;
            }
        }

        if(defaultMouseSensitivity == null)
            defaultMouseSensitivity = mouseSensitivitySetting.getValue();

        mouseSensitivitySetting.setValue(defaultMouseSensitivity * (1.0 / currentLevel));

        return (float) (fov / currentLevel);
    }

    public static void onMouseScroll(double vert) {
        if (!keyBindings.zoomKey.isPressed()) return;
        scrollVelocity += vert * 2.5;
    }

    public static void tick() {
        double dt = DeltaTime.getSec();

        if (Math.abs(scrollVelocity) > 1e-4) {
            scrollZoom *= Math.exp(scrollVelocity * dt);
            scrollZoom = Math.clamp(scrollZoom, 1.0 / defaultZoom, 50.0 / defaultZoom);
        }

        scrollVelocity *= Math.exp(-12.0 * dt);

        if (!keyBindings.zoomKey.isPressed()) {
            scrollZoom = 1.0;
        }

        targetLevel = baseZoom * scrollZoom;

        if (config.interpolationType != InterpolationMode.INSTANT){
            currentLevel = math.interpolate(currentLevel, targetLevel, lerpSpeed, dt, config.interpolationType);
        } else {
            currentLevel = targetLevel;
        }
    }
}
