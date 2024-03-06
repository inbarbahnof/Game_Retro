package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

/**
 * Represents the energy UI component in the game.
 * Extends GameObject to inherit position, size, and rendering functionality.
 * Responsible for displaying the energy level.
 * @author Daniel, inbar
 */
public class EnergyUI extends GameObject {
    private float energy; // Energy level to be displayed
    private TextRenderable textRenderable;

    /**
     * Constructs an EnergyUI object.
     * @param pos The position of the energy UI.
     * @param size The size of the energy UI.
     * @param renderable The renderable object for rendering the energy UI.
     */
    public EnergyUI(Vector2 pos, Vector2 size, Renderable renderable) {
        super(pos, size, renderable);
        this.textRenderable = (TextRenderable) renderable;
        this.energy = 100; // energy level at start
    }

    /**
     * Updates the energy level to be displayed.
     * @param energy The new energy level.
     */
    public void updateEnergy(float energy) {
        this.energy = energy;
        if (textRenderable != null) {
            textRenderable.setString("Energy: " + (int) energy);
        }
    }
}
