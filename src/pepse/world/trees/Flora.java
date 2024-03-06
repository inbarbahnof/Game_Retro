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
    private static final Color GREEN = new Color(50, 200, 30);
    private static final Color BROWN = new Color(100, 50, 20);
    private static final float BUSH_SIZE = 80;
    private static final float LEAF_SIZE = 15;
    private static final float MIN_TREE_HEIGHT = 30;
    private static final float MAX_TREE_HEIGHT = 50;
    private static final float BLOCK_SIZE = 30;
    private Avatar avatar;
    private GroundHeightProvider groundHeightProvider; // Functional interface
    private HashMap<GameObject, ArrayList<GameObject>> trees;
    private Random random;

    public Flora(Avatar avatar, GroundHeightProvider groundHeightProvider){
        this.avatar = avatar;
        this.groundHeightProvider = groundHeightProvider;
        this.random = new Random();
    }

    /**
     * makes the trees at certain locations
     */
    public HashMap<GameObject, ArrayList<GameObject>> createInRange(int minX, int maxX) {
        HashMap<GameObject, ArrayList<GameObject>> treesMap = new HashMap<>();

        //TODO
        // calculate the tree position, call to create tree
        // for each tree, make the leafs
        // add the tree log->leafs to treesmap

        return treesMap;
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

    @FunctionalInterface
    public interface GroundHeightProvider {
        float getGroundHeightAtX0(float x);
    }
}
