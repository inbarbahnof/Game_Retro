package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

public class EnergyUI extends GameObject {
    private float energy; // Energy level to be displayed
    private TextRenderable textRenderable;

    public EnergyUI(Vector2 pos, Vector2 size, Renderable renderable) {
        super(pos, size, renderable);
        this.textRenderable = (TextRenderable) renderable;
        this.energy = 100; // energy level at start
    }

    // Update the energy level to display
    public void updateEnergy(float energy) {
        this.energy = energy;
        if (textRenderable != null) {
            System.out.println("Energy: " + (int) energy);
            textRenderable.setString("Energy: " + (int) energy);
        }
    }
}
