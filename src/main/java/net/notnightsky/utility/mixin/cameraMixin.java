package net.notnightsky.utility.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import net.notnightsky.utility.modules.zoom.zoomHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public class cameraMixin {
    @ModifyReturnValue(method = "calculateFov", at = @At(value = "RETURN"))
    private float calculateFov(float original) {
        return zoomHelper.changeFovOnZoom(original);
    }

    @ModifyReturnValue(method = "calculateHudFov", at = @At("RETURN"))
    private float modifyHudFovWithZoom(float original) {
        return zoomHelper.changeFovOnZoom(original);
    }
}
