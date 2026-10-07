package com.endexpansion.client;
import com.endexpansion.EndExpansion;
import com.endexpansion.client.model.*;
import com.endexpansion.client.render.*;
import com.endexpansion.client.particle.EndParticle;
import com.endexpansion.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import com.endexpansion.client.screen.EnderCompassScreen;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
public final class EndExpansionClient implements ClientModInitializer {
    public static final EntityModelLayer MOB_LAYER=new EntityModelLayer(new Identifier(EndExpansion.MOD_ID,"end_mob"),"main");
    public static final EntityModelLayer BOSS_LAYER=new EntityModelLayer(new Identifier(EndExpansion.MOD_ID,"end_boss"),"main");
    @Override public void onInitializeClient(){
        net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.registerModelLayer(MOB_LAYER,EndMobModel::getTexturedModelData);
        net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.registerModelLayer(BOSS_LAYER,EndBossModel::getTexturedModelData);
        EntityRendererRegistry.register(EndEntities.ENDERLING,c->new EndMobRenderer(c,MOB_LAYER,"enderling"));
        EntityRendererRegistry.register(EndEntities.VOID_STALKER,c->new EndMobRenderer(c,MOB_LAYER,"void_stalker"));
        EntityRendererRegistry.register(EndEntities.CRYSTAL_MITE,c->new EndMobRenderer(c,MOB_LAYER,"crystal_mite"));
        EntityRendererRegistry.register(EndEntities.ENDER_WRAITH,c->new EndMobRenderer(c,MOB_LAYER,"ender_wraith"));
        EntityRendererRegistry.register(EndEntities.VOID_FLYER,c->new EndMobRenderer(c,MOB_LAYER,"void_flyer"));
        EntityRendererRegistry.register(EndEntities.END_GUARDIAN,c->new EndMobRenderer(c,MOB_LAYER,"end_guardian"));
        EntityRendererRegistry.register(EndEntities.CRYSTAL_GOLEM,c->new EndMobRenderer(c,MOB_LAYER,"crystal_golem"));
        EntityRendererRegistry.register(EndEntities.VOID_LEVIATHAN,c->new EndMobRenderer(c,MOB_LAYER,"void_leviathan"));
        EntityRendererRegistry.register(EndEntities.ENDER_NOMAD,c->new EndMobRenderer(c,MOB_LAYER,"ender_nomad"));
        EntityRendererRegistry.register(EndEntities.THE_NULL,c->new EndMobRenderer(c,MOB_LAYER,"the_null"));
        EntityRendererRegistry.register(EndEntities.CRYSTAL_TITAN,c->new EndBossRenderer<>(c,BOSS_LAYER,"crystal_titan"));
        EntityRendererRegistry.register(EndEntities.VOID_ARCHON,c->new EndBossRenderer<>(c,BOSS_LAYER,"void_archon"));
        UseItemCallback.EVENT.register((player,world,hand)->{ if(player.getStackInHand(hand).isOf(EndItems.ENDER_COMPASS)){net.minecraft.client.MinecraftClient.getInstance().setScreen(new EnderCompassScreen());return net.minecraft.util.TypedActionResult.success(player.getStackInHand(hand));} return net.minecraft.util.TypedActionResult.pass(player.getStackInHand(hand));});
        ParticleFactoryRegistry reg=ParticleFactoryRegistry.getInstance();
        reg.register(EndParticles.ENDER_AMBIENT,EndParticle.Factory::new);reg.register(EndParticles.ENDER_DUST,EndParticle.Factory::new);reg.register(EndParticles.ENDER_ENERGY,EndParticle.Factory::new);
        reg.register(EndParticles.VOID_PARTICLE,EndParticle.Factory::new);reg.register(EndParticles.VOID_SMOKE,EndParticle.Factory::new);reg.register(EndParticles.VOID_SPARK,EndParticle.Factory::new);
        reg.register(EndParticles.CRYSTAL_SPARK,EndParticle.Factory::new);reg.register(EndParticles.CRYSTAL_SHARD,EndParticle.Factory::new);reg.register(EndParticles.TELEPORT_BURST,EndParticle.Factory::new);
        reg.register(EndParticles.TELEPORT_TRAIL,EndParticle.Factory::new);reg.register(EndParticles.VOID_EXPLOSION,EndParticle.Factory::new);reg.register(EndParticles.VOID_BEAM,EndParticle.Factory::new);
        reg.register(EndParticles.ENDER_MAGIC,EndParticle.Factory::new);reg.register(EndParticles.BOSS_ENERGY,EndParticle.Factory::new);reg.register(EndParticles.REALITY_DISTORTION,EndParticle.Factory::new);
    }
}
