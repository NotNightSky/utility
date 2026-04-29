package net.notnightsky.utility.modules.fullbright;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.notnightsky.utility.config.config;

public class fullbright {
    public static void enableFullbright(){
        Minecraft.getInstance().options.gamma().set(config.gamma / 100.0);
        if (Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value is Set to " + (int) config.gamma + "%"));
        }
        config.fullBright = true;
    }

    public static void disableFullbright(){
        Minecraft.getInstance().options.gamma().set(config.defaultGamma / 100.0);
        if (Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value is Set to " + (int)config.defaultGamma + "%"));
        }
        config.fullBright = false;
    }

    public static void incrementFullBright(){
        if(config.fullBright){
            if( !(config.gamma > 1000.0 || config.gamma + config.increment > 1000.0)) {
                config.gamma = config.gamma + config.increment;
                enableFullbright();
            } else if (config.gamma <= 1000.0){
                config.gamma = 1000.0;
                if (Minecraft.getInstance().player != null){
                    Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value Reached 1000%"));
                }
            }
            config.HANDLER.save();
        }
    }

    public static void decrementFullBright(){
        if(config.fullBright){
            if( !(config.gamma < 0.0 || config.gamma - config.increment < 0.0)) {
                config.gamma = config.gamma - config.decrement;
                enableFullbright();
            } else if (config.gamma < 0.0){
                config.gamma = 0.0;
                if (Minecraft.getInstance().player != null){
                    Minecraft.getInstance().player.sendOverlayMessage(Component.nullToEmpty("Gamma Value Reached 1%"));
                }
            }
            config.HANDLER.save();
        }
    }

    public void noDarknessEffect(){
        if (config.nodarkness){
            if (Minecraft.getInstance().player != null) {
                MobEffectInstance darknessEffect = Minecraft.getInstance().player.getEffect(MobEffects.DARKNESS);
                if (darknessEffect != null) {
                    Minecraft.getInstance().player.removeEffect(MobEffects.DARKNESS);
                }
            }
        }
    }
}
