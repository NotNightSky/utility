package net.notnightsky.utility.modules.reach;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class rayCasting{
    public static EntityHitResult rayCastingMethod(double reachDistance) {
        MinecraftClient client = MinecraftClient.getInstance();
        Entity camera = client.getCameraEntity();

        if (camera == null) return null;

        Vec3d eyePos = camera.getEyePos();
        Vec3d lookVec = camera.getRotationVec(1.0F);
        Vec3d endPos = eyePos.add(lookVec.multiply(reachDistance));

        Box searchBox = camera.getBoundingBox().stretch(lookVec.multiply(reachDistance)).expand(1.0);

        return ProjectileUtil.raycast(
                camera,
                eyePos,
                endPos,
                searchBox,
                entity -> !entity.isSpectator() && entity.canHit(),
                reachDistance * reachDistance
        );
    }
}

