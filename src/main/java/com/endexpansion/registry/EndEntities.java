package com.endexpansion.registry;
import com.endexpansion.EndExpansion;
import com.endexpansion.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.Heightmap;
public final class EndEntities {
    public static final EntityType<EndExpansionMob> ENDERLING=mob("enderling",SpawnGroup.CREATURE,0.65f,0.9f);
    public static final EntityType<EndExpansionMob> VOID_STALKER=mob("void_stalker",SpawnGroup.MONSTER,0.8f,1.9f);
    public static final EntityType<EndExpansionMob> CRYSTAL_MITE=mob("crystal_mite",SpawnGroup.MONSTER,0.45f,0.35f);
    public static final EntityType<EndExpansionMob> ENDER_WRAITH=mob("ender_wraith",SpawnGroup.MONSTER,0.7f,1.7f);
    public static final EntityType<EndExpansionMob> VOID_FLYER=mob("void_flyer",SpawnGroup.MONSTER,1.0f,1.0f);
    public static final EntityType<EndExpansionMob> END_GUARDIAN=mob("end_guardian",SpawnGroup.MONSTER,0.9f,2.4f);
    public static final EntityType<EndExpansionMob> CRYSTAL_GOLEM=mob("crystal_golem",SpawnGroup.MONSTER,1.6f,2.8f);
    public static final EntityType<EndExpansionMob> VOID_LEVIATHAN=mob("void_leviathan",SpawnGroup.MONSTER,3.0f,5.0f);
    public static final EntityType<EndExpansionMob> ENDER_NOMAD=mob("ender_nomad",SpawnGroup.CREATURE,0.65f,1.8f);
    public static final EntityType<EndExpansionMob> THE_NULL=mob("the_null",SpawnGroup.MONSTER,1.1f,2.2f);
    public static final EntityType<CrystalTitanEntity> CRYSTAL_TITAN=FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,CrystalTitanEntity::new).dimensions(EntityDimensions.fixed(2.8f,4.2f)).trackRangeBlocks(128).build();
    public static final EntityType<VoidArchonEntity> VOID_ARCHON=FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,VoidArchonEntity::new).dimensions(EntityDimensions.fixed(3.2f,5.0f)).trackRangeBlocks(160).build();
    static {
        Registry.register(Registries.ENTITY_TYPE,id("crystal_titan"),CRYSTAL_TITAN);
        Registry.register(Registries.ENTITY_TYPE,id("void_archon"),VOID_ARCHON);
        for(EntityType<EndExpansionMob> t:new EntityType[]{ENDERLING,VOID_STALKER,CRYSTAL_MITE,ENDER_WRAITH,VOID_FLYER,END_GUARDIAN,CRYSTAL_GOLEM,VOID_LEVIATHAN,ENDER_NOMAD,THE_NULL})
            FabricDefaultAttributeRegistry.register(t,attrs(t));
        FabricDefaultAttributeRegistry.register(CRYSTAL_TITAN,bossAttrs(36));
        FabricDefaultAttributeRegistry.register(VOID_ARCHON,bossAttrs(40));
    }
    private static EntityType<EndExpansionMob> mob(String id,SpawnGroup group,float w,float h){
        return Registry.register(Registries.ENTITY_TYPE,id(id),FabricEntityTypeBuilder.create(group,EndExpansionMob::new).dimensions(EntityDimensions.fixed(w,h)).trackRangeBlocks(96).build());
    }
    private static Identifier id(String s){return new Identifier(EndExpansion.MOD_ID,s);}
    private static DefaultAttributeContainer.Builder attrs(EntityType<?> t){
        double hp=12,damage=3,speed=.23;
        if(t==VOID_STALKER){hp=34;damage=7;speed=.30;}
        if(t==CRYSTAL_MITE){hp=10;damage=3;speed=.32;}
        if(t==ENDER_WRAITH){hp=28;damage=6;speed=.28;}
        if(t==VOID_FLYER){hp=26;damage=5;speed=.35;}
        if(t==END_GUARDIAN){hp=90;damage=11;speed=.24;}
        if(t==CRYSTAL_GOLEM){hp=130;damage=15;speed=.16;}
        if(t==VOID_LEVIATHAN){hp=220;damage=18;speed=.18;}
        if(t==THE_NULL){hp=160;damage=16;speed=.27;}
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH,hp).add(EntityAttributes.GENERIC_ATTACK_DAMAGE,damage).add(EntityAttributes.GENERIC_MOVEMENT_SPEED,speed).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,(t==CRYSTAL_GOLEM?0.8:0.1));
    }
    private static DefaultAttributeContainer.Builder bossAttrs(double hp){return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH,hp*10).add(EntityAttributes.GENERIC_ATTACK_DAMAGE,18).add(EntityAttributes.GENERIC_MOVEMENT_SPEED,.22).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,1);}
    public static void register(){}
}
