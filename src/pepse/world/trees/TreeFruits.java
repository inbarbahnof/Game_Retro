package pepse.world.trees;

import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarObserver;

import java.util.ArrayList;
import java.util.Random;

import static java.awt.Color.ORANGE;
import static java.awt.Color.RED;

/**
 * TreeFruits class represents the fruits of a tree in the game world.
 * It generates and manages the fruits of a tree.
 * It also implements the AvatarObserver interface to handle avatar actions.
 *
 * @author Daniel, Inbar
 */
public class TreeFruits implements AvatarObserver {
    private static final float FRUIT_SIZE = 15f;
    private static final float BUSH_SIZE = 150;
    private final ArrayList<Fruit> fruits;

    /**
     * Constructs a new TreeFruits object with the specified parameters.
     *
     * @param pos the position of the tree
     * @param numFruits the number of fruits to generate
     * @param treeHeight the height of the tree
     */
    public TreeFruits(Vector2 pos, int numFruits, float treeHeight){
        ArrayList<Fruit> fruits = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numFruits; i++) {
            float randomX = pos.x() - BUSH_SIZE / 2 + random.nextFloat() * BUSH_SIZE;
            float randomY = (pos.y() - BUSH_SIZE / 2 + random.nextFloat() * BUSH_SIZE) - treeHeight;
            Vector2 position = new Vector2(randomX, randomY);
            Fruit fruit = new Fruit(position, new Vector2(FRUIT_SIZE, FRUIT_SIZE),
                    new OvalRenderable(ColorSupplier.approximateColor(RED)));
            fruits.add(fruit);
        }
        this.fruits = fruits;
    }

    /**
     * Handles the jump action of the avatar.
     * Calls the onJump method for each fruit in the tree.
     */
    @Override
    public void onJump() {
        for (Fruit fruit:fruits)
            fruit.onJump();
    }

    /**
     * Retrieves the list of fruits of the tree.
     *
     * @return the list of fruits
     */
    public ArrayList<Fruit> getTreeFruits() {
        return fruits;
    }
}
