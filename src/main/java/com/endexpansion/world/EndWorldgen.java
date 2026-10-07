package com.endexpansion.world;
import com.endexpansion.registry.*;
import com.endexpansion.entity.*;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
public final class EndWorldgen {
    private static boolean ready=false;
    public static void register(){
        EndStructures.register();
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.END_WASTES,EndBiomes.ENDER_FORESTS,EndBiomes.PURPLE_SWAMPS,EndBiomes.CRYSTAL_CAVES,EndBiomes.FLOATING_ISLANDS,EndBiomes.END_ABYSS),SpawnGroup.CREATURE,EndEntities.ENDERLING,18,2,5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.ENDER_FORESTS),SpawnGroup.CREATURE,EndEntities.ENDER_NOMAD,5,1,2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.END_WASTES,EndBiomes.PURPLE_SWAMPS),SpawnGroup.MONSTER,EndEntities.VOID_STALKER,12,1,3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.CRYSTAL_CAVES),SpawnGroup.MONSTER,EndEntities.CRYSTAL_MITE,30,2,6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.END_ABYSS),SpawnGroup.MONSTER,EndEntities.ENDER_WRAITH,15,1,2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.FLOATING_ISLANDS),SpawnGroup.MONSTER,EndEntities.VOID_FLYER,8,1,2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.END_WASTES,EndBiomes.CRYSTAL_CAVES),SpawnGroup.MONSTER,EndEntities.END_GUARDIAN,3,1,1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.CRYSTAL_CAVES),SpawnGroup.MONSTER,EndEntities.CRYSTAL_GOLEM,1,1,1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(EndBiomes.END_ABYSS),SpawnGroup.MONSTER,EndEntities.THE_NULL,1,1,1);
        ServerChunkEvents.CHUNK_LOAD.register(EndWorldgen::generateChunkContent);
    }
    private static void generateChunkContent(ServerWorld world,WorldChunk chunk){
        if(world.getRegistryKey()!=World.END)return;
        ChunkPos cp=chunk.getPos();
        int cx=cp.x,cz=cp.z;
        long seed=world.getSeed() ^ ((long)cx*341873128712L) ^ ((long)cz*132897987541L);
        Random r=Random.create(seed);
        int bx=cp.getStartX()+8,bz=cp.getStartZ()+8;
        double dist=Math.sqrt((double)bx*bx+(double)bz*bz);
        if(dist<320)return;
        // Biome dressing: replace only a thin surface layer in outer End; central vanilla terrain remains untouched.
        var biome=world.getBiome(new BlockPos(bx,64,bz));
        net.minecraft.block.Block surface=EndBlocks.ENDER_STONE;
        if(biome.matchesKey(EndBiomes.ENDER_FORESTS)) surface=EndBlocks.ENDER_GRASS;
        else if(biome.matchesKey(EndBiomes.PURPLE_SWAMPS)) surface=EndBlocks.ENDER_MUD;
        else if(biome.matchesKey(EndBiomes.CRYSTAL_CAVES)) surface=EndBlocks.CRYSTAL_BRICKS;
        else if(biome.matchesKey(EndBiomes.FLOATING_ISLANDS)) surface=EndBlocks.POLISHED_ENDER_STONE;
        else if(biome.matchesKey(EndBiomes.END_ABYSS)) surface=EndBlocks.VOID_STONE;
        for(int dx=0;dx<16;dx+=3) for(int dz=0;dz<16;dz+=3){
            int x=cp.getStartX()+dx,z=cp.getStartZ()+dz; int y=world.getTopY(Heightmap.Type.WORLD_SURFACE,x,z)-1;
            if(y>10 && world.getBlockState(new BlockPos(x,y,z)).isOf(net.minecraft.block.Blocks.END_STONE)) world.setBlockState(new BlockPos(x,y,z),surface.getDefaultState());
        }
        if(biome.matchesKey(EndBiomes.ENDER_FORESTS) && r.nextInt(3)==0){
            int tx=cp.getStartX()+r.nextInt(16),tz=cp.getStartZ()+r.nextInt(16),ty=world.getTopY(Heightmap.Type.WORLD_SURFACE,tx,tz);
            for(int y=0;y<5+r.nextInt(4);y++)world.setBlockState(new BlockPos(tx,ty+y,tz),EndBlocks.ENDER_LOG.getDefaultState());
            for(int dx=-2;dx<=2;dx++)for(int dz=-2;dz<=2;dz++)for(int dy=3;dy<=6;dy++)if(Math.abs(dx)+Math.abs(dz)<4)world.setBlockState(new BlockPos(tx+dx,ty+dy,tz+dz),EndBlocks.ENDER_LEAVES.getDefaultState());
        }
        if(biome.matchesKey(EndBiomes.PURPLE_SWAMPS) && r.nextInt(2)==0){
            int tx=cp.getStartX()+r.nextInt(16),tz=cp.getStartZ()+r.nextInt(16),ty=world.getTopY(Heightmap.Type.WORLD_SURFACE,tx,tz);
            world.setBlockState(new BlockPos(tx,ty,tz),EndBlocks.ENDER_MOSS.getDefaultState());
            if(r.nextBoolean())world.setBlockState(new BlockPos(tx,ty+1,tz),EndBlocks.ENDER_GRASS.getDefaultState());
        }
        if(biome.matchesKey(EndBiomes.CRYSTAL_CAVES)){
            for(int i=0;i<4;i++){int x=cp.getStartX()+r.nextInt(16),z=cp.getStartZ()+r.nextInt(16),y=8+r.nextInt(50);BlockPos q=new BlockPos(x,y,z);if(world.getBlockState(q).isOf(net.minecraft.block.Blocks.END_STONE))world.setBlockState(q,EndBlocks.CRYSTAL_CLUSTER.getDefaultState());}
        }
        if(biome.matchesKey(EndBiomes.FLOATING_ISLANDS)){
            int fy=110+r.nextInt(45),fx=cp.getStartX()+8,fz=cp.getStartZ()+8;
            for(int dx=-3;dx<=3;dx++)for(int dz=-3;dz<=3;dz++)if(dx*dx+dz*dz<=10)world.setBlockState(new BlockPos(fx+dx,fy-Math.abs(dx*dx+dz*dz)/4,fz+dz),EndBlocks.ENDER_STONE.getDefaultState());
        }
        if(biome.matchesKey(EndBiomes.END_ABYSS) && r.nextInt(5)==0){
            int fy=70+r.nextInt(90),fx=cp.getStartX()+8,fz=cp.getStartZ()+8;
            for(int dx=-2;dx<=2;dx++)for(int dz=-2;dz<=2;dz++)if(Math.abs(dx)+Math.abs(dz)<4)world.setBlockState(new BlockPos(fx+dx,fy,fz+dz),EndBlocks.VOID_STONE.getDefaultState());
        }
        // low-frequency ore generation, 1-2 candidate positions per chunk.
        if(r.nextInt(100)<6){
            int x=cp.getStartX()+r.nextInt(16),z=cp.getStartZ()+r.nextInt(16);
            int y=20+r.nextInt(70);
            BlockPos p=new BlockPos(x,y,z);
            if(world.getBlockState(p).isOf(net.minecraft.block.Blocks.END_STONE))
                world.setBlockState(p,EndBlocks.ENDERITE_ORE.getDefaultState());
        }
        if(r.nextInt(5000)==0 && dist>1200){
            com.endexpansion.entity.EndExpansionMob levi=new com.endexpansion.entity.EndExpansionMob(EndEntities.VOID_LEVIATHAN,world);
            levi.refreshPositionAndAngles(bx,150+r.nextInt(70),bz,r.nextFloat()*360f,0);world.spawnEntity(levi);
        }
        if(r.nextInt(2500)==0) generateEndCityVariant(world,new BlockPos(bx,world.getTopY(Heightmap.Type.WORLD_SURFACE,bx,bz),bz),r.nextInt(5),r);
        if(r.nextInt(1000)<8) generateNamedStructure(world,new BlockPos(bx,world.getTopY(Heightmap.Type.WORLD_SURFACE,bx,bz),bz),r.nextInt(8),r);
    }
    private static void generateEndCityVariant(ServerWorld w,BlockPos base,int variant,Random r){
        if(base.getY()<30||base.getY()>220)return;
        int h=switch(variant){case 0->6;case 1->10;case 2->14;case 3->8;default->12;};
        net.minecraft.block.BlockState wall=variant==4?EndBlocks.CRYSTAL_BRICKS.getDefaultState():EndBlocks.ENDER_BRICKS.getDefaultState();
        int radius=variant==0?3:variant==1?4:variant==2?5:4;
        for(int y=0;y<h;y++)for(int dx=-radius;dx<=radius;dx++)for(int dz=-radius;dz<=radius;dz++){
            if(Math.abs(dx)==radius||Math.abs(dz)==radius||y==0)w.setBlockState(base.add(dx,y,dz),wall);
        }
        for(int y=1;y<h;y+=3){w.setBlockState(base.add(0,y,0),variant==4?EndBlocks.CRYSTAL_LAMP.getDefaultState():EndBlocks.VOID_LAMP.getDefaultState());}
        if(variant==3) for(int i=0;i<10;i++){int x=r.nextInt(radius*2+1)-radius,z=r.nextInt(radius*2+1)-radius;w.setBlockState(base.add(x,1,z),EndBlocks.ENDER_STONE.getDefaultState());}
        if(variant==2) for(int y=0;y<h;y++)for(int x=-radius-3;x<=radius+3;x++)w.setBlockState(base.add(x,y,0),wall);
        if(variant==4) w.setBlockState(base.add(0,h,0),EndBlocks.CRYSTAL_CLUSTER.getDefaultState());
    }

    private static void generateNamedStructure(ServerWorld w,BlockPos base,int type,Random r){
        // Deterministic procedural structures: no missing .nbt templates and safe across servers.
        int radius=switch(type){case 1->7;case 2->9;case 3->5;case 4->4;case 5->12;case 6->6;case 7->8;default->6;};
        if(base.getY()<20||base.getY()>250)return;
        if(w.getBlockState(base.up()).isOf(net.minecraft.block.Blocks.CHEST))return;
        BlockState shell=type==2?EndBlocks.CRYSTAL_BRICKS.getDefaultState():type>=6?EndBlocks.VOID_BRICKS.getDefaultState():EndBlocks.ENDER_BRICKS.getDefaultState();
        BlockState floor=type>=6?EndBlocks.VOID_STONE.getDefaultState():EndBlocks.ENDER_STONE.getDefaultState();
        for(int dx=-radius;dx<=radius;dx++)for(int dz=-radius;dz<=radius;dz++){
            if(dx*dx+dz*dz<=radius*radius)w.setBlockState(base.add(dx,0,dz),floor);
        }
        for(int dx=-radius;dx<=radius;dx++)for(int dz=-radius;dz<=radius;dz++){
            if(Math.abs(dx)==radius||Math.abs(dz)==radius) for(int y=1;y<Math.min(6,radius);y++)w.setBlockState(base.add(dx,y,dz),shell);
        }
        int towers=Math.max(2,radius/3);
        for(int i=0;i<towers;i++){double a=(Math.PI*2*i)/towers;int x=(int)Math.round(Math.cos(a)*(radius-1)),z=(int)Math.round(Math.sin(a)*(radius-1));for(int y=1;y<radius/2+2;y++)w.setBlockState(base.add(x,y,z),shell);}
        w.setBlockState(base.up(),EndBlocks.CRYSTAL_LAMP.getDefaultState());
        // Structure-specific marker/loot seed: chests use deterministic vanilla container loot via a simple chest.
        if(type==0||type==1||type==7){
            BlockPos cp=base.add(0,1,0);
            w.setBlockState(cp,net.minecraft.block.Blocks.CHEST.getDefaultState());
            if(w.getBlockEntity(cp) instanceof net.minecraft.block.entity.ChestBlockEntity chest){
                String[] names={"ancient_end_ruins","end_fortress","crystal_temple","void_shrine","ender_village","floating_citadel","abyssal_gate","ancient_observatory"};
                chest.setLootTable(new net.minecraft.util.Identifier(com.endexpansion.EndExpansion.MOD_ID,"chests/"+names[type]),r.nextLong());
            }
        }
    }
}
