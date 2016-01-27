package de.xthelegend.projectredextended;

import java.util.Random;

import codechicken.lib.vec.Vector3;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import de.xthelegend.projectredextended.lib.xawpnqf;
import de.xthelegend.projectredextended.lib.nawapsk;

public class WorldGenRubberTree extends WorldGenerator
{
    public void putLeaves(World world, int x, int y, int z)
    {
        Block var5 = world.getBlock(x, y, z);
        world.setBlock(x, y, z, MainClass.BlockRubberLeaves);  
    }

    
    public boolean fillBlock(World world, int x, int y, int z)
    {
        if (y >= 0 && y <= 126)
        {
        	Block[] block = new Block[]{Blocks.tallgrass,Blocks.grass,Blocks.vine,Blocks.dirt,Blocks.grass};
            Block var5 = world.getBlock(x, y, z);
            Block var6 = var5;

            if (var6 != null && var6.isWood(world, x, y, z))
            {
                return true;
            }
            
            else
            {
                world.setBlock(x, y, z, MainClass.BlockRubberWood);
                this.putLeaves(world, x, y - 1, z);
                this.putLeaves(world, x, y + 1, z);
                this.putLeaves(world, x, y, z - 1);
                this.putLeaves(world, x, y, z + 1);
                this.putLeaves(world, x - 1, y, z);
                this.putLeaves(world, x + 1, y, z);
                this.putLeaves(world, x - 1, y - 1, z);
                this.putLeaves(world, x - 1, y + 1, z);
                this.putLeaves(world, x - 1, y, z - 1);
                this.putLeaves(world, x - 1, y, z + 1);
                this.putLeaves(world, x - 1 - 1, y, z);
                this.putLeaves(world, x - 1 + 1, y, z);
                this.putLeaves(world, x, y - 1, z - 1);
                this.putLeaves(world, x, y + 1, z - 1);
                this.putLeaves(world, x, y, z - 1 + 2);
                this.putLeaves(world, x, y, z + 1 + 2);
                this.putLeaves(world, x - 1, y, z - 1);
                this.putLeaves(world, x + 1, y, z - 1);
                
                return true;
            }
        }
		return false;
        
    }

    @SuppressWarnings("unused")
	@Override
    public boolean generate(World var1, Random x, int y, int z, int var5)
    {
        int var9 = x.nextInt(6) + 25;

        if (z >= 1 && z + var9 + 2 <= var1.getHeight())
        {
            Block var6;
            int var7;
            int var8;

            for (var7 = -1; var7 <= 1; ++var7)
            {
                for (var8 = -1; var8 <= 1; ++var8)
                {
                    var6 = var1.getBlock(y + var7, z - 1, var5 + var8);

                    
                }
            }

            byte var10 = 1;
            int var11;

            for (var11 = z; var11 < z + var9; ++var11)
            {
                if (var11 > z + 3)
                {
                    var10 = 5;
                }

                for (var7 = y - var10; var7 <= y + var10; ++var7)
                {
                    for (var8 = var5 - var10; var8 <= var5 + var10; ++var8)
                    {
                        var6 = var1.getBlock(var7, var11, var8);
                        Block var12 = var6; /*Block.blocksList[var6];*/

                       
                    }
                }
            }

            for (var7 = -1; var7 <= 1; ++var7)
            {
                for (var8 = -1; var8 <= 1; ++var8)
                {
                    var1.setBlock(y + var7, z - 1, var5 + var8, Blocks.dirt);
                }
            }

            for (var11 = 0; var11 <= 6; ++var11)
            {
                for (var7 = -1; var7 <= 1; ++var7)
                {
                    for (var8 = -1; var8 <= 1; ++var8)
                    {
                        var1.setBlock(y + var7, z + var11, var5 + var8, MainClass.BlockRubberWood);
                    }
                }

                for (var7 = -1; var7 <= 1; ++var7)
                {
                    if (x.nextInt(5) == 1 && var1.getBlock(y + var7, z + var11, var5 - 2) == Blocks.air)
                    {
                    	var1.setBlock(y + var7, z + var11, var5 - 2, Blocks.vine);
                        var1.setBlockMetadataWithNotify(y + var7, z + var11, var5 - 2,  1, 0);
                    }

                    if (x.nextInt(5) == 1 && var1.getBlock(y + var7, z + var11, var5 + 2) == Blocks.air)
                    {
                    	var1.setBlock(y + var7, z + var11, var5 + 2, Blocks.vine);
                        var1.setBlockMetadataWithNotify(y + var7, z + var11, var5 + 2,  4, 0);
                    }
                }

                for (var8 = -1; var8 <= 1; ++var8)
                {
                    if (x.nextInt(5) == 1 && var1.getBlock(y - 2, z + var11, var5 + var8) == Blocks.air)
                    {
                    	var1.setBlock(y - 2, z + var11, var5 + var8, Blocks.vine);
                        var1.setBlockMetadataWithNotify(y - 2, z + var11, var5 + var8,  8, 0);
                    }

                    if (x.nextInt(5) == 1 && var1.getBlock(y + 2, z + var11, var5 + var8) == Blocks.air)
                    {
                    	var1.setBlock(y + 2, z + var11, var5 + var8, Blocks.vine);
                        var1.setBlockMetadataWithNotify(y + 2, z + var11, var5 + var8,  2, 0);
                    }
                }
            }

            Vector3 x3 = new Vector3();
            Vector3 x4 = new Vector3();
            int var14 = x.nextInt(100) + 10;
            int var17 = 0;

            
                x4.set((double)x.nextFloat() - 0.5D, (double)x.nextFloat(), (double)x.nextFloat() - 0.5D);
                x4.normalize();
                double var18 = ((double)var14 / 10.0D + 4.0D) * (double)(1.0F + 1.0F * x.nextFloat());
                x4.x *= var18;
                x4.z *= var18;
                x4.y = x4.y * (double)(var9 - 15) + (double)var14 / 10.0D;

                for(int ix = 0; ix < 5; ix++)
                {if (var14 < 8)
                {
                    switch (var14)
                    {
                        case 0:
                            x3.set((double)(y - 1), (double)(z + 6), (double)(var5 - 1));
                            break;

                        case 1:
                            x3.set((double)(y - 1), (double)(z + 6), (double)var5);
                            break;

                        case 2:
                            x3.set((double)(y - 1), (double)(z + 6), (double)(var5 + 1));
                            break;

                        case 3:
                            x3.set((double)y, (double)(z + 6), (double)(var5 + 1));
                            break;

                        case 4:
                            x3.set((double)(y + 1), (double)(z + 6), (double)(var5 + 1));
                            break;

                        case 5:
                            x3.set((double)(y + 1), (double)(z + 6), (double)var5);
                            break;

                        case 6:
                            x3.set((double)(y + 1), (double)(z + 6), (double)(var5 - 1));
                            break;

                        default:
                            x3.set((double)y, (double)(z + 6), (double)(var5 - 1));
                    }
                }
                
                else
                {
                    x3.set((double)(y + x.nextInt(3) - 1), (double)(z + 6), (double)(var5 + x.nextInt(3) - 1));
                }

                long x0 = x.nextLong();
                nawapsk var13 = new nawapsk(x3, x4, x0);

                while (true)
                {
                    if (var13.iterate())
                    {
                        Vector3 x2 = var13.get();

                        if (this.fillBlock(var1, (int)Math.floor(x2.x), (int)Math.floor(x2.y), (int)Math.floor(x2.z)))
                        {
                            continue;
                        }
                    }

                    ++var17;
                    break;
                }
                return true;
            }
        
            
       
    }
		return false;
}}