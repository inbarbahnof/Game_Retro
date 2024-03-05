package pepse.world.daynight;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import java.awt.*;

public class SunHalo {
    public static GameObject create(GameObject sun) {
        Color clr = new Color(255, 255, 0, 20);

        OvalRenderable haloRenderable = new OvalRenderable(clr);
        Vector2 haloSize = new Vector2(Sun.SUN_RADIUS * 4, Sun.SUN_RADIUS * 4);

        Vector2 placementHalo = sun.getTopLeftCorner();
        GameObject halo = new GameObject(placementHalo, haloSize, haloRenderable);
        halo.setCoordinateSpace(sun.getCoordinateSpace());
        halo.setTag("sunHalo");
        halo.setCenter(sun.getCenter());
        halo.addComponent((float deltaTime) ->
        {
            halo.setCenter(sun.getCenter());
        });
        return halo;

    }
}
