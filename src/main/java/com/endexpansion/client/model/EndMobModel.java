package com.endexpansion.client.model;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import com.endexpansion.entity.EndExpansionMob;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
public class EndMobModel extends SinglePartEntityModel<EndExpansionMob> {
    private final ModelPart root,body,head,leftArm,rightArm,leftLeg,rightLeg,hornLeft,hornRight,wingLeft,wingRight,crystal;
    public EndMobModel(ModelPart root){this.root=root;body=root.getChild("body");head=root.getChild("head");leftArm=root.getChild("left_arm");rightArm=root.getChild("right_arm");leftLeg=root.getChild("left_leg");rightLeg=root.getChild("right_leg");hornLeft=root.getChild("horn_left");hornRight=root.getChild("horn_right");wingLeft=root.getChild("wing_left");wingRight=root.getChild("wing_right");crystal=root.getChild("crystal");}
    public static TexturedModelData getTexturedModelData(){
        ModelData d=new ModelData();ModelPartData r=d.getRoot();
        r.addChild("body",ModelPartBuilder.create().uv(0,16).cuboid(-5,-8,-3,10,16,6),ModelTransform.pivot(0,8,0));
        r.addChild("head",ModelPartBuilder.create().uv(0,0).cuboid(-4,-4,-4,8,8,8),ModelTransform.pivot(0,-8,0));
        r.addChild("left_arm",ModelPartBuilder.create().uv(32,16).cuboid(-2,-1,-2,4,12,4),ModelTransform.pivot(7,0,0));
        r.addChild("right_arm",ModelPartBuilder.create().uv(32,16).cuboid(-2,-1,-2,4,12,4),ModelTransform.pivot(-7,0,0));
        r.addChild("left_leg",ModelPartBuilder.create().uv(48,0).cuboid(-2,0,-2,4,12,4),ModelTransform.pivot(3,8,0));
        r.addChild("right_leg",ModelPartBuilder.create().uv(48,0).cuboid(-2,0,-2,4,12,4),ModelTransform.pivot(-3,8,0));
        r.addChild("horn_left",ModelPartBuilder.create().uv(24,0).cuboid(-1,-4,-1,2,5,2),ModelTransform.pivot(-3,-12,0));
        r.addChild("horn_right",ModelPartBuilder.create().uv(24,0).cuboid(-1,-4,-1,2,5,2),ModelTransform.pivot(3,-12,0));
        r.addChild("wing_left",ModelPartBuilder.create().uv(0,40).cuboid(0,-4,0,8,10,1),ModelTransform.pivot(5,0,3));
        r.addChild("wing_right",ModelPartBuilder.create().uv(18,40).cuboid(-8,-4,0,8,10,1),ModelTransform.pivot(-5,0,3));
        r.addChild("crystal",ModelPartBuilder.create().uv(32,40).cuboid(-3,-5,-3,6,8,6),ModelTransform.pivot(0,6,0));
        return TexturedModelData.of(d,64,64);
    }
    @Override public ModelPart getPart(){return root;}
    @Override public void setAngles(EndExpansionMob e,float limbAngle,float limbDistance,float age,float headYaw,float headPitch){
        head.yaw=headYaw*0.017453292f;head.pitch=headPitch*0.017453292f;
        float swing=net.minecraft.util.math.MathHelper.cos(limbAngle*0.6662f)*1.2f*limbDistance;
        leftLeg.pitch=swing;rightLeg.pitch=-swing;leftArm.pitch=-swing*.65f;rightArm.pitch=swing*.65f;
        boolean horns=e.kind()==EndExpansionMob.Kind.ENDER_WRAITH||e.kind()==EndExpansionMob.Kind.THE_NULL||e.kind()==EndExpansionMob.Kind.END_GUARDIAN;hornLeft.visible=horns;hornRight.visible=horns;boolean wings=e.kind()==EndExpansionMob.Kind.VOID_FLYER||e.kind()==EndExpansionMob.Kind.VOID_LEVIATHAN;wingLeft.visible=wings;wingRight.visible=wings;crystal.visible=e.kind()==EndExpansionMob.Kind.CRYSTAL_GOLEM||e.kind()==EndExpansionMob.Kind.CRYSTAL_MITE||e.kind()==EndExpansionMob.Kind.THE_NULL;
        if(e.kind()==EndExpansionMob.Kind.VOID_FLYER){body.pitch=0.25f+net.minecraft.util.math.MathHelper.sin(age*.12f)*.08f;}
        else body.pitch=0;
    }
}
