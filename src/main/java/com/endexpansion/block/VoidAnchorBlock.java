package com.endexpansion.block;
import com.endexpansion.registry.EndEffects;
import com.endexpansion.registry.EndItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
public class VoidAnchorBlock extends Block {
    public static final IntProperty CHARGE=IntProperty.of("charge",0,8);
    public VoidAnchorBlock(Settings settings){super(settings);setDefaultState(getStateManager().getDefaultState().with(CHARGE,8));}
    @Override protected void appendProperties(StateManager.Builder<Block,BlockState> b){b.add(CHARGE);}
    @Override public ActionResult onUse(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,net.minecraft.util.hit.BlockHitResult hit){
        if(world.isClient)return ActionResult.SUCCESS;
        ItemStack s=player.getStackInHand(hand);
        if(s.isOf(EndItems.ENDER_ESSENCE)&&state.get(CHARGE)<8){s.decrement(1);world.setBlockState(pos,state.with(CHARGE,8));return ActionResult.CONSUME;}
        return ActionResult.PASS;
    }
    @Override public void scheduledTick(BlockState state,ServerWorld world,BlockPos pos,net.minecraft.util.math.random.Random random){
        int charge=state.get(CHARGE);
        if(charge<=0)return;
        world.getPlayers(p->p.squaredDistanceTo(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5)<20*20).forEach(p->p.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(EndEffects.VOID_RESISTANCE,40,0,true,false)));
        world.spawnParticles(com.endexpansion.registry.EndParticles.VOID_SPARK,pos.getX()+.5,pos.getY()+1,pos.getZ()+.5,4,.5,.5,.5,.02);
        world.setBlockState(pos,state.with(CHARGE,charge-1));
        if(charge>1)world.scheduleBlockTick(pos,this,200);
    }
    @Override public void onPlaced(World world,BlockPos pos,BlockState state,LivingEntity placer,ItemStack stack){if(!world.isClient)world.scheduleBlockTick(pos,this,200);}
}
