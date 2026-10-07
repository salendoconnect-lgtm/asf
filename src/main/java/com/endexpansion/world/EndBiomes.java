package com.endexpansion.world;

import com.endexpansion.EndExpansion;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;

/** Runtime biome definitions for the outer End.
 *  1.20.1 treats biomes as dynamic registry entries, so these are kept as
 *  direct entries instead of incorrectly using Registries.BIOME (which does
 *  not exist in this Yarn version).
 */
public final class EndBiomes {
    public static RegistryKey<Biome> END_WASTES, ENDER_FORESTS, PURPLE_SWAMPS, CRYSTAL_CAVES, FLOATING_ISLANDS, END_ABYSS;
    private static Biome END_WASTES_BIOME, ENDER_FORESTS_BIOME, PURPLE_SWAMPS_BIOME, CRYSTAL_CAVES_BIOME, FLOATING_ISLANDS_BIOME, END_ABYSS_BIOME;

    public static void register() {
        END_WASTES = key("ender_wastes");
        ENDER_FORESTS = key("ender_forests");
        PURPLE_SWAMPS = key("purple_swamps");
        CRYSTAL_CAVES = key("crystal_caves");
        FLOATING_ISLANDS = key("floating_islands");
        END_ABYSS = key("end_abyss");

        END_WASTES_BIOME = create(0x160D24, 0x281040);
        ENDER_FORESTS_BIOME = create(0x0E0618, 0x3B155A);
        PURPLE_SWAMPS_BIOME = create(0x12051C, 0x4D1A66);
        CRYSTAL_CAVES_BIOME = create(0x080812, 0x552B77);
        FLOATING_ISLANDS_BIOME = create(0x100817, 0x32204A);
        END_ABYSS_BIOME = create(0x030006, 0x100014);
    }

    private static RegistryKey<Biome> key(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(EndExpansion.MOD_ID, id));
    }

    private static Biome create(int fog, int sky) {
        SpawnSettings.Builder sp = new SpawnSettings.Builder();
        GenerationSettings gen = new GenerationSettings.Builder().build();
        BiomeEffects fx = new BiomeEffects.Builder()
                .fogColor(fog).skyColor(sky).waterColor(0x24103F)
                .waterFogColor(0x0B0310).foliageColor(0x4B1468)
                .grassColor(0x351044).build();
        return new Biome.Builder()
                .precipitation(false)
                .temperature(0.8f)
                .downfall(0f)
                .effects(fx)
                .spawnSettings(sp.build())
                .generationSettings(gen)
                .build();
    }

    public static Biome biome(RegistryKey<Biome> key) {
        if (key == END_WASTES) return END_WASTES_BIOME;
        if (key == ENDER_FORESTS) return ENDER_FORESTS_BIOME;
        if (key == PURPLE_SWAMPS) return PURPLE_SWAMPS_BIOME;
        if (key == CRYSTAL_CAVES) return CRYSTAL_CAVES_BIOME;
        if (key == FLOATING_ISLANDS) return FLOATING_ISLANDS_BIOME;
        return END_ABYSS_BIOME;
    }

    private EndBiomes() {}
}
