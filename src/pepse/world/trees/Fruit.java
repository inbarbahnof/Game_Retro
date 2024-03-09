package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;

import java.awt.*;
import java.util.Random;

import static java.awt.Color.*;

/**
 * Fruit class represents a fruit GameObject in the game world.
 * It provides functionality to change the appearance of the fruit and handle collisions with the Avatar.
 * Fruit gives energy to the Avatar upon collision and then disappears for a certain duration.
 * After the duration, it reappears.
 *
 * @author Daniel, Inbar
 */
public class Fruit extends GameObject {
    private static final String AVATAR= "Avatar";
    private static final Color[] colors = {RED, PINK, ORANGE, MAGENTA, CYAN};
    private static final float ENERGY_BOOST = 10;
    private final Random random;
    private static final float APPEAR = 100;
    private static final float DISAPPEAR = 0;
    private boolean appearent= true;
    private static final long TIME_LAPSE = 30_000;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        random = new Random();
    }

    /**
     * Changes the appearance of the fruit upon jumping.
     */
    public void onJump() {
        int randomIndex = random.nextInt(colors.length);
        Color color = colors[randomIndex];
        Renderable renderable = new OvalRenderable(ColorSupplier.approximateColor(color));
        this.renderer().setRenderable(renderable);
    }

    /**
     * Handles collision with other GameObjects.
     * If collides with Avatar, gives energy and disappears for a duration.
     *
     * @param other the GameObject collided with
     * @param collision the details of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(AVATAR) && appearent){
            // give energy
            ((Avatar) other).addEnergy(ENERGY_BOOST);
            // dissaper for 30 sec
            renderer().setOpaqueness(DISAPPEAR);
            appearent = false;

            new Thread(() -> {
                try {
                    Thread.sleep(TIME_LAPSE); // 30 seconds in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Make the fruit reappear
                makeApperant();
            }).start();
        }

    }

    /**
     * Makes the fruit appear again after disappearing.
     */
    private void makeApperant(){
        renderer().setOpaqueness(APPEAR);
        appearent = true;
    }
}
