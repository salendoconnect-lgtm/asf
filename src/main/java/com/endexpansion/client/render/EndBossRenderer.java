package com.endexpansion.client.render;
import com.endexpansion.EndExpansion;
import com.endexpansion.client.model.EndBossModel;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;

public class EndBossRenderer extends MobEntityRenderer<PathAwareEntity, EndBossModel<PathAwareEntity>> {
    private final String id;
    public EndBossRenderer(EntityRendererFactory.Context c, EntityModelLayer layer, String id) {
        super(c, new EndBossModel<>(c.getPart(layer)), 1.0f);
        this.id = id;
    }
    @Override public Identifier getTexture(PathAwareEntity e) {
        return new Identifier(EndExpansion.MOD_ID, "textures/entity/" + id + ".png");
    }
}
