package com.endexpansion.entity;

import com.endexpansion.registry.EndEntities;
import com.endexpansion.registry.EndParticles;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EndExpansionMob extends PathAwareEntity {
    public enum Kind { ENDERLING, VOID_STALKER, CRYSTAL_MITE, ENDER_WRAITH, VOID_FLYER, END_GUARDIAN, CRYSTAL_GOLEM, VOID_LEVIATHAN, ENDER_NOMAD, THE_NULL }
    private final Kind kind;
    public EndExpansionMob(EntityType<? extends EndExpansionMob> type, World world){super(type,world); this.kind=kindFor(type); setStepHeight(1f);}
    public Kind kind(){return kind;}
    private static Kind kindFor(EntityType<?> t){
        if(t==EndEntities.ENDERLING)return Kind.ENDERLING;
        if(t==EndEntities.VOID_STALKER)return Kind.VOID_STALKER;
        if(t==EndEntities.CRYSTAL_MITE)return Kind.CRYSTAL_MITE;
        if(t==EndEntities.ENDER_WRAITH)return Kind.ENDER_WRAITH;
        if(t==EndEntities.VOID_FLYER)return Kind.VOID_FLYER;
        if(t==EndEntities.END_GUARDIAN)return Kind.END_GUARDIAN;
        if(t==EndEntities.CRYSTAL_GOLEM)return Kind.CRYSTAL_GOLEM;
        if(t==EndEntities.VOID_LEVIATHAN)return Kind.VOID_LEVIATHAN;
        if(t==EndEntities.ENDER_NOMAD)return Kind.ENDER_NOMAD;
        return Kind.THE_NULL;
    }
    @Override protected void initGoals(){
        goalSelector.add(0,new SwimGoal(this));
        goalSelector.add(1,new EscapeDangerGoal(this,1.35));
        if(kind==Kind.ENDERLING || kind==Kind.ENDER_NOMAD){
            goalSelector.add(2,new FleeEntityGoal<>(this,PlayerEntity.class,8f,1.25,1.45));
        } else {
            goalSelector.add(2,new MeleeAttackGoal(this,1.15,true));
            targetSelector.add(1,new ActiveTargetGoal<>(this,PlayerEntity.class,true));
            targetSelector.add(2,new RevengeGoal(this));
        }
        if(kind==Kind.END_GUARDIAN || kind==Kind.CRYSTAL_GOLEM) goalSelector.add(3,new FollowGroupLeaderGoal(this,1.0));
        goalSelector.add(6,new WanderAroundFarGoal(this,1.0));
        goalSelector.add(7,new LookAtEntityGoal(this,PlayerEntity.class,10f));
        goalSelector.add(8,new LookAroundGoal(this));
    }
    @Override
    protected net.minecraft.util.ActionResult interactMob(PlayerEntity player, net.minecraft.util.Hand hand){
        if(kind!=Kind.ENDER_NOMAD) return super.interactMob(player,hand);
        if(world.isClient) return net.minecraft.util.ActionResult.SUCCESS;
        net.minecraft.item.ItemStack in=player.getStackInHand(hand);
        if(in.isOf(com.endexpansion.registry.EndItems.ENDER_PEARL_SHARD) && in.getCount()>=8){
            in.decrement(8);player.giveItemStack(new net.minecraft.item.ItemStack(com.endexpansion.registry.EndItems.ANCIENT_ENDER_KEY));player.sendMessage(net.minecraft.text.Text.translatable("message.endexpansion.nomad_trade_key"),true);return net.minecraft.util.ActionResult.CONSUME;
        }
        if(in.isOf(com.endexpansion.registry.EndItems.VOID_CRYSTAL) && in.getCount()>=2){
            in.decrement(2);player.giveItemStack(new net.minecraft.item.ItemStack(com.endexpansion.registry.EndItems.ENDER_ESSENCE,3));player.sendMessage(net.minecraft.text.Text.translatable("message.endexpansion.nomad_trade_essence"),true);return net.minecraft.util.ActionResult.CONSUME;
        }
        player.sendMessage(net.minecraft.text.Text.translatable("message.endexpansion.nomad_trade"),true);
        return net.minecraft.util.ActionResult.CONSUME;
    }
    @Override public boolean canImmediatelyDespawn(double distanceSquared){return kind==Kind.VOID_LEVIATHAN || kind==Kind.THE_NULL ? distanceSquared>96*96 : super.canImmediatelyDespawn(distanceSquared);}
    @Override protected void mobTick(){
        super.mobTick();
        if(world.isClient)return;
        if(world.getDifficulty()==net.minecraft.world.Difficulty.PEACEFUL && kind!=Kind.ENDERLING && kind!=Kind.ENDER_NOMAD){setTarget(null);return;}
        if((kind==Kind.ENDERLING || kind==Kind.VOID_STALKER || kind==Kind.ENDER_WRAITH) && age%80==0 && random.nextFloat()<.22f){
            teleportRandomly();
        }
        if(kind==Kind.VOID_LEVIATHAN && getTarget()!=null && distanceTo(getTarget())>40){setTarget(null);}
        if(kind==Kind.VOID_FLYER && age%40==0) setVelocity(getVelocity().add(0,(random.nextDouble()-.35)*.08,0));
        if(kind==Kind.CRYSTAL_GOLEM && hurtTime>0 && age%8==0){
            if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.CRYSTAL_SHARD,getX(),getY()+1,getZ(),8,.5,.5,.5,.08);
        }
        if(kind==Kind.VOID_STALKER && getTarget()!=null && age%30==0 && distanceTo(getTarget())>8) rangedPulse();
        if(kind==Kind.VOID_LEVIATHAN && getTarget()!=null && distanceTo(getTarget())<24 && age%60==0) diveStrike();
        if(kind==Kind.THE_NULL && age%100==0) nullPulse();
        if(age%200==0 && random.nextFloat()<.35f){world.playSound(null,getBlockPos(),com.endexpansion.registry.EndSounds.AMBIENT_END,net.minecraft.sound.SoundCategory.AMBIENT,.35f,.8f+random.nextFloat()*.4f);}
    }
    protected void rangedPulse(){
        LivingEntity t=getTarget(); if(t==null)return;
        t.damage(world.getDamageSources().magic(),4f);
        if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.VOID_BEAM,getX(),getY()+1,getZ(),18,.2,.2,.2,.1);
    }
    protected void diveStrike(){
        LivingEntity t=getTarget(); if(t==null)return;
        if(getY()-t.getY()>3) setVelocity(t.getX()-getX(),-0.7,t.getZ()-getZ());
        if(distanceTo(t)<4)t.damage(world.getDamageSources().mobAttack(this),9f);
    }
    protected void nullPulse(){
        world.getNonSpectatingEntities(PlayerEntity.class,getBoundingBox().expand(12)).forEach(p->p.damage(world.getDamageSources().magic(),5f));
        if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(EndParticles.REALITY_DISTORTION,getX(),getY()+1,getZ(),24,1,1,1,.12);
    }
    protected boolean teleportRandomly(){
        double x=getX()+(random.nextDouble()-.5)*16,y=getY()+(random.nextInt(7)-3),z=getZ()+(random.nextDouble()-.5)*16;
        if(world.getBlockState(getBlockPos().add((int)x,(int)y,(int)z)).isAir()){requestTeleport(x,y,z);world.sendEntityStatus(this,(byte)46);return true;} return false;
    }
    @Override public void handleStatus(byte status){
        if(status==46){for(int i=0;i<12;i++)world.addParticle(EndParticles.TELEPORT_BURST,getParticleX(.5),getRandomBodyY(),getParticleZ(.5),0,0,0);return;}
        super.handleStatus(status);
    }
}
