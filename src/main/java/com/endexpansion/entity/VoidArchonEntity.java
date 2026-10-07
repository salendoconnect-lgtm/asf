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
public class VoidArchonEntity extends PathAwareEntity {
    private final ServerBossBar bar=new ServerBossBar(getDisplayName(),BossBar.Color.PURPLE,BossBar.Style.PROGRESS);
    private int phase=1, attackCooldown=0, pendingAttack=0;
    public VoidArchonEntity(EntityType<? extends VoidArchonEntity> t,World w){super(t,w);setHealth(400);setStepHeight(2f);}
    @Override protected void initGoals(){goalSelector.add(0,new MeleeAttackGoal(this,1.1,true));goalSelector.add(2,new WanderAroundFarGoal(this,.5));targetSelector.add(0,new ActiveTargetGoal<>(this,PlayerEntity.class,true));}
    @Override protected void mobTick(){
        super.mobTick(); if(world.isClient)return;
        float hp=getHealth()/getMaxHealth();
        phase=hp>.8?1:hp>.55?2:hp>.3?3:hp>.12?4:5;
        bar.setPercent(hp);
        if(age%10==0) updatePlayers();
        if(attackCooldown>0)attackCooldown--;
        if(pendingAttack>0){pendingAttack--;if(pendingAttack==0)performAttack();}
        else if(attackCooldown==0){attackCooldown=switch(phase){case 1->90;case 2->75;case 3->65;case 4->50;default->35;}; telegraph();}
    }
    private void updatePlayers(){
        if(!(world instanceof ServerWorld sw))return;
        for(ServerPlayerEntity p:sw.getPlayers())if(p.squaredDistanceTo(this)<160*160)bar.addPlayer(p);else bar.removePlayer(p);
    }
    private void telegraph(){
        pendingAttack=10;
        world.playSound(null,getBlockPos(),com.endexpansion.registry.EndSounds.BOSS_ARCHON,net.minecraft.sound.SoundCategory.HOSTILE,1.2f,0.7f+phase*.08f);
        if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.REALITY_DISTORTION,getX(),getY()+1,getZ(),35,2,2,2,.05);
    }
    private void performAttack(){
        world.getNonSpectatingEntities(PlayerEntity.class,getBoundingBox().expand(phase>=4?18:12)).forEach(p->{
            double d=distanceTo(p);
            if(d<4)p.damage(world.getDamageSources().mobAttack(this),12+phase*2);
            else if(d<18)p.damage(world.getDamageSources().magic(),6+phase);
        });
        if(phase>=3)requestTeleport(getX()+(random.nextDouble()-.5)*14,getY(),getZ()+(random.nextDouble()-.5)*14);
        if(phase>=4){for(int i=0;i<6;i++){net.minecraft.util.math.BlockPos q=getBlockPos().add(random.nextInt(15)-7,random.nextInt(4)-1,random.nextInt(15)-7);if(world.getBlockState(q).isOpaque())world.breakBlock(q,false);}if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.VOID_EXPLOSION,getX(),getY()+1,getZ(),55,3,2,3,.12);}
        if(phase==5)world.getNonSpectatingEntities(PlayerEntity.class,getBoundingBox().expand(24)).forEach(p->p.damage(world.getDamageSources().magic(),10));
    }
    @Override public void onDeath(net.minecraft.entity.damage.DamageSource source){
        if(world instanceof ServerWorld sw)bar.clearPlayers();
        
        super.onDeath(source);
    }
    @Override public void writeCustomDataToNbt(net.minecraft.nbt.NbtCompound nbt){super.writeCustomDataToNbt(nbt);nbt.putInt("Phase",phase);}
    @Override public void readCustomDataFromNbt(net.minecraft.nbt.NbtCompound nbt){super.readCustomDataFromNbt(nbt);phase=nbt.getInt("Phase");}
}
