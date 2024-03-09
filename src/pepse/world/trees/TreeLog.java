package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarObserver;

import java.awt.*;
import java.util.Random;

import static java.awt.Color.PINK;

/**
 * TreeLog class represents a log GameObject in the game world.
 * It provides functionality to change the appearance of the log.
 * It also implements the AvatarObserver interface to handle avatar actions.
 *
 * @author Daniel, Inbar
 */
public class TreeLog extends GameObject implements AvatarObserver {
    private static final Color BROWN = new Color(100, 50, 20);
    private Renderable renderable;
    private Vector2 position;
    private Vector2 dimentions;

    /**
     * Constructs a new TreeLog object with the specified parameters.
     *
     * @param topLeftCorner the position of the log, in window coordinates (pixels)
     * @param dimensions the width and height of the log in window coordinates
     * @param renderable the renderable representing the log, can be null if not rendered
     */
    public TreeLog(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = renderable;
        this.position = topLeftCorner;
        this.dimentions = dimensions;

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * Handles the jump action of the avatar.
     * Changes the color of the log.
     */
    @Override
    public void onJump() {
        renderable = new RectangleRenderable(ColorSupplier.approximateColor(BROWN));
        this.renderer().setRenderable(renderable);
    }
}
