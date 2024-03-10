package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.Random;
import java.util.function.Consumer;

import static java.awt.Color.PINK;

/**
 * Leaf class represents a leaf GameObject in the game world.
 * It provides functionality to change the appearance and behavior of the leaf.
 *
 * @author Daniel, Inbar
 */
public class Leaf extends GameObject {
    private static final float WIDTH_DURATION = 5f;
    private static final float CIRCLE_END = 25f;
    private static final float ANGLE_DURATION = 20f;
    private static final float LEAF_MAX_WIDTH = 15f;
    private static final float LEAF_MIN_WIDTH = 12f;
    private static final float LEAF_ROTATION_ANGLE = 90f;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        Random random = new Random();
        Consumer<Float> angelConsumer = (Float angle) -> renderer().setRenderableAngle(angle);
        Consumer<Float> dimentionsConsumer = (Float width) ->
                setDimensions(new Vector2(width, dimensions.y()));
        Leaf leafInstance = this;
        float leafDegree = this.renderer().getRenderableAngle();
        Runnable angleTask = new Runnable() {
            @Override()
            public void run() {
                // Code to execute when the task runs
                new Transition(leafInstance, angelConsumer, leafDegree, leafDegree+CIRCLE_END,
                        Transition.LINEAR_INTERPOLATOR_FLOAT, ANGLE_DURATION,
                        Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
            }
        };

        Runnable dimentionTask = new Runnable() {
            @Override
            public void run() {
                // Code to execute when the task runs
                new Transition(leafInstance, dimentionsConsumer,
                        LEAF_MAX_WIDTH, LEAF_MIN_WIDTH,
                        Transition.LINEAR_INTERPOLATOR_FLOAT, WIDTH_DURATION,
                        Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
            }
        };
        new ScheduledTask(this, random.nextFloat(), false, angleTask);
        new ScheduledTask(this, random.nextFloat(), false, dimentionTask);
    }

    /**
     * Changes the color and rotation angle of the leaf.
     */
    public void changeColor(){
        Consumer<Float> angelConsumer = (Float angle) ->
                renderer().setRenderableAngle(angle);

        float leafDegree = this.renderer().getRenderableAngle();
        new Transition(this, angelConsumer, leafDegree, leafDegree+LEAF_ROTATION_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT, 1f,
                Transition.TransitionType.TRANSITION_ONCE, null);
    }
}
