package com.endexpansion.item;
import com.endexpansion.registry.EndItems;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.recipe.Ingredient;
public final class EnderiteArmorMaterial implements ArmorMaterial {
    public static final EnderiteArmorMaterial INSTANCE = new EnderiteArmorMaterial();
    private static final int[] DUR = {13,15,16,11};
    public int getDurability(ArmorItem.Type type){return DUR[type.getEquipmentSlot().getEntitySlotId() < 4 ? type.getEquipmentSlot().getEntitySlotId() : 0] * 38;}
    public int getProtection(ArmorItem.Type type){return switch(type){case BOOTS->4;case LEGGINGS->7;case CHESTPLATE->9;case HELMET->4;};}
    public int getEnchantability(){return 20;}
    public net.minecraft.sound.SoundEvent getEquipSound(){return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;}
    public Ingredient getRepairIngredient(){return Ingredient.ofItems(EndItems.ENDERITE_INGOT);}
    public String getName(){return "endexpansion:enderite";}
    public float getToughness(){return 3.5f;}
    public float getKnockbackResistance(){return 0.12f;}
}
