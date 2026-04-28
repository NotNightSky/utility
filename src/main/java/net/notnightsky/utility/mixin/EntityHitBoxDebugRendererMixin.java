package net.notnightsky.utility.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.debug.EntityHitboxDebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.notnightsky.utility.config.config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityHitboxDebugRenderer.class)
public class EntityHitBoxDebugRendererMixin {
    private MinecraftClient client = MinecraftClient.getInstance();
    @ModifyVariable(
            method = "drawHitbox",
            at = @At(value = "STORE", ordinal = 0),
            ordinal = 0
    )
    private int modifyHitboxColor(int originalColor, Entity entity, float tickProgress, boolean inLocalServer) {
        if (config.reachHitbox && isLookingAtValidTarget()) {
            return config.hitboxcolor.getRGB();
        }
        return originalColor;
    }

    private boolean isLookingAtValidTarget() {
        if (this.client == null || this.client.player == null) {
            return false;
        }

        HitResult hitResult = this.client.crosshairTarget;
        if (hitResult instanceof EntityHitResult entityHit) {
            Entity target = entityHit.getEntity();
            if (target instanceof LivingEntity livingTarget) {
                return livingTarget.isAlive();
            }
        }
        return false;
    }
}
