package net.notnightsky.utility.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.notnightsky.utility.modules.zoom.DeltaTime;
import net.notnightsky.utility.modules.zoom.zoomHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyReturnValue(at = @At("RETURN"), method = "getFov")
    private float onGetFov(float original) {
        return zoomHelper.changeFovOnZoom(original);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void skyutils$updateZoom(DeltaTracker tickCounter, boolean tick, CallbackInfo ci) {
        DeltaTime.update();
        zoomHelper.tick();
    }
}
