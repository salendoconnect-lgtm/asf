package com.endexpansion.world;
import com.endexpansion.registry.EndBlocks;
import com.endexpansion.registry.EndEffects;
import com.endexpansion.registry.EndItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
public final class VoidPressureManager {
    public static void tick(MinecraftServer server){
        for(ServerWorld world:server.getWorlds()){
            if(world.getRegistryKey()!=World.END)continue;
            for(PlayerEntity p:world.getPlayers()){
                if(p.isSpectator()||p.isCreative())continue;
                int pressure=pressureAt(world,p.getBlockPos());
                var biome=world.getBiome(p.getBlockPos());
                if(biome.matchesKey(EndBiomes.PURPLE_SWAMPS)||biome.matchesKey(EndBiomes.FLOATING_ISLANDS)){
                    p.addStatusEffect(new StatusEffectInstance(EndEffects.LOW_GRAVITY,30,0,true,false));
                    if(!p.isOnGround() && p.getVelocity().y<0) p.addVelocity(0,0.012,0);
                    p.fallDistance=Math.min(p.fallDistance,2f);
                }
                if(pressure<=0)continue;
                int armor=countArmor(p);
                int resistance=Math.min(pressure,(armor>=4||hasItem(p,EndItems.VOID_CROWN))?1:0);
                if(resistance<pressure){
                    p.addStatusEffect(new StatusEffectInstance(EndEffects.VOID_PRESSURE,45,pressure-1,true,false));
                    if(pressure>=3 && p.age%80==0) p.damage(world.getDamageSources().magic(),pressure>=5?3:1);
                }
                if(armor>=4){p.addStatusEffect(new StatusEffectInstance(EndEffects.VOID_RESISTANCE,30,0,true,false));p.addStatusEffect(new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.SPEED,30,0,true,false));}
                if(pressure>=2 && p.age%12==0)world.spawnParticles(com.endexpansion.registry.EndParticles.VOID_PARTICLE,p.getX(),p.getY()+1,p.getZ(),2,.4,.6,.4,.01);
            }
        }
    }
    private static int pressureAt(ServerWorld w,BlockPos p){
        var biome=w.getBiome(p);
        if(biome.matchesKey(EndBiomes.END_ABYSS))return 5;
        if(biome.matchesKey(EndBiomes.FLOATING_ISLANDS))return 2;
        if(biome.matchesKey(EndBiomes.PURPLE_SWAMPS))return 1;
        return 0;
    }
    private static boolean hasItem(PlayerEntity p,net.minecraft.item.Item item){for(ItemStack s:p.getInventory().main)if(s.isOf(item))return true;for(ItemStack s:p.getInventory().offHand)if(s.isOf(item))return true;return false;}
    private static int countArmor(PlayerEntity p){
        int n=0;for(ItemStack s:p.getArmorItems())if(s.isOf(EndItems.ENDERITE_HELMET)||s.isOf(EndItems.ENDERITE_CHESTPLATE)||s.isOf(EndItems.ENDERITE_LEGGINGS)||s.isOf(EndItems.ENDERITE_BOOTS))n++;return n;
    }
}
