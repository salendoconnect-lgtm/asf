package com.endexpansion.client.particle;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
public class EndParticle extends SpriteBillboardParticle {
    private final float baseScale;
    protected EndParticle(ClientWorld world,double x,double y,double z,SpriteProvider sprites){super(world,x,y,z);this.sprite=sprites.getSprite(random);baseScale=.5f+random.nextFloat()*.8f;scale=baseScale;maxAge=12+random.nextInt(24);velocityX=(random.nextDouble()-.5)*.03;velocityY=random.nextDouble()*.04;velocityZ=(random.nextDouble()-.5)*.03;alpha=.75f;}
    public ParticleTextureSheet getType(){return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;}
    @Override public void tick(){super.tick();scale=baseScale*(1f-age/(float)maxAge);velocityY+=.001;}
    public static class Factory implements ParticleFactory<DefaultParticleType>{private final SpriteProvider sprites;public Factory(SpriteProvider s){sprites=s;}public Particle createParticle(DefaultParticleType t,ClientWorld w,double x,double y,double z,double vx,double vy,double vz){EndParticle p=new EndParticle(w,x,y,z,sprites);p.velocityX=vx;p.velocityY=vy;p.velocityZ=vz;return p;}}
}
