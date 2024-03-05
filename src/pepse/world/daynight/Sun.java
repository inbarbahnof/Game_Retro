package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Consumer;

public class Sun {
    public  final static float SUN_RADIUS = 30;
    private final static float TWO_THIRDS= 2/3f;
    private final static Vector2 SUN_SIZE = new Vector2(2*SUN_RADIUS, SUN_RADIUS*2);
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        Renderable skyRenderable = new OvalRenderable(Color.YELLOW);
        Vector2 initialSunCenter = new Vector2(windowDimensions.x()/2 - SUN_RADIUS, 90);
        GameObject sun = new GameObject(initialSunCenter, SUN_SIZE, skyRenderable);
        sun.setTag("sun");
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        Vector2 cycleCenter = new Vector2(windowDimensions.x()/2, windowDimensions.y()*TWO_THIRDS);
        Consumer<Float> sunChange = (Float angle)-> sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle)
                .add(cycleCenter));
        Transition transition = new Transition(sun, sunChange, 0f, 360f, Transition
                .LINEAR_INTERPOLATOR_FLOAT, cycleLength,Transition.TransitionType.TRANSITION_LOOP
                ,null);
//
//        Transition transition = new Transition(night, nightChange, DAY_OPAQUENESS, NIGHT_OPAQUENESS,
//                Transition.CUBIC_INTERPOLATOR_FLOAT, CYCLE_LENGTH, Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
//                null);
        return sun;
    }


}
