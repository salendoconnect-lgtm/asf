package com.endexpansion.registry;
import com.endexpansion.EndExpansion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
public final class EndSounds {
    public static final SoundEvent AMBIENT_END=reg("ambient_end");
    public static final SoundEvent VOID_AMBIENT=reg("void_ambient");
    public static final SoundEvent ENDERLING_IDLE=reg("enderling_idle");
    public static final SoundEvent VOID_STALKER_ATTACK=reg("void_stalker_attack");
    public static final SoundEvent CRYSTAL_HIT=reg("crystal_hit");
    public static final SoundEvent TELEPORT=reg("teleport");
    public static final SoundEvent VOID_BEAM=reg("void_beam");
    public static final SoundEvent BOSS_CRYSTAL=reg("boss_crystal");
    public static final SoundEvent BOSS_ARCHON=reg("boss_archon");
    public static final SoundEvent REALITY_SHIFT=reg("reality_shift");
    private static SoundEvent reg(String id){Identifier i=new Identifier(EndExpansion.MOD_ID,id);return Registry.register(Registries.SOUND_EVENT,i,SoundEvent.of(i));}
    public static void register(){}
}
