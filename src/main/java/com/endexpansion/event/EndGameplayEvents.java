package com.endexpansion.event;
import com.endexpansion.registry.*;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public final class EndGameplayEvents {
    public static void register(){
        PlayerBlockBreakEvents.AFTER.register((world,player,pos,state)->{
            if(world.getRegistryKey()==World.END && isEnderite(player.getMainHandStack()) && player.getRandom().nextFloat()<.10f)
                if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.ENDER_ENERGY,pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5,6,.2,.2,.2,.03);
        });
    }
    private static boolean isEnderite(ItemStack s){
        return s.isOf(EndItems.ENDERITE_SWORD)||s.isOf(EndItems.ENDERITE_PICKAXE)||s.isOf(EndItems.ENDERITE_AXE)||s.isOf(EndItems.ENDERITE_SHOVEL)||s.isOf(EndItems.ENDERITE_HOE)||s.isOf(EndItems.ENDERITE_KNIFE);
    }
}
