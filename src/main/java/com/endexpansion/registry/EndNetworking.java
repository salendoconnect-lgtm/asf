package com.endexpansion.registry;
import com.endexpansion.EndExpansion;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
public final class EndNetworking {
    public static final Identifier COMPASS_MODE=new Identifier(EndExpansion.MOD_ID,"compass_mode");
    public static void register(){
        ServerPlayNetworking.registerGlobalReceiver(COMPASS_MODE,(server,player,handler,buf,responseSender)->{
            int mode=Math.max(0,Math.min(3,buf.readVarInt()));
            server.execute(()->{
                for(ItemStack s:player.getInventory().main) if(s.isOf(EndItems.ENDER_COMPASS)){s.getOrCreateNbt().putInt("Mode",mode);return;}
                ItemStack off=player.getOffHandStack(); if(off.isOf(EndItems.ENDER_COMPASS))off.getOrCreateNbt().putInt("Mode",mode);
            });
        });
    }
}
