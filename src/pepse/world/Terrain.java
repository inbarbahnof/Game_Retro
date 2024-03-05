package pepse.world;

import danogl.util.Vector2;
import pepse.util.NoiseGenerator;

public class Terrain {
    private static final int INIT_X_GROUND = (2/3);
    private float groundHeightAtX0;
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.x() * INIT_X_GROUND;


        /*     * public float groundHeightAt(float x) {
         *           float noise = (float) noiseGenerator.noise(x, BLOCK_SIZE *7);
         *           return groundHeightAtX0 + noise;
         *       }*/

    }
    public float getGroundHeightAtX0(float x){
         return 0;
    }
}
