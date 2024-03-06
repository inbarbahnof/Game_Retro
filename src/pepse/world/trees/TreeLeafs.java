package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TreeLeafs implements AvatarObserver {
    private static final Color GREEN = new Color(50, 200, 30);
    private static final float BUSH_SIZE = 80;
    private static final float LEAF_SIZE = 15;
    private ArrayList<Leaf> leafs;

    /**
     * creates new leafs game object and returns it
     * @param pos the position of the log
     * @return the new leafs game object
     */
    public TreeLeafs(Vector2 pos, int numLeafs) {
        ArrayList<Leaf> leafs = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < numLeafs; i++) {
            float randomX = pos.x() - BUSH_SIZE / 2 + rand.nextFloat() * BUSH_SIZE;
            float randomY = pos.y() - BUSH_SIZE / 2 + rand.nextFloat() * BUSH_SIZE;
            Vector2 position = new Vector2(randomX, randomY);
            Leaf leaf = new Leaf(position, new Vector2(LEAF_SIZE, LEAF_SIZE),
                    new RectangleRenderable(ColorSupplier.approximateColor(GREEN)));
            leafs.add(leaf);
        }
        this.leafs = leafs;
    }

    @Override
    public void onJump() {
        for (Leaf leaf : leafs){
            leaf.changeColor();
        }
    }
}
