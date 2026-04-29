package net.notnightsky.utility.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.notnightsky.utility.config.config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityHitboxDebugRenderer.class)
public class EntityHitBoxDebugRendererMixin {
    @Unique
    private Minecraft client = Minecraft.getInstance();
    @ModifyVariable(
            method = "showHitboxes",
            at = @At(value = "STORE", ordinal = 0),
            ordinal = 0
    )
    private int modifyHitboxColor(int originalColor, Entity entity, float tickProgress, boolean inLocalServer) {
        if (config.reachHitbox && isLookingAtValidTarget()) {
            HitResult hitResult = this.client.hitResult;
            if (hitResult instanceof EntityHitResult entityHit){
                if (entity == entityHit.getEntity()){
                    return config.hitboxcolor.getRGB();
                }
            }
        }
        return originalColor;
    }

    @Unique
    private boolean isLookingAtValidTarget() {
        if (this.client == null || this.client.player == null) {
            return false;
        }

        HitResult hitResult = this.client.hitResult;
        if (hitResult instanceof EntityHitResult entityHit) {
            Entity target = entityHit.getEntity();
            if (target instanceof LivingEntity livingTarget) {
                return livingTarget.isAlive();
            }
        }
        return false;
    }
}
