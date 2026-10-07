package com.endexpansion.client.model;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
public class EndBossModel<T extends MobEntity> extends SinglePartEntityModel<T>{
    private final ModelPart root,core,head;
    public EndBossModel(ModelPart root){this.root=root;core=root.getChild("core");head=root.getChild("head");}
    public static TexturedModelData getTexturedModelData(){
        ModelData d=new ModelData();ModelPartData r=d.getRoot();
        r.addChild("core",ModelPartBuilder.create().uv(0,0).cuboid(-10,-10,-6,20,20,12),ModelTransform.pivot(0,10,0));
        r.addChild("head",ModelPartBuilder.create().uv(0,32).cuboid(-7,-7,-7,14,14,14),ModelTransform.pivot(0,-8,0));
        return TexturedModelData.of(d,64,64);
    }
    @Override public ModelPart getPart(){return root;}
    @Override public void setAngles(T e,float limbAngle,float limbDistance,float age,float headYaw,float headPitch){
        head.yaw=headYaw*.017453292f;head.pitch=headPitch*.017453292f;core.yaw=age*.025f;core.roll=net.minecraft.util.math.MathHelper.sin(age*.06f)*.05f;
    }
}
