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

/**
 * the sun class
 * @author Daniel, inbar
 */
public class Sun {
    /**
     * the sun radius
     */
    public  final static float SUN_RADIUS = 25;
    private final static float TWO_THIRDS= 2/3f;
    private static final float CIRCLE_START = 0f;
    private static final float CIRCLE_END = 360f;
    private final static Vector2 SUN_SIZE = new Vector2(2*SUN_RADIUS, SUN_RADIUS*2);

    /**
     * creates the sun object
     * @param windowDimensions the window dimensions
     * @param cycleLength the cycle length
     * @return the sun gameobject
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        Renderable skyRenderable = new OvalRenderable(Color.YELLOW);
        Vector2 initialSunCenter = new Vector2(windowDimensions.x()/2 - SUN_RADIUS,
                windowDimensions.y()/2 - SUN_RADIUS);
        GameObject sun = new GameObject(initialSunCenter, SUN_SIZE, skyRenderable);
        sun.setTag("sun");
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        Vector2 cycleCenter = new Vector2(windowDimensions.x()/2, windowDimensions.y()*TWO_THIRDS);
        Consumer<Float> sunChange = (Float angle)->
                sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter));
      new Transition(sun, sunChange, CIRCLE_START, CIRCLE_END, Transition
                .LINEAR_INTERPOLATOR_FLOAT, cycleLength/2,
              Transition.TransitionType.TRANSITION_LOOP,null);
        return sun;
    }

//public class Sun {
//    static final float SUN_DIAMETER = 50;
//    public static GameObject create(Vector2 windowDimensions, float cycleLength){
//        Renderable sunRender = new OvalRenderable(Color.YELLOW);
//        Vector2 center = new Vector2(windowDimensions.x()*0.5f, windowDimensions.y()*0.5f);
//        System.out.println(center.x() + " sun " + center.y());
//        GameObject sun = new GameObject(center, new Vector2(SUN_DIAMETER,SUN_DIAMETER), sunRender);
//        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
//        sun.setTag("sun");
//        Vector2 cycleCenter = new Vector2(windowDimensions.x()*0.5f,
//                windowDimensions.y()*((float) 2/3));
//        float distanceFromCenter = windowDimensions.y() * ((float) 2 / 3) - center.y();
//        new Transition<Float>(sun,
//                    (Float angle) -> sun.setCenter
//                            (center.subtract(cycleCenter)
//                                    .rotated(angle)
//                                    .add(cycleCenter))
//                ,0f, 360f
//                ,Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
//                Transition.TransitionType.TRANSITION_LOOP, null);
}
