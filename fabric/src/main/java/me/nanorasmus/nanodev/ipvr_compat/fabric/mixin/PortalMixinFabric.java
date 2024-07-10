package me.nanorasmus.nanodev.ipvr_compat.fabric.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.api_beta.VivecraftAPI;
import qouteall.imm_ptl.core.McHelper;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.teleportation.ServerTeleportationManager;

import java.util.UUID;

@Mixin(ServerTeleportationManager.class)
public abstract class PortalMixinFabric {
    @Shadow public abstract void recordLastPosition(ServerPlayerEntity par1);

    @Shadow protected abstract Portal findPortal(RegistryKey<World> par1, UUID par2);

    @Shadow protected abstract boolean canPlayerTeleport(ServerPlayerEntity par1, RegistryKey<World> par2, Vec3d par3, Entity par4);

    @Inject(method = "onPlayerTeleportedInClient", at = @At("HEAD"), cancellable = true)
    void onPlayerTeleportedInClient(ServerPlayerEntity player, RegistryKey<World> dimensionBefore, Vec3d oldEyePos, UUID portalId, CallbackInfo ci) {
        if (VivecraftAPI.getInstance().isVRPlayer(player)) {
            recordLastPosition(player);

            Portal portal = findPortal(dimensionBefore, portalId);

            Vec3d oldFeetPos = oldEyePos.subtract(McHelper.getEyeOffset(player));

            // Verify teleportation, prevent a hacked client from teleporting through any portal.
            // Well I guess no one will make the hacked ImmPtl client.
            if (canPlayerTeleport(player, dimensionBefore, oldFeetPos, portal)) {
                Vec3d destination = portal.getDestPos();
                player.teleport((ServerWorld) portal.getDestWorld(), destination.x, destination.y, destination.z, player.headYaw, player.prevPitch);
            }
            ci.cancel();
        }
    }
}