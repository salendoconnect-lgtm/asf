package com.endexpansion.item;
import com.endexpansion.registry.*;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;
public class EndArtifactItem extends Item {
    public EndArtifactItem(Settings s){super(s);}
    @Override public TypedActionResult<ItemStack> use(World world,PlayerEntity player,Hand hand){
        ItemStack stack=player.getStackInHand(hand);
        if(world.isClient)return TypedActionResult.success(stack);
        if(stack.isOf(EndItems.EYE_OF_THE_VOID)){
            if(world.getRegistryKey()==net.minecraft.world.World.END){
                net.minecraft.util.math.BlockPos best=null;double bestD=Double.MAX_VALUE;
                for(int dx=-48;dx<=48;dx+=4)for(int dz=-48;dz<=48;dz+=4){int cx=player.getChunkPos().x+dx,cz=player.getChunkPos().z+dz;long h=(world instanceof net.minecraft.server.world.ServerWorld sw ? sw.getSeed() : 0L)^((long)cx*341873128712L)^((long)cz*132897987541L);if(Math.floorMod(h,125)==0){net.minecraft.util.math.BlockPos q=new net.minecraft.util.math.BlockPos(cx*16+8,64,cz*16+8);double dd=q.getSquaredDistance(player.getBlockPos());if(dd<bestD){bestD=dd;best=q;}}}
                if(best!=null)player.sendMessage(Text.literal("Outer-End structure signal: "+best.getX()+", "+best.getZ()),true); else player.sendMessage(Text.translatable("message.endexpansion.eye_of_the_void"),true);
            } else player.sendMessage(Text.translatable("message.endexpansion.eye_of_the_void"),true);
            player.getItemCooldownManager().set(this,200);
        } else if(stack.isOf(EndItems.ENDER_SIGIL)){
            Vec3d look=player.getRotationVec(1f); Vec3d target=player.getPos().add(look.multiply(8));
            if(world.getBlockState(player.getBlockPos().add((int)look.x*8,(int)look.y*8,(int)look.z*8)).isAir()) player.teleport(target.x,target.y,target.z);
            player.getItemCooldownManager().set(this,120);
        } else if(stack.isOf(EndItems.CRYSTAL_CORE)){
            if(world.getRegistryKey()==net.minecraft.world.World.END && world.getBiome(player.getBlockPos()).value() == com.endexpansion.world.EndBiomes.biome(com.endexpansion.world.EndBiomes.CRYSTAL_CAVES)){
                boolean exists=!world.getEntitiesByType(com.endexpansion.registry.EndEntities.CRYSTAL_TITAN,e->e.isAlive() && e.squaredDistanceTo(player)<96*96).isEmpty();
                if(!exists){
                    com.endexpansion.entity.CrystalTitanEntity titan=new com.endexpansion.entity.CrystalTitanEntity(com.endexpansion.registry.EndEntities.CRYSTAL_TITAN,world);
                    titan.refreshPositionAndAngles(player.getBlockPos().up(),player.getYaw(),0);world.spawnEntity(titan);stack.decrement(1);player.sendMessage(Text.translatable("message.endexpansion.titan_summoned"),true);
                }
            } else player.addStatusEffect(new StatusEffectInstance(EndEffects.CRYSTAL_RESONANCE,240,1));
            player.getItemCooldownManager().set(this,400);
        } else if(stack.isOf(EndItems.VOID_KEY)){
            if(world.getRegistryKey()==net.minecraft.world.World.END && player.squaredDistanceTo(0,64,0)>320*320){
                boolean exists=!world.getEntitiesByType(com.endexpansion.registry.EndEntities.VOID_ARCHON,e->e.isAlive() && e.squaredDistanceTo(player)<192*192).isEmpty();
                if(!exists){
                    com.endexpansion.entity.VoidArchonEntity boss=new com.endexpansion.entity.VoidArchonEntity(com.endexpansion.registry.EndEntities.VOID_ARCHON,world);
                    boss.refreshPositionAndAngles(player.getBlockPos().up(2),player.getYaw(),0);world.spawnEntity(boss);stack.decrement(1);player.sendMessage(Text.translatable("message.endexpansion.archon_summoned"),true);
                }
            }
            player.getItemCooldownManager().set(this,1200);
        } else if(stack.isOf(EndItems.REALITY_CORE)){
            player.addStatusEffect(new StatusEffectInstance(EndEffects.ENDER_FOCUS,600,1));
            player.addStatusEffect(new StatusEffectInstance(EndEffects.VOID_RESISTANCE,600,1));
            player.getItemCooldownManager().set(this,1200);
        }
        return TypedActionResult.consume(stack);
    }
    @Override public void appendTooltip(ItemStack stack,World world,List<Text> tooltip,TooltipContext ctx){
        if(stack.isOf(EndItems.EYE_OF_THE_VOID))tooltip.add(Text.translatable("item.endexpansion.eye_of_the_void.tooltip"));
        if(stack.isOf(EndItems.HEART_OF_THE_END))tooltip.add(Text.translatable("item.endexpansion.heart_of_the_end.tooltip"));
        if(stack.isOf(EndItems.VOID_CROWN))tooltip.add(Text.translatable("item.endexpansion.void_crown.tooltip"));
    }
    public static void registerEvents(){
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity,source,amount)->{
            if(!(entity instanceof ServerPlayerEntity p) || amount<p.getHealth())return true;
            ItemStack heart=find(p,EndItems.HEART_OF_THE_END);
            if(!heart.isEmpty()){
                heart.decrement(1);p.setHealth(Math.max(1f,p.getMaxHealth()*0.35f));
                p.addStatusEffect(new StatusEffectInstance(EndEffects.VOID_RESISTANCE,200,1));
                p.getItemCooldownManager().set(EndItems.HEART_OF_THE_END,12000);
                p.sendMessage(Text.translatable("message.endexpansion.heart_saved"),true);
                return false;
            }
            return true;
        });
    }
    private static ItemStack find(PlayerEntity p,Item item){
        for(ItemStack s:p.getInventory().main)if(s.isOf(item))return s;
        for(ItemStack s:p.getInventory().offHand)if(s.isOf(item))return s;
        return ItemStack.EMPTY;
    }
}
