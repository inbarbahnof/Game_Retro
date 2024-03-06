package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarObserver;

import java.awt.*;
import java.util.Random;

public class TreeLog extends GameObject implements AvatarObserver {
    private static final Color BROWN = new Color(100, 50, 20);
    private Renderable renderable;
    private Vector2 position;
    private Vector2 dimentions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public TreeLog(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = renderable;
        this.position = topLeftCorner;
        this.dimentions = dimensions;
    }

    @Override
    public void onJump() {
        renderable = new RectangleRenderable(ColorSupplier.approximateColor(BROWN));
    }
}
