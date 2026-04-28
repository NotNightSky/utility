package net.notnightsky.utility.modules.fullbright;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.notnightsky.utility.config.config;

public class fullbright {
    public static void enableFullbright(){
        MinecraftClient.getInstance().options.getGamma().setValue(config.gamma / 100.0);
        if (MinecraftClient.getInstance().player != null){
            MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value is Set to " + (int) config.gamma + "%"), true);
        }
        config.fullBright = true;
    }

    public static void disableFullbright(){
        MinecraftClient.getInstance().options.getGamma().setValue(config.defaultGamma / 100.0);
        if (MinecraftClient.getInstance().player != null){
            MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value is Set to " + (int)config.defaultGamma + "%"), true);
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
                if (MinecraftClient.getInstance().player != null){
                    MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value Reached 1000%"), true);
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
                if (MinecraftClient.getInstance().player != null){
                    MinecraftClient.getInstance().player.sendMessage(Text.of("Gamma Value Reached 1%"), true);
                }
            }
            config.HANDLER.save();
        }
    }

    public void noDarknessEffect(){
        if (config.nodarkness){
            if (MinecraftClient.getInstance().player != null) {
                StatusEffectInstance darknessEffect = MinecraftClient.getInstance().player.getStatusEffect(StatusEffects.DARKNESS);
                if (darknessEffect != null) {
                    MinecraftClient.getInstance().player.removeStatusEffect(StatusEffects.DARKNESS);
                }
            }
        }
    }
}
