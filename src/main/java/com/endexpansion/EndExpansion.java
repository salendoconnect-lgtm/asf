package com.endexpansion;

import com.endexpansion.registry.*;
import com.endexpansion.world.EndBiomes;
import com.endexpansion.world.VoidPressureManager;
import com.endexpansion.event.EndGameplayEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EndExpansion implements ModInitializer {
    public static final String MOD_ID = "endexpansion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        EndBlocks.register();
        EndItems.register();
        EndEffects.register();
        EndEnchantments.register();
        EndParticles.register();
        EndEntities.register();
        EndBiomes.register();
        EndWorldgen.register();
        EndSounds.register();
        EndNetworking.register();
        EndAdvancements.register();
        EndGameplayEvents.register();
        ServerTickEvents.END_SERVER_TICK.register(server -> VoidPressureManager.tick(server));
        LOGGER.info("End Expansion initialized: external End progression online.");
    }
}
