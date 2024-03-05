package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import pepse.world.Block;

public class Terrain {
    private static final float INIT_X_GROUND = (2.0f/3.0f);
    private static final int BLOCK_SIZE = Block.SIZE;
    private static  int terrainDepth ;
    private static final Color BASE_GROUND_COLOR =
            new Color(212, 123, 74);
    private final NoiseGenerator noiseGenerator;
    private float groundHeightAtX0;
    private final float windowWidth;
    private final float windowHeigth;
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.x() * INIT_X_GROUND;
        double seedDouble = seed;
        noiseGenerator = new NoiseGenerator(seedDouble,0);
        windowWidth = windowDimensions.x();
        windowHeigth = windowDimensions.y();
        terrainDepth = (int)windowDimensions.y()/(3*BLOCK_SIZE);


    }
    public float getGroundHeightAtX0(float x){
         float noise = (float) noiseGenerator.noise(x, BLOCK_SIZE *7);
         return groundHeightAtX0 + noise;
    
    }
    public List<Block> createInRange(int minX, int maxX){
        /**
         *  function that creates a field of blocks using the getGroundHeightAtX0 function
         * and returns a list of blocks
         * @param minX the minimum x value of the range
         * @param maxX the maximum x value of the range
         * @return a list of blocks
         *
         */
        List<Block> blocks = new ArrayList<>();
        // make it multiplied by block size
         minX =(minX/BLOCK_SIZE) * BLOCK_SIZE;
         maxX = (int)Math.ceil(maxX/BLOCK_SIZE) * BLOCK_SIZE;


         for (int x = minX; x <= maxX; x += BLOCK_SIZE) {
             int y = (int) (getGroundHeightAtX0(x) / BLOCK_SIZE) * BLOCK_SIZE;

             for (int i = 0; i < terrainDepth; i++) {
                 Vector2 placement = new Vector2(x,  windowHeigth-(y + i * BLOCK_SIZE));
                 RectangleRenderable rectangleRenderable = new RectangleRenderable
                         (ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                 Block block = new Block(placement, rectangleRenderable);
                 block.setTag("ground");
                 blocks.add(block);
             }
         }
        return blocks;
    }
}
