package com.endexpansion.client.render;
import com.endexpansion.EndExpansion;
import com.endexpansion.client.model.EndBossModel;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
public class EndBossRenderer<T extends MobEntity> extends MobEntityRenderer<T,EndBossModel<T>>{
    private final String id;
    public EndBossRenderer(EntityRendererFactory.Context c,EntityModelLayer layer,String id){super(c,new EndBossModel<>(c.getPart(layer)),1.0f);this.id=id;}
    @Override public Identifier getTexture(T e){return new Identifier(EndExpansion.MOD_ID,"textures/entity/"+id+".png");}
}
