package net.notnightsky.utility.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.notnightsky.utility.config.config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public class InGameHudMixin {
    @Unique
    private final Minecraft client = Minecraft.getInstance();

    @Inject(method = "renderCrosshair", at = @At(value = "HEAD"), cancellable = true)
    private void changeCrosshairColor(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (!config.reachCrosshair){
            return;
        }

        if (this.client.player == null || this.client.level == null) {
            return;
        }

        Options options = this.client.options;
        if (!options.getCameraType().isFirstPerson()) {
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

    @Unique
    private void renderColoredCrosshair(GuiGraphics context, int color) {
        int centerX = context.guiWidth() / 2;
        int centerY = context.guiHeight() / 2;
        int crosshairSize = 4;
        int thickness = 1;
        int gap = 0;

        // Draw the four crosshair lines
        context.fill(centerX - crosshairSize - gap, centerY, centerX - gap, centerY + thickness, color);
        context.fill(centerX + gap, centerY, centerX + crosshairSize + gap + 1, centerY + thickness, color);
        context.fill(centerX, centerY - crosshairSize - gap, centerX + thickness, centerY - gap, color);
        context.fill(centerX, centerY + gap, centerX + thickness, centerY + crosshairSize + gap + 1, color);
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

    @Unique
    private void renderAttackIndicator(GuiGraphics context) {
        if (this.client.player == null) {
            return;
        }

        float attackCooldown = this.client.player.getAttackStrengthScale(0f);
        if (attackCooldown >= 1f) {
            return;
        }

        int centerX = context.guiWidth() / 2;
        int centerY = context.guiHeight() / 2;
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
