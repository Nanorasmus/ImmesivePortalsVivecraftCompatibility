package me.nanorasmus.nanodev.ipvr_compat.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(me.nanorasmus.nanodev.ipvr_compat.IPVRCompat.MOD_ID)
public class IPVRCompatForge {
    public IPVRCompatForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(me.nanorasmus.nanodev.ipvr_compat.IPVRCompat.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        me.nanorasmus.nanodev.ipvr_compat.IPVRCompat.init();
    }
}