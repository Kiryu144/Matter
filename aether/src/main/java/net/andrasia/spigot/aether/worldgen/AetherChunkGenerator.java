package net.andrasia.spigot.aether.worldgen;

import net.andrasia.spigot.aether.Aether;
import net.andrasia.spigot.aether.item.AetherBlocks;
import net.andrasia.spigot.core.Core;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AetherChunkGenerator extends ChunkGenerator
{
    public static class NoiseGeneratorPerlin
    {
        private int[] d;
        public double a;
        public double b;
        public double c;
        private static final double[] e = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
        private static final double[] f = new double[] { 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D};
        private static final double[] g = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};
        private static final double[] h = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
        private static final double[] i = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};

        public NoiseGeneratorPerlin() {
            this(new Random());
        }

        public NoiseGeneratorPerlin(Random random) {
            this.d = new int[512];
            this.a = random.nextDouble() * 256.0D;
            this.b = random.nextDouble() * 256.0D;
            this.c = random.nextDouble() * 256.0D;

            int i;

            for (i = 0; i < 256; this.d[i] = i++) {
                ;
            }

            for (i = 0; i < 256; ++i) {
                int j = random.nextInt(256 - i) + i;
                int k = this.d[i];

                this.d[i] = this.d[j];
                this.d[j] = k;
                this.d[i + 256] = this.d[i];
            }
        }

        public final double b(double d0, double d1, double d2) {
            return d1 + d0 * (d2 - d1);
        }

        public final double a(int i, double d0, double d1) {
            int j = i & 15;

            return h[j] * d0 + f[j] * d1;
        }

        public final double a(int i, double d0, double d1, double d2) {
            int j = i & 15;

            return e[j] * d0 + f[j] * d1 + g[j] * d2;
        }

        public void a(double[] adouble, double d0, double d1, double d2, int i, int j, int k, double d3, double d4, double d5, double d6) {
            int l;
            int i1;
            double d7;
            double d8;
            double d9;
            int j1;
            double d10;
            int k1;
            int l1;
            int i2;
            int j2;

            if (j == 1) {
                boolean flag = false;
                boolean flag1 = false;
                boolean flag2 = false;
                boolean flag3 = false;
                double d11 = 0.0D;
                double d12 = 0.0D;

                j2 = 0;
                double d13 = 1.0D / d6;

                for (int k2 = 0; k2 < i; ++k2) {
                    d7 = d0 + (double) k2 * d3 + this.a;
                    int l2 = (int) d7;

                    if (d7 < (double) l2) {
                        --l2;
                    }

                    int i3 = l2 & 255;

                    d7 -= (double) l2;
                    d8 = d7 * d7 * d7 * (d7 * (d7 * 6.0D - 15.0D) + 10.0D);

                    for (j1 = 0; j1 < k; ++j1) {
                        d9 = d2 + (double) j1 * d5 + this.c;
                        k1 = (int) d9;
                        if (d9 < (double) k1) {
                            --k1;
                        }

                        l1 = k1 & 255;
                        d9 -= (double) k1;
                        d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0D - 15.0D) + 10.0D);
                        l = this.d[i3] + 0;
                        int j3 = this.d[l] + l1;
                        int k3 = this.d[i3 + 1] + 0;

                        i1 = this.d[k3] + l1;
                        d11 = this.b(d8, this.a(this.d[j3], d7, d9), this.a(this.d[i1], d7 - 1.0D, 0.0D, d9));
                        d12 = this.b(d8, this.a(this.d[j3 + 1], d7, 0.0D, d9 - 1.0D), this.a(this.d[i1 + 1], d7 - 1.0D, 0.0D, d9 - 1.0D));
                        double d14 = this.b(d10, d11, d12);

                        i2 = j2++;
                        adouble[i2] += d14 * d13;
                    }
                }
            } else {
                l = 0;
                double d15 = 1.0D / d6;

                i1 = -1;
                boolean flag4 = false;
                boolean flag5 = false;
                boolean flag6 = false;
                boolean flag7 = false;
                boolean flag8 = false;
                boolean flag9 = false;
                double d16 = 0.0D;

                d7 = 0.0D;
                double d17 = 0.0D;

                d8 = 0.0D;

                for (j1 = 0; j1 < i; ++j1) {
                    d9 = d0 + (double) j1 * d3 + this.a;
                    k1 = (int) d9;
                    if (d9 < (double) k1) {
                        --k1;
                    }

                    l1 = k1 & 255;
                    d9 -= (double) k1;
                    d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0D - 15.0D) + 10.0D);

                    for (int l3 = 0; l3 < k; ++l3) {
                        double d18 = d2 + (double) l3 * d5 + this.c;
                        int i4 = (int) d18;

                        if (d18 < (double) i4) {
                            --i4;
                        }

                        int j4 = i4 & 255;

                        d18 -= (double) i4;
                        double d19 = d18 * d18 * d18 * (d18 * (d18 * 6.0D - 15.0D) + 10.0D);

                        for (int k4 = 0; k4 < j; ++k4) {
                            double d20 = d1 + (double) k4 * d4 + this.b;
                            int l4 = (int) d20;

                            if (d20 < (double) l4) {
                                --l4;
                            }

                            int i5 = l4 & 255;

                            d20 -= (double) l4;
                            double d21 = d20 * d20 * d20 * (d20 * (d20 * 6.0D - 15.0D) + 10.0D);

                            if (k4 == 0 || i5 != i1) {
                                i1 = i5;
                                int j5 = this.d[l1] + i5;
                                int k5 = this.d[j5] + j4;
                                int l5 = this.d[j5 + 1] + j4;
                                int i6 = this.d[l1 + 1] + i5;

                                j2 = this.d[i6] + j4;
                                int j6 = this.d[i6 + 1] + j4;

                                d16 = this.b(d10, this.a(this.d[k5], d9, d20, d18), this.a(this.d[j2], d9 - 1.0D, d20, d18));
                                d7 = this.b(d10, this.a(this.d[l5], d9, d20 - 1.0D, d18), this.a(this.d[j6], d9 - 1.0D, d20 - 1.0D, d18));
                                d17 = this.b(d10, this.a(this.d[k5 + 1], d9, d20, d18 - 1.0D), this.a(this.d[j2 + 1], d9 - 1.0D, d20, d18 - 1.0D));
                                d8 = this.b(d10, this.a(this.d[l5 + 1], d9, d20 - 1.0D, d18 - 1.0D), this.a(this.d[j6 + 1], d9 - 1.0D, d20 - 1.0D, d18 - 1.0D));
                            }

                            double d22 = this.b(d21, d16, d7);
                            double d23 = this.b(d21, d17, d8);
                            double d24 = this.b(d19, d22, d23);

                            i2 = l++;
                            adouble[i2] += d24 * d15;
                        }
                    }
                }
            }
        }
    }

    public static class NoiseGeneratorOctaves
    {

        private NoiseGeneratorPerlin[] a;
        private int b;

        public NoiseGeneratorOctaves(Random random, int i) {
            this.b = i;
            this.a = new NoiseGeneratorPerlin[i];

            for (int j = 0; j < i; ++j) {
                this.a[j] = new NoiseGeneratorPerlin(random);
            }
        }

        public static long d(double d0) {
            long i = (long) d0;

            return d0 < (double) i ? i - 1L : i;
        }

        public double[] a(double[] adouble, int i, int j, int k, int l, int i1, int j1, double d0, double d1, double d2) {
            if (adouble == null) {
                adouble = new double[l * i1 * j1];
            } else {
                for (int k1 = 0; k1 < adouble.length; ++k1) {
                    adouble[k1] = 0.0D;
                }
            }

            double d3 = 1.0D;

            for (int l1 = 0; l1 < this.b; ++l1) {
                double d4 = (double) i * d3 * d0;
                double d5 = (double) j * d3 * d1;
                double d6 = (double) k * d3 * d2;
                long i2 = d(d4);
                long j2 = d(d6);

                d4 -= (double) i2;
                d6 -= (double) j2;
                i2 %= 16777216L;
                j2 %= 16777216L;
                d4 += (double) i2;
                d6 += (double) j2;
                this.a[l1].a(adouble, d4, d5, d6, l, i1, j1, d0 * d3, d1 * d3, d2 * d3, d3);
                d3 /= 2.0D;
            }

            return adouble;
        }

        public double[] a(double[] adouble, int i, int j, int k, int l, double d0, double d1, double d2) {
            return this.a(adouble, i, 10, j, k, 1, l, d0, 1.0D, d1);
        }
    }

    public static class AetherGenQuicksoil
    {
        protected int range = 8;
        protected Random rand = new Random();
        protected World world;

        public void generate(World worldIn, int x, int z, ChunkGenerator.ChunkData primer)
        {
            int i = this.range;
            this.world = worldIn;
            this.rand.setSeed(worldIn.getSeed());
            long j = this.rand.nextLong();
            long k = this.rand.nextLong();

            for (int l = x - i; l <= x + i; ++l)
            {
                for (int i1 = z - i; i1 <= z + i; ++i1)
                {
                    long j1 = (long) l * j;
                    long k1 = (long) i1 * k;
                    this.rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());
                    this.recursiveGenerate(worldIn, l, i1, x, z, primer);
                }
            }
        }

        public void generate(ChunkGenerator.ChunkData chunkPrimer, int posX, int posY, int posZ)
        {
            for (int x = posX - 3; x < posX + 4; x++)
            {
                for (int z = posZ - 3; z < posZ + 4; z++)
                {
                    if (chunkPrimer.getBlockData(x, posY, z).getMaterial().isAir() && ((x - posX) * (x - posX) + (z - posZ) * (z - posZ)) < 12)
                    {
                        chunkPrimer.setBlock(x, posY, z, AetherBlocks.QUICKSOIL.getDefaultBlockData());
                    }
                }
            }
        }

        protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkGenerator.ChunkData chunkPrimerIn)
        {
            if (this.rand.nextInt(10) == 0)
            {
                for (int x = 3; x < 12; x++)
                {
                    for (int z = 3; z < 12; z++)
                    {
                        for (int n = 3; n < 48; n++)
                        {
                            if (chunkPrimerIn.getBlockData(x, n, z).getMaterial().isAir() && Core.getInstance().getCustomBlockRegistry().getCustomBlock(chunkPrimerIn.getBlockData(x, n + 1, z)) == AetherBlocks.AETHER_GRASS && chunkPrimerIn.getBlockData(x, n + 2, z).getMaterial().isAir())
                            {
                                this.generate(chunkPrimerIn, x, n, z);
                                n += 128;
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome)
    {
        Random noiseRandom = new Random(world.getSeed());
        NoiseGeneratorOctaves noiseGen, perlinNoise;
        noiseGen = new NoiseGeneratorOctaves(noiseRandom, 16);
        perlinNoise = new NoiseGeneratorOctaves(noiseRandom, 8);

        random.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkData chunkData = this.createChunkData(world);
        this.setBlocksInChunk(x, z, chunkData, perlinNoise, noiseGen);
        this.buildSurfaces(x, z, random, chunkData);

        (new AetherGenQuicksoil()).generate(world, x, z, chunkData);

        return chunkData;
    }

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world)
    {
        return Collections.singletonList(new AetherTreePopulator());
    }

    @Override
    public boolean isParallelCapable()
    {
        return false;
    }

    private void setBlocksInChunk(int x, int z, ChunkData chunkData, NoiseGeneratorOctaves perlinNoise, NoiseGeneratorOctaves noiseGen)
    {
        double[] buffer = null;
        buffer = this.setupNoiseGenerators(buffer, x * 2, z * 2, perlinNoise, noiseGen);

        for (int i1 = 0; i1 < 2; i1++)
        {
            for (int j1 = 0; j1 < 2; j1++)
            {
                for (int k1 = 0; k1 < 32; k1++)
                {
                    double d1 = buffer[(i1 * 3 + j1) * 33 + k1];
                    double d2 = buffer[(i1 * 3 + (j1 + 1)) * 33 + k1];
                    double d3 = buffer[((i1 + 1) * 3 + j1) * 33 + k1];
                    double d4 = buffer[((i1 + 1) * 3 + (j1 + 1)) * 33 + k1];

                    double d5 = (buffer[(i1 * 3 + j1) * 33 + (k1 + 1)] - d1) * 0.25D;
                    double d6 = (buffer[(i1 * 3 + (j1 + 1)) * 33 + (k1 + 1)] - d2) * 0.25D;
                    double d7 = (buffer[((i1 + 1) * 3 + j1) * 33 + (k1 + 1)] - d3) * 0.25D;
                    double d8 = (buffer[((i1 + 1) * 3 + (j1 + 1)) * 33 + (k1 + 1)] - d4) * 0.25D;

                    for (int l1 = 0; l1 < 4; l1++)
                    {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.125D;
                        double d13 = (d4 - d2) * 0.125D;

                        for (int i2 = 0; i2 < 8; i2++)
                        {
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.125D;

                            for (int k2 = 0; k2 < 8; k2++)
                            {
                                int x1 = i2 + i1 * 8;
                                int y = l1 + k1 * 4;
                                int z1 = k2 + j1 * 8;

                                BlockData filler = Material.AIR.createBlockData();

                                if (d15 > 0.0D)
                                {
                                    filler = AetherBlocks.HOLYSTONE.getDefaultBlockData();
                                }

                                chunkData.setBlock(x1, y, z1, filler);

                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }

                }

            }

        }

    }

    private void buildSurfaces(int i, int j, Random random, ChunkData chunkData)
    {
        for (int k = 0; k < 16; k++)
        {
            for (int l = 0; l < 16; l++)
            {
                int j1 = -1;
                int i1 = (int) (3.0D + random.nextDouble() * 0.25D);

                BlockData top = AetherBlocks.AETHER_GRASS.getDefaultBlockData();
                BlockData filler = AetherBlocks.AETHER_DIRT.getDefaultBlockData();

                for (int k1 = 127; k1 >= 0; k1--)
                {
                    BlockData block = chunkData.getBlockData(k, k1, l);

                    if (block.getMaterial().isAir())
                    {
                        j1 = -1;
                    }
                    else if (Core.getInstance().getCustomBlockRegistry().getCustomBlock(block) == AetherBlocks.HOLYSTONE)
                    {
                        if (j1 == -1)
                        {
                            if (i1 <= 0)
                            {
                                top = Material.AIR.createBlockData();
                                filler = AetherBlocks.HOLYSTONE.getDefaultBlockData();
                            }

                            j1 = i1;

                            if (k1 >= 0)
                            {
                                chunkData.setBlock(k, k1, l, top);
                            }
                            else
                            {
                                chunkData.setBlock(k, k1, l, filler);
                            }
                        }
                        else if (j1 > 0)
                        {
                            --j1;
                            chunkData.setBlock(k, k1, l, filler);
                        }
                    }
                }
            }
        }
    }

    private double[] setupNoiseGenerators(double[] buffer, int x, int z, NoiseGeneratorOctaves perlinNoise, NoiseGeneratorOctaves noiseGen)
    {
        if (buffer == null)
        {
            buffer = new double[3366];
        }

        double d = 1368.824D;
        double d1 = 684.41200000000003D;

        double[] pnr = null, ar = null, br = null;

        pnr = perlinNoise.a(pnr, x, 0, z, 3, 33, 3, d / 80D, d1 / 160D, d / 80D);
        ar = noiseGen.a(ar, x, 0, z, 3, 33, 3, d, d1, d);
        br = noiseGen.a(br, x, 0, z, 3, 33, 3, d, d1, d);

        int id = 0;

        for (int j2 = 0; j2 < 3; j2++)
        {
            for (int l2 = 0; l2 < 3; l2++)
            {
                for (int j3 = 0; j3 < 33; j3++)
                {
                    double d8;

                    double d10 = ar[id] / 512D;
                    double d11 = br[id] / 512D;
                    double d12 = (pnr[id] / 10D + 1.0D) / 2D;

                    if (d12 < 0.0D)
                    {
                        d8 = d10;
                    }
                    else if (d12 > 1.0D)
                    {
                        d8 = d11;
                    }
                    else
                    {
                        d8 = d10 + (d11 - d10) * d12;
                    }

                    d8 -= 8D;

                    if (j3 > 33 - 32)
                    {
                        double d13 = (float) (j3 - (33 - 32)) / ((float) 32 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30D * d13;
                    }

                    if (j3 < 8)
                    {
                        double d14 = (float) (8 - j3) / ((float) 8 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30D * d14;
                    }

                    buffer[id] = d8;

                    id++;
                }
            }
        }
        return buffer;
    }
}
