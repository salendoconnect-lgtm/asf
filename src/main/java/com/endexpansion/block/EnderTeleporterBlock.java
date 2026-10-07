package com.endexpansion.block;
import com.endexpansion.registry.EndItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.particle.ParticleTypes;
public class EnderTeleporterBlock extends Block {
    public EnderTeleporterBlock(Settings s){super(s);}
    @Override public ActionResult onUse(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,net.minecraft.util.hit.BlockHitResult hit){
        if(world.isClient)return ActionResult.SUCCESS;
        ItemStack held=player.getStackInHand(hand);
        if(player.getItemCooldownManager().isCoolingDown(EndItems.ENDER_TELEPORTER))return ActionResult.FAIL;
        if(!held.isOf(EndItems.ENDER_ESSENCE) || held.getCount()<1)return ActionResult.FAIL;
        held.decrement(1);
        if(player.getY()<5)return ActionResult.FAIL;
        Vec3d target=findSafe(world,pos);
        if(target==null)return ActionResult.FAIL;
        player.teleport(target.x,target.y,target.z);
        player.getItemCooldownManager().set(EndItems.ENDER_TELEPORTER,100);
        world.playSound(null,pos,net.minecraft.sound.SoundEvents.ENTITY_ENDERMAN_TELEPORT,SoundCategory.BLOCKS,1,1);
        if(world instanceof net.minecraft.server.world.ServerWorld sw) sw.spawnParticles(ParticleTypes.PORTAL,pos.getX()+.5,pos.getY()+1,pos.getZ()+.5,40,.5,.8,.5,.2);
        return ActionResult.CONSUME;
    }
    private Vec3d findSafe(World w,BlockPos p){
        for(int dy=1;dy<32;dy++){BlockPos q=p.up(dy);if(w.getBlockState(q).isAir()&&w.getBlockState(q.up()).isAir()&&w.getBlockState(q.down()).isOpaque())return q.toCenterPos();}
        return null;
    }
}
