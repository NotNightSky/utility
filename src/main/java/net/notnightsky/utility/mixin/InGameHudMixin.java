package net.notnightsky.utility.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.notnightsky.utility.config.config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    private MinecraftClient client;

    @Inject(method = "renderCrosshair", at = @At(value = "HEAD"), cancellable = true)
    private void changeCrosshairColor(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (!config.reachCrosshair){
            return;
        }

        if (this.client == null || this.client.player == null || this.client.world == null) {
            return;
        }

        GameOptions options = this.client.options;
        if (options == null || !options.getPerspective().isFirstPerson()) {
            return;
        }

        ci.cancel();

        boolean isAttackReady = this.isLookingAtValidTarget();

        int crosshairColor;
        if (config.reachCrosshair && isAttackReady) {
            crosshairColor = config.crosscolor.getRGB(); // Bright red for attack ready (AARRGGBB format)
        } else {
            crosshairColor = 0xBFFFFFFF; // White with some transparency
        }

        this.renderColoredCrosshair(context, crosshairColor);
        this.renderAttackIndicator(context);
    }

    private void renderColoredCrosshair(DrawContext context, int color) {
        int centerX = context.getScaledWindowWidth() / 2;
        int centerY = context.getScaledWindowHeight() / 2;
        int crosshairSize = 4;
        int thickness = 1;
        int gap = 0;

        // Draw the four crosshair lines
        context.fill(centerX - crosshairSize - gap, centerY, centerX - gap, centerY + thickness, color);
        context.fill(centerX + gap, centerY, centerX + crosshairSize + gap + 1, centerY + thickness, color);
        context.fill(centerX, centerY - crosshairSize - gap, centerX + thickness, centerY - gap, color);
        context.fill(centerX, centerY + gap, centerX + thickness, centerY + crosshairSize + gap + 1, color);
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

    private void renderAttackIndicator(DrawContext context) {
        if (this.client == null || this.client.player == null) {
            return;
        }

        float attackCooldown = this.client.player.getAttackCooldownProgress(0f);
        if (attackCooldown >= 1f) {
            return;
        }

        int centerX = context.getScaledWindowWidth() / 2;
        int centerY = context.getScaledWindowHeight() / 2;
        int indicatorWidth = 16;
        int indicatorHeight = 2;
        int indicatorOffset = 10;

        int x = centerX - indicatorWidth / 2;
        int y = centerY + indicatorOffset;

        // Draw background (dark blue-grey)
        context.fill(x, y, x + indicatorWidth, y + indicatorHeight, 0xFF808080);

        // Draw progress (white)
        int progressWidth = (int)((float)indicatorWidth * attackCooldown);
        context.fill(x, y, x + progressWidth, y + indicatorHeight, 0xFFFFFFFF);
    }
}
