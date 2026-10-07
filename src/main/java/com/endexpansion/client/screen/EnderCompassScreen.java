package com.endexpansion.client.screen;
import com.endexpansion.registry.EndNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
public class EnderCompassScreen extends Screen {
    public EnderCompassScreen(){super(Text.translatable("item.endexpansion.ender_compass"));}
    @Override protected void init(){
        int x=width/2-80,y=height/2-50;
        addDrawableChild(ButtonWidget.builder(Text.translatable("screen.endexpansion.structures"),b->{send(0);close();}).dimensions(x,y,160,20).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("screen.endexpansion.biomes"),b->{send(1);close();}).dimensions(x,y+24,160,20).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("screen.endexpansion.end_cities"),b->{send(2);close();}).dimensions(x,y+48,160,20).build());
        addDrawableChild(ButtonWidget.builder(Text.translatable("screen.endexpansion.rare_structures"),b->{send(3);close();}).dimensions(x,y+72,160,20).build());
    }
    private void send(int mode){var buf=PacketByteBufs.create();buf.writeVarInt(mode);ClientPlayNetworking.send(EndNetworking.COMPASS_MODE,buf);}
}
