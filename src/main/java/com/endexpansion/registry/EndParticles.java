package com.endexpansion.registry;
import com.endexpansion.EndExpansion;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
public final class EndParticles {
    public static final DefaultParticleType ENDER_AMBIENT=p("ender_ambient");
    public static final DefaultParticleType ENDER_DUST=p("ender_dust");
    public static final DefaultParticleType ENDER_ENERGY=p("ender_energy");
    public static final DefaultParticleType VOID_PARTICLE=p("void_particle");
    public static final DefaultParticleType VOID_SMOKE=p("void_smoke");
    public static final DefaultParticleType VOID_SPARK=p("void_spark");
    public static final DefaultParticleType CRYSTAL_SPARK=p("crystal_spark");
    public static final DefaultParticleType CRYSTAL_SHARD=p("crystal_shard");
    public static final DefaultParticleType TELEPORT_BURST=p("teleport_burst");
    public static final DefaultParticleType TELEPORT_TRAIL=p("teleport_trail");
    public static final DefaultParticleType VOID_EXPLOSION=p("void_explosion");
    public static final DefaultParticleType VOID_BEAM=p("void_beam");
    public static final DefaultParticleType ENDER_MAGIC=p("ender_magic");
    public static final DefaultParticleType BOSS_ENERGY=p("boss_energy");
    public static final DefaultParticleType REALITY_DISTORTION=p("reality_distortion");
    private static DefaultParticleType p(String id){return Registry.register(Registries.PARTICLE_TYPE,new Identifier(EndExpansion.MOD_ID,id),FabricParticleTypes.simple());}
    public static void register(){}
}
