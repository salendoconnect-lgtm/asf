package com.endexpansion.item;
import com.endexpansion.registry.EndBlocks;
import com.endexpansion.registry.EndItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
public final class EnderiteToolMaterial implements ToolMaterial {
    public int getDurability(){return 2250;}
    public float getMiningSpeedMultiplier(){return 9.2f;}
    public float getAttackDamage(){return 4.0f;}
    public int getMiningLevel(){return 4;}
    public int getEnchantability(){return 18;}
    public Ingredient getRepairIngredient(){return Ingredient.ofItems(EndItems.ENDERITE_INGOT);}
}
