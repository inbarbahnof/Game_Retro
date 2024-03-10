package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * TreeLeafs class represents the leaves of a tree in the game world.
 * It generates and manages the leaves of a tree.
 * It also implements the AvatarObserver interface to handle avatar actions.
 *
 * @author Daniel, Inbar
 */
public class TreeLeafs implements AvatarObserver {
    private static final Color GREEN = new Color(50, 200, 30);
    private static final float BUSH_SIZE = 150;
    private static final float LEAF_SIZE = 15;
    private ArrayList<Leaf> leafs;

    /**
     * Constructs a new TreeLeafs object with the specified parameters.
     *
     * @param pos the position of the tree
     * @param numLeafs the number of leaves to generate
     * @param treeHeight the height of the tree
     */
    public TreeLeafs(Vector2 pos, int numLeafs, float treeHeight) {
        ArrayList<Leaf> leafs = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numLeafs; i++) {
            float randomX = pos.x() - BUSH_SIZE / 2 + rand.nextFloat() * BUSH_SIZE;
            float randomY = (pos.y() - BUSH_SIZE / 2 + rand.nextFloat() * BUSH_SIZE) - treeHeight;
            Vector2 position = new Vector2(randomX, randomY);
            Leaf leaf = new Leaf(position, new Vector2(LEAF_SIZE, LEAF_SIZE),
                    new RectangleRenderable(ColorSupplier.approximateColor(GREEN)));
            leafs.add(leaf);
        }
        this.leafs = leafs;
    }

    /**
     * Retrieves the list of leaves of the tree.
     *
     * @return the list of leaves
     */
    public ArrayList<Leaf> getLeafs(){
        return leafs;
    }

    /**
     * Handles the jump action of the avatar.
     * Changes the color of each leaf in the tree.
     */
    @Override
    public void onJump() {
        for (Leaf leaf : leafs){
            leaf.changeColor();
        }
    }
}
