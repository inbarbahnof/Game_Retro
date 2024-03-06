package pepse.world.daynight;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Consumer;

import danogl.*;

/**
 * Represents the night phase in the day-night cycle of the game.
 * Creates a GameObject that serves as a screen overlay to simulate darkness.
 * Manages the transition between day and night based on a specified cycle length.
 * Uses a Consumer to adjust the opacity of the night screen.
 * Utilizes a Transition component for smooth transitions between day and night.
 * @author Daniel, inbar
 */
public class Night {
    private static final Float NIGHT_OPAQUENESS = 0.5f;
    private static final Float DAY_OPAQUENESS = 0f;
    private static final Float CYCLE_LENGTH = 12f;

    /**
     * Creates a GameObject representing the night phase.
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength The length of the day-night cycle.
     * @return The GameObject representing the night phase.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        RectangleRenderable NightScreen = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, NightScreen);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");
        Consumer<Float> nightChange = opaqueness -> night.renderer().setOpaqueness(opaqueness);

        new Transition(night, nightChange, DAY_OPAQUENESS, NIGHT_OPAQUENESS,
                Transition.CUBIC_INTERPOLATOR_FLOAT, CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        return night;
    }
}
