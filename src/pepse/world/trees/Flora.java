package pepse.world.trees;


import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * the flora class
 * @author Daniel, inbar
 */
public class Flora {
    private static final Color BROWN = new Color(100, 50, 20);
    private static final float BIOS_COIN_PROB = 0.1f;
    private static final int LEAF_BOUND = 50;
    private static final int FRUIT_BOUND = 15;
    private static final float MIN_TREE_HEIGHT = 100;
    private static final float MAX_TREE_HEIGHT = 250;
    private static final float BLOCK_SIZE = 30;
    private Avatar avatar;
    private GroundHeightProvider groundHeightProvider; // Functional interface
    private HashMap<GameObject, ArrayList<Fruit>> fruits;
    private Random random;

    /**
     * Constructs a Flora object with an Avatar and a GroundHeightProvider.
     *
     * @param avatar the Avatar object in the game world
     * @param groundHeightProvider the provider of ground height
     */
    public Flora(Avatar avatar, GroundHeightProvider groundHeightProvider){
        this.avatar = avatar;
        this.groundHeightProvider = groundHeightProvider;
        this.random = new Random();
        fruits = new HashMap<>();
    }

    /**
     * Generates trees with leaves and fruits within a specified range.
     *
     * @param minX the minimum X coordinate for tree generation
     * @param maxX the maximum X coordinate for tree generation
     * @return a HashMap containing TreeLog and corresponding ArrayList of leaves
     */
    public HashMap<TreeLog, ArrayList<Leaf>> createInRange(int minX, int maxX) {
        HashMap<TreeLog, ArrayList<Leaf>> treesMap = new HashMap<>();
        Random rand = new Random();

        for (int x = minX; x <= maxX; x += (int)BLOCK_SIZE) {
            if (rand.nextFloat() <= BIOS_COIN_PROB){
                float groundHeight = groundHeightProvider.getGroundHeightAtX0(x/BLOCK_SIZE);
                Vector2 position = new Vector2(x, groundHeight);
                TreeLog log = createTreeLog(position);
                int leafNum = rand.nextInt(LEAF_BOUND) + 5;
                int fruitNum = rand.nextInt(FRUIT_BOUND) + 1;

                TreeLeafs leafs = new TreeLeafs(position, leafNum, log.getDimensions().y());
                avatar.addObserver(leafs);
                ArrayList<Leaf> treeLeaf = leafs.getLeafs();

                TreeFruits fruit = new TreeFruits(position, fruitNum, log.getDimensions().y());
                ArrayList<Fruit> treeFruits = fruit.getTreeFruits();
                avatar.addObserver(fruit);

                fruits.put(log, treeFruits);
                treesMap.put(log, treeLeaf);
            }
        }
        return treesMap;
    }

    /**
     * Retrieves the generated fruits.
     *
     * @return a HashMap containing GameObject and corresponding ArrayList of fruits
     */
    public HashMap<GameObject, ArrayList<Fruit>> getFruits(){
        return fruits;
    }

    /**
     * creates a new log and returns it
     * @param pos the position of the ground at the wanted x
     * @return the new log game object
     */
    private TreeLog createTreeLog(Vector2 pos){
        float height = MIN_TREE_HEIGHT + random.nextFloat() * (MAX_TREE_HEIGHT - MIN_TREE_HEIGHT);
        Vector2 position = new Vector2(pos.x(), (pos.y() - height));
        TreeLog log =  new TreeLog(position, new Vector2(BLOCK_SIZE, height),
                new RectangleRenderable(ColorSupplier.approximateColor(BROWN)));
        log.setTag("treeLog");
        return log;
    }

    /**
     * Functional interface for providing ground height at a specific X coordinate.
     */
    @FunctionalInterface
    public interface GroundHeightProvider {
        /**
         * returns the Ground Height at a certain x
         * @param x the x coordinate
         * @return the height
         */
        float getGroundHeightAtX0(float x);
    }
}
