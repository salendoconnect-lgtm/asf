package com.endexpansion.mixin;
import com.endexpansion.world.EndBiomes;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(TheEndBiomeSource.class)
public abstract class TheEndBiomeSourceMixin {
        @Inject(method="getBiome", at=@At("RETURN"), cancellable=true)
        private void endexpansion$sample(int x,int y,int z,MultiNoiseUtil.MultiNoiseSampler noise,CallbackInfoReturnable<RegistryEntry<Biome>> cir){
            double d=Math.sqrt((double)x*x+(double)z*z);
            if(d<320) return; // keep the central vanilla island and dragon arena recognizable
            long h=((long)x*341873128712L)^((long)z*132897987541L);
            double n=((h^(h>>>33))*0x9E3779B97F4A7C15L>>>11)/(double)(1L<<53);
            RegistryKey<Biome> key;
            if(d>2200 && n>0.82) key=EndBiomes.END_ABYSS;
            else if(n<0.18) key=EndBiomes.ENDER_FORESTS;
            else if(n<0.34) key=EndBiomes.PURPLE_SWAMPS;
            else if(n<0.53) key=EndBiomes.CRYSTAL_CAVES;
            else if(n<0.72) key=EndBiomes.FLOATING_ISLANDS;
            else key=EndBiomes.END_WASTES;
            cir.setReturnValue(RegistryEntry.of(EndBiomes.biome(key)));
        }
}
