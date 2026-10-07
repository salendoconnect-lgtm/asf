package com.endexpansion.registry;
import com.endexpansion.EndExpansion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
public final class EndEnchantments {
    public static final Enchantment VOID_RESISTANCE = reg("void_resistance", new EndEnchantment(Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET},2));
    public static final Enchantment ENDER_STEP = reg("ender_step", new EndEnchantment(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET},3));
    public static final Enchantment PHASE = reg("phase", new EndEnchantment(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND},2));
    public static final Enchantment CRYSTAL_BREAKER = reg("crystal_breaker", new EndEnchantment(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND},3));
    public static final Enchantment VOID_WALKER = reg("void_walker", new EndEnchantment(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET},2));
    private static Enchantment reg(String id, Enchantment e){return Registry.register(Registries.ENCHANTMENT,new Identifier(EndExpansion.MOD_ID,id),e);}
    public static void register(){}
    private static final class EndEnchantment extends Enchantment {
        private final int max;
        EndEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slots,int max){super(rarity,target,slots);this.max=max;}
        @Override public int getMaxLevel(){return max;}
    }
}
