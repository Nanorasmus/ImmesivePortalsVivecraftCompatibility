package me.nanorasmus.nanodev.ipvr_compat.fabric;

import me.nanorasmus.nanodev.ipvr_compat.IPVRCompat;
import net.fabricmc.api.ModInitializer;

public class IPVRCompatFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        IPVRCompat.init();
    }
}