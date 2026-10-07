package com.endexpansion.item;
import com.endexpansion.registry.EndItems;
import net.minecraft.item.Item;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.ChunkPos;
import java.util.List;
public class VoidCompassItem extends Item {
    public VoidCompassItem(Settings s){super(s);}
    @Override public TypedActionResult<ItemStack> use(World world,net.minecraft.entity.player.PlayerEntity player,net.minecraft.util.Hand hand){
        ItemStack stack=player.getStackInHand(hand);
        if(!world.isClient && world.getRegistryKey()==World.END){
            ChunkPos cp=player.getChunkPos(); String signal="No Nullium signal within 64 chunks.";
            outer: for(int r=1;r<=64;r++)for(int a=0;a<8;a++){int cx=cp.x+(int)(Math.cos(a*Math.PI/4)*r),cz=cp.z+(int)(Math.sin(a*Math.PI/4)*r);long h=(world instanceof net.minecraft.server.world.ServerWorld sw ? sw.getSeed() : 0L)^((long)cx*341873128712L)^((long)cz*132897987541L);if(Math.floorMod(h,100)<8){int x=cx*16+5,z=cz*16+5;if(world.getBiome(new net.minecraft.util.math.BlockPos(x,64,z)).value() == com.endexpansion.world.EndBiomes.biome(com.endexpansion.world.EndBiomes.END_ABYSS)){signal="Nullium resonance: "+x+", "+z;break outer;}}}
            player.sendMessage(Text.literal(signal),true);
        }
        return TypedActionResult.success(stack);
    }
    @Override public void appendTooltip(ItemStack stack,World world,List<Text> tooltip,TooltipContext ctx){
        tooltip.add(Text.translatable("item.endexpansion.void_compass.help"));
        tooltip.add(Text.translatable("item.endexpansion.void_compass.range"));
    }
}
