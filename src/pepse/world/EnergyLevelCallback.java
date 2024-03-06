package pepse.world;

/**
 * Defines a callback interface for handling changes in energy level.
 * @author Daniel, Inbar
 */
public interface EnergyLevelCallback {
    /**
     * Called when the energy level changes.
     * @param energy The new energy level.
     */
    void onEnergyLevelChanged(float energy);
}
