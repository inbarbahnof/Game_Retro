package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * makes sky game object
 * @author Daniel,  Inbar
 */
public class Sky {
    private  static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * creates the sky gameobject
     * @param windowDimensions the window Dimensions
     * @return sky
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setTag("sky");
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return sky;
    }
}
