package com.endexpansion.item;
import net.minecraft.item.Item;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import java.util.List;
public class EnderCompassItem extends Item {
    public EnderCompassItem(Settings s){super(s);}
    @Override public TypedActionResult<ItemStack> use(World world,net.minecraft.entity.player.PlayerEntity player,net.minecraft.util.Hand hand){
        ItemStack stack=player.getStackInHand(hand);
        if(!world.isClient && world.getRegistryKey()==World.END){
            int mode=stack.getOrCreateNbt().getInt("Mode");
            String result=switch(mode){
                case 1 -> findBiome(world,player);
                case 2 -> findVanillaCity(world,player);
                case 3 -> findStructure(world,player,true);
                default -> findStructure(world,player,false);
            };
            player.sendMessage(Text.literal(result),true);
        }
        return TypedActionResult.success(stack);
    }
    private String findStructure(World world,net.minecraft.entity.player.PlayerEntity p,boolean rare){
        for(int r=1;r<=64;r++)for(int a=0;a<8;a++){int cx=p.getChunkPos().x+(int)(Math.cos(a*Math.PI/4)*r),cz=p.getChunkPos().z+(int)(Math.sin(a*Math.PI/4)*r);long h=(world instanceof net.minecraft.server.world.ServerWorld sw ? sw.getSeed() : 0L)^((long)cx*341873128712L)^((long)cz*132897987541L);if(Math.floorMod(h,rare?900:125)==0)return "Structure signal: "+(cx*16+8)+", "+(cz*16+8);}
        return "No structure signal within 64 chunks.";
    }
    private String findBiome(World world,net.minecraft.entity.player.PlayerEntity p){
        for(int r=4;r<=64;r+=4)for(int a=0;a<8;a++){int x=p.getBlockPos().getX()+(int)(Math.cos(a*Math.PI/4)*r*16),z=p.getBlockPos().getZ()+(int)(Math.sin(a*Math.PI/4)*r*16);var b=world.getBiome(new BlockPos(x,64,z));if(b.matchesKey(com.endexpansion.world.EndBiomes.CRYSTAL_CAVES)||b.matchesKey(com.endexpansion.world.EndBiomes.ENDER_FORESTS)||b.matchesKey(com.endexpansion.world.EndBiomes.PURPLE_SWAMPS))return "Biome signal: "+b.getKey().map(k->k.getValue().getPath()).orElse("unknown");}
        return "No new biome signal within 64 chunks.";
    }
    private String findVanillaCity(World world,net.minecraft.entity.player.PlayerEntity p){return "End City mode active: use this compass with outer-End exploration to locate existing cities.";}
    @Override public void appendTooltip(ItemStack stack,World world,List<Text> tooltip,TooltipContext ctx){
        tooltip.add(Text.translatable("item.endexpansion.ender_compass.mode"));
        tooltip.add(Text.translatable("item.endexpansion.ender_compass.help"));
    }
}
