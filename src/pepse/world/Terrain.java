package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import pepse.world.Block;

/**
 * makes Terrain game object
 * @author Daniel,  Inbar
 */
public class Terrain {
    private static final float INIT_X_GROUND = (2.0f/3.0f);
    private static final int BLOCK_SIZE = Block.SIZE;
    private static final Color BASE_GROUND_COLOR =
            new Color(212, 123, 74);
    private final NoiseGenerator noiseGenerator;
    private float groundHeightAtX0;
    private final float windowHeigth;
    private ArrayList<Float> blockHeights;

    /**
     * constructor
     * @param windowDimensions the window dimensions
     * @param seed the random seed
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.x() * INIT_X_GROUND;
        double seedDouble = seed;
        noiseGenerator = new NoiseGenerator(seedDouble,(int)groundHeightAtX0);
        windowHeigth = windowDimensions.y();
    }

    /**
     * returns the ground height at a certain x
     * @param x the x
     * @return the height
     */
    public float getGroundHeightAtX0(float x){
        int index = (int) x;
        return blockHeights.get(index);
    }

    /**
     *  function that creates a field of blocks using the getGroundHeightAtX0 function
     * and returns a list of blocks
     * @param minX the minimum x value of the range
     * @param maxX the maximum x value of the range
     * @return a list of blocks
     *
     */
    public List<Block> createInRange(int minX, int maxX){
        ArrayList<Block> blocks = new ArrayList<>();
        blockHeights = new ArrayList<>();
        // make it multiplied by block size
         minX =(minX/BLOCK_SIZE) * BLOCK_SIZE;
         maxX = (int)Math.ceil(maxX/BLOCK_SIZE) * BLOCK_SIZE;

         for (int x = minX; x <= maxX; x += BLOCK_SIZE) {
             float noise = (float) noiseGenerator.noise(x, BLOCK_SIZE * 7);
             float y = ((groundHeightAtX0 + noise) / BLOCK_SIZE) * BLOCK_SIZE;
             blockHeights.add(((y + BLOCK_SIZE) - (2/3f) * windowHeigth));

             int Depth = (int)(windowHeigth - (2*windowHeigth/(3*BLOCK_SIZE)));
             for (int i = 0; i < Depth; i++) {
                 Vector2 placement = new Vector2(x, ((y + (i * BLOCK_SIZE)) - (2/3f) * windowHeigth));
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
