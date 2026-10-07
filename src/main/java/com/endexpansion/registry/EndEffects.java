package com.endexpansion.registry;
import com.endexpansion.EndExpansion;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
public final class EndEffects {
    public static final StatusEffect VOID_RESISTANCE = register("void_resistance", new EndStatusEffect(StatusEffectCategory.BENEFICIAL, 0x4B0082));
    public static final StatusEffect ENDER_FOCUS = register("ender_focus", new EndStatusEffect(StatusEffectCategory.BENEFICIAL, 0x8A2BE2));
    public static final StatusEffect CRYSTAL_RESONANCE = register("crystal_resonance", new EndStatusEffect(StatusEffectCategory.BENEFICIAL, 0xDDA0DD));
    public static final StatusEffect LOW_GRAVITY = register("low_gravity", new EndStatusEffect(StatusEffectCategory.BENEFICIAL, 0x6A5ACD));
    public static final StatusEffect VOID_PRESSURE = register("void_pressure", new EndStatusEffect(StatusEffectCategory.HARMFUL, 0x111111));
    private static StatusEffect register(String id, StatusEffect e){return Registry.register(Registries.STATUS_EFFECT,new Identifier(EndExpansion.MOD_ID,id),e);}
    public static void register(){}
    private static final class EndStatusEffect extends StatusEffect {
        EndStatusEffect(StatusEffectCategory c,int color){super(c,color);}
    }
}
