package com.endexpansion.client.render;
import com.endexpansion.EndExpansion;
import com.endexpansion.client.model.EndMobModel;
import com.endexpansion.entity.EndExpansionMob;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
public class EndMobRenderer extends MobEntityRenderer<EndExpansionMob,EndMobModel>{
    private final String id;
    public EndMobRenderer(EntityRendererFactory.Context c,EntityModelLayer layer,String id){super(c,new EndMobModel(c.getPart(layer)),.45f);this.id=id;}
    @Override public Identifier getTexture(EndExpansionMob e){return new Identifier(EndExpansion.MOD_ID,"textures/entity/"+id+".png");}
}
