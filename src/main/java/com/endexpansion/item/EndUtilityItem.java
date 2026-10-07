package com.endexpansion.item;
import com.endexpansion.registry.EndEffects;
import com.endexpansion.registry.EndItems;
import com.endexpansion.registry.EndParticles;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
public class EndUtilityItem extends Item {
    public EndUtilityItem(Settings s){super(s);}
    @Override public TypedActionResult<ItemStack> use(World world,PlayerEntity p,Hand hand){
        ItemStack s=p.getStackInHand(hand);
        if(world.isClient)return TypedActionResult.success(s);
        if(s.isOf(EndItems.ENDER_BOTTLE)){p.addStatusEffect(new StatusEffectInstance(EndEffects.ENDER_FOCUS,300,0));s.decrement(1);}
        else if(s.isOf(EndItems.ENDER_FLARE)){if(world instanceof ServerWorld sw)sw.spawnParticles(EndParticles.ENDER_ENERGY,p.getX(),p.getY()+1,p.getZ(),25,1,1,1,.08);p.addStatusEffect(new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.GLOWING,160));s.decrement(1);}
        else if(s.isOf(EndItems.ENDER_LANTERN)){p.addStatusEffect(new StatusEffectInstance(net.minecraft.entity.effect.StatusEffects.NIGHT_VISION,400,0));}
        return TypedActionResult.consume(s);
    }
}
