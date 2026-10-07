package com.endexpansion.registry;

import com.endexpansion.EndExpansion;
import com.endexpansion.item.EnderCompassItem;
import com.endexpansion.item.VoidCompassItem;
import com.endexpansion.item.EnderiteArmorMaterial;
import com.endexpansion.item.EnderiteToolMaterial;
import com.endexpansion.item.EndArtifactItem;
import com.endexpansion.item.EndUtilityItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;

public final class EndItems {
    public static final Item ENDERITE_INGOT = item("enderite_ingot", new Item.Settings().fireproof());
    public static final Item RAW_ENDERITE = item("raw_enderite", new Item.Settings());
    public static final Item ENDERITE_NUGGET = item("enderite_nugget", new Item.Settings());
    public static final Item VOID_CRYSTAL = item("void_crystal", new Item.Settings().rarity(Rarity.RARE));
    public static final Item ENDER_PEARL_SHARD = item("ender_pearl_shard", new Item.Settings());
    public static final Item NULLIUM_FRAGMENT = item("nullium_fragment", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item ANCIENT_ENDER_KEY = item("ancient_ender_key", new Item.Settings().rarity(Rarity.RARE));
    public static final Item VOID_KEY = item("void_key", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item ENDER_LANTERN = item("ender_lantern", new EndUtilityItem(new Item.Settings().maxCount(16)));
    public static final Item ENDER_FLARE = item("ender_flare", new EndUtilityItem(new Item.Settings().maxCount(16)));
    public static final Item ENDER_BOTTLE = item("ender_bottle", new EndUtilityItem(new Item.Settings().maxCount(16)));
    public static final Item ENDER_ESSENCE = item("ender_essence", new Item.Settings());
    public static final Item CRYSTAL_DUST = item("crystal_dust", new Item.Settings());
    public static final Item ENDER_SEED = item("ender_seed", new Item.Settings());
    public static final Item EYE_OF_THE_VOID = item("eye_of_the_void", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item HEART_OF_THE_END = item("heart_of_the_end", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item CRYSTAL_CORE = item("crystal_core", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item VOID_CROWN = item("void_crown", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item ENDER_SIGIL = item("ender_sigil", new Item.Settings().rarity(Rarity.RARE));
    public static final Item REALITY_FRAGMENT = item("reality_fragment", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item REALITY_CORE = item("reality_core", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item ENDER_COMPASS = item("ender_compass", new EnderCompassItem(new Item.Settings().maxCount(1)));
    public static final Item VOID_COMPASS = item("void_compass", new VoidCompassItem(new Item.Settings().maxCount(1)));
    public static final Item ENDER_TELEPORTER = Registries.ITEM.get(new Identifier(EndExpansion.MOD_ID,"ender_teleporter"));
    public static final Item VOID_ANCHOR = Registries.ITEM.get(new Identifier(EndExpansion.MOD_ID,"void_anchor"));

    public static final ToolMaterial ENDERITE = new EnderiteToolMaterial();
    public static final Item ENDERITE_SWORD = new SwordItem(ENDERITE, 3, -2.4f, new Item.Settings().fireproof());
    public static final Item ENDERITE_PICKAXE = new PickaxeItem(ENDERITE, 1, -2.8f, new Item.Settings().fireproof());
    public static final Item ENDERITE_AXE = new AxeItem(ENDERITE, 5, -3.0f, new Item.Settings().fireproof());
    public static final Item ENDERITE_SHOVEL = new ShovelItem(ENDERITE, 1.5f, -3.0f, new Item.Settings().fireproof());
    public static final Item ENDERITE_HOE = new HoeItem(ENDERITE, -2, 0f, new Item.Settings().fireproof());
    public static final Item ENDERITE_KNIFE = new SwordItem(ENDERITE, 1, -1.7f, new Item.Settings().fireproof());

    public static final Item ENDERITE_HELMET = new ArmorItem(EnderiteArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new Item.Settings().fireproof());
    public static final Item ENDERITE_CHESTPLATE = new ArmorItem(EnderiteArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new Item.Settings().fireproof());
    public static final Item ENDERITE_LEGGINGS = new ArmorItem(EnderiteArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new Item.Settings().fireproof());
    public static final Item ENDERITE_BOOTS = new ArmorItem(EnderiteArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new Item.Settings().fireproof());

    public static final ItemGroup GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(VOID_CRYSTAL))
            .displayName(Text.translatable("itemGroup.endexpansion.main"))
            .entries((ctx, entries) -> {
                entries.add(ENDERITE_INGOT);entries.add(RAW_ENDERITE);entries.add(ENDERITE_NUGGET);entries.add(VOID_CRYSTAL);entries.add(ENDER_PEARL_SHARD);entries.add(NULLIUM_FRAGMENT);
                entries.add(ANCIENT_ENDER_KEY);entries.add(VOID_KEY);entries.add(ENDER_LANTERN);entries.add(ENDER_FLARE);entries.add(ENDER_BOTTLE);entries.add(ENDER_ESSENCE);entries.add(CRYSTAL_DUST);entries.add(ENDER_SEED);
                entries.add(EYE_OF_THE_VOID);entries.add(HEART_OF_THE_END);entries.add(CRYSTAL_CORE);entries.add(VOID_CROWN);entries.add(ENDER_SIGIL);entries.add(REALITY_FRAGMENT);entries.add(REALITY_CORE);
                entries.add(ENDER_COMPASS);entries.add(VOID_COMPASS);entries.add(ENDER_TELEPORTER);entries.add(VOID_ANCHOR);
                entries.add(ENDERITE_SWORD);entries.add(ENDERITE_PICKAXE);entries.add(ENDERITE_AXE);entries.add(ENDERITE_SHOVEL);entries.add(ENDERITE_HOE);entries.add(ENDERITE_KNIFE);
                entries.add(ENDERITE_HELMET);entries.add(ENDERITE_CHESTPLATE);entries.add(ENDERITE_LEGGINGS);entries.add(ENDERITE_BOOTS);
                entries.add(EndBlocks.ENDER_STONE);entries.add(EndBlocks.ENDER_BRICKS);entries.add(EndBlocks.ENDER_PLANKS);entries.add(EndBlocks.CRYSTAL_LAMP);entries.add(EndBlocks.VOID_STONE);entries.add(EndBlocks.VOID_ANCHOR);
            }).build();

    private static Item item(String id, Item.Settings settings) { return Registry.register(Registries.ITEM, new Identifier(EndExpansion.MOD_ID, id), new Item(settings)); }
    private static Item item(String id, Item item) { return Registry.register(Registries.ITEM, new Identifier(EndExpansion.MOD_ID, id), item); }

    public static void register() {
        Registry.register(Registries.ITEM_GROUP,new Identifier(EndExpansion.MOD_ID,"main"),GROUP);
        EndArtifactItem.registerEvents();
    }
}
