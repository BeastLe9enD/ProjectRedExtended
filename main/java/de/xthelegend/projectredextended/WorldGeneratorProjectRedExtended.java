package de.xthelegend.projectredextended;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import mrtjp.core.world.WorldLib;

public class WorldGeneratorProjectRedExtended implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// TODO Auto-generated method stub
		
		BiomeGenBase bgb = world.getWorldChunkManager().getBiomeGenAt(chunkX * 16 + 16, chunkZ * 16 + 16);
		
		int n = 0;
        if (bgb == BiomeGenBase.birchForest)
            n = 1;
        else if (bgb == BiomeGenBase.birchForestHills)
            n = 1;
        else if (bgb == BiomeGenBase.plains)
            n = 1;
        else if (bgb == BiomeGenBase.forest)
            n = 4;
        else if (bgb == BiomeGenBase.roofedForest)
            n = 4;
        else if (bgb == BiomeGenBase.taiga)
        	n = 3;

		
		for (int i = 0; i < n; i++) {
            int x = chunkX * 16 + random.nextInt(16) + 8;
            int y = random.nextInt(128);
            int z = chunkZ * 16 + random.nextInt(16) + 8;
            new WorldGenIndigo(MainClass.BlockIndigoFlower).generate(world, random, x, y, z);
        }
		
		if (bgb == BiomeGenBase.jungle || bgb == BiomeGenBase.jungleHills)
        {
            for (int i = 0; i < 20; i++)
            {
                int x = chunkX * 16 + random.nextInt(16) + 8;
                int y = random.nextInt(128);
                int z = chunkZ * 16 + random.nextInt(16) + 8;
                Block uwb = world.getBlock(x, y - (2/2), z);
                if(uwb == Blocks.dirt || uwb == Blocks.grass)
                {
                	(new de.xthelegend.projectredextended.rubbergen.schematic01()).generate(world, random, x, y, z);
                }
                
                else return;
                
            }
        }
		/*
		if (var15 == BiomeGenBase.jungle || var15 == BiomeGenBase.jungleHills)
        {
            for (var7 = 0; var7 < 6; ++var7)
            {
                var12 = var2 * 16 + var8.nextInt(16) + 8;
                var13 = var3 * 16 + var8.nextInt(16) + 8;
                var14 = var4.getHeightValue(var12, var13);
                (new WorldGenRubberTree()).generate(var4, var4.rand, var12, var14, var13);
            }
        }*/
	}

	

}
