package com.endexpansion.entity;
import com.endexpansion.registry.EndEntities;
import com.endexpansion.registry.EndItems;
import com.endexpansion.registry.EndParticles;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import java.util.*;
public class CrystalTitanEntity extends PathAwareEntity {
    private final ServerBossBar bossBar=new ServerBossBar(getDisplayName(),BossBar.Color.PURPLE,BossBar.Style.NOTCHED_10);
    private int phase=1;
    public CrystalTitanEntity(EntityType<? extends CrystalTitanEntity> t,World w){super(t,w);setHealth(360);setStepHeight(1.5f);}
    @Override protected void initGoals(){goalSelector.add(0,new MeleeAttackGoal(this,1.0,true));goalSelector.add(1,new WanderAroundFarGoal(this,0.7));targetSelector.add(0,new ActiveTargetGoal<>(this,PlayerEntity.class,true));}
    @Override protected void mobTick(){
        super.mobTick(); if(world.isClient)return;
        phase=getHealth()>240?1:getHealth()>100?2:3;
        bossBar.setPercent(getHealth()/getMaxHealth());
        if(age%10==0)updateBarPlayers();
        if(age%70==0)groundStrike();
        if(phase>=2&&age%90==0)beam();
        if(phase==3&&age%50==0)collapse();
    }
    private void updateBarPlayers(){
        if(!(world instanceof ServerWorld sw))return;
        for(ServerPlayerEntity p:sw.getPlayers()) if(p.squaredDistanceTo(this)<128*128)bossBar.addPlayer(p); else bossBar.removePlayer(p);
    }
    private void groundStrike(){world.getNonSpectatingEntities(PlayerEntity.class,getBoundingBox().expand(5)).forEach(p->p.damage(world.getDamageSources().mobAttack(this),8));if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.CRYSTAL_SHARD,getX(),getY(),getZ(),35,2,1,2,.1);}
    private void beam(){world.playSound(null,getBlockPos(),com.endexpansion.registry.EndSounds.BOSS_CRYSTAL,net.minecraft.sound.SoundCategory.HOSTILE,1.2f,0.8f);LivingEntity t=getTarget();if(t!=null){t.damage(world.getDamageSources().magic(),11);if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.VOID_BEAM,getX(),getY()+2,getZ(),30,1,1,1,.15);}}
    private void collapse(){world.getNonSpectatingEntities(PlayerEntity.class,getBoundingBox().expand(10)).forEach(p->p.damage(world.getDamageSources().magic(),6));if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.REALITY_DISTORTION,getX(),getY()+1,getZ(),50,3,2,3,.1);}
    @Override public void onDeath(net.minecraft.entity.damage.DamageSource source){if(world instanceof ServerWorld sw)bossBar.clearPlayers();super.onDeath(source);}
    @Override public void writeCustomDataToNbt(net.minecraft.nbt.NbtCompound nbt){super.writeCustomDataToNbt(nbt);nbt.putInt("Phase",phase);}
    @Override public void readCustomDataFromNbt(net.minecraft.nbt.NbtCompound nbt){super.readCustomDataFromNbt(nbt);phase=nbt.getInt("Phase");}
}
