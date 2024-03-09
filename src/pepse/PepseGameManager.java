package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.Block;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.EnergyUI;
import pepse.world.trees.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The game manager class for the Pepse game.
 * Manages the initialization and setup of game elements.
 * @author Daniel, inbar
 */
public class PepseGameManager extends GameManager {

    private int CYCLE_LENGTH = 30;
    private Vector2 windowDimentions;
    private Terrain terrain;

    /**
     * The entry point of the program. Creates an instance of PepseGameManager and runs the game.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    /**
     * Initializes the game components including terrain, sky, sun, avatar, and energy UI.
     * @param imageReader The ImageReader object for reading images.
     * @param soundReader The SoundReader object for reading sounds.
     * @param inputListener The UserInputListener object for handling user input.
     * @param windowController The WindowController object for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimentions = windowController.getWindowDimensions();
        // create terrain
        makeTerrain(windowController);

        // create sky
        makeSky(imageReader);

        // create sun
        makeSunAndHalo();

        // add avatar
        Avatar avatar = makeAvatar(inputListener, imageReader);

        // create energy UI
        makeEnergy(avatar);

        // create flora
        makeFlora(avatar);

        // create night
        GameObject night = Night.create(windowDimentions, CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

    }

    /**
     * makes the flora class
     * @param avatar the avatar of the game
     */
    private void makeFlora(Avatar avatar) {
        Flora.GroundHeightProvider groundHeightProvider = x -> terrain.getGroundHeightAtX0(x);
        Flora flora = new Flora(avatar, groundHeightProvider);
        System.out.println(windowDimentions.x());
        HashMap<TreeLog, ArrayList<Leaf>> trees = flora.createInRange(0,(int) windowDimentions.x());

        for (HashMap.Entry<TreeLog, ArrayList<Leaf>> entry : trees.entrySet()){
            TreeLog treeLog = entry.getKey();
            ArrayList<Leaf> leaves = entry.getValue();

            gameObjects().addGameObject(treeLog, Layer.STATIC_OBJECTS);
            avatar.addObserver(treeLog);

            for (Leaf leaf : leaves){
                gameObjects().addGameObject(leaf, Layer.BACKGROUND);
            }
        }
        HashMap<GameObject, ArrayList<Fruit>> fruits = flora.getFruits();
        for (HashMap.Entry<GameObject, ArrayList<Fruit>> entry : fruits.entrySet()){
            ArrayList<Fruit> treeFruits = entry.getValue();
            for (Fruit fruit : treeFruits){
                gameObjects().addGameObject(fruit, Layer.DEFAULT);
            }
        }
    }


    /**
     * Creates the energy UI and adds it to the game objects.
     * @param avatar The Avatar object to bind energy UI to.
     */
    private void makeEnergy(Avatar avatar){
        Vector2 energyUIPosition = new Vector2(20, 20);
        Vector2 energyUISize = new Vector2(200, 30);
        Renderable energyUIRenderable = new TextRenderable("Energy: 100");
        EnergyUI energyUI = new EnergyUI(energyUIPosition, energyUISize, energyUIRenderable);
        avatar.setEnergyLevelCallback(energyUI::updateEnergy);
        gameObjects().addGameObject(energyUI, Layer.UI);
    }

    /**
     * Creates an Avatar object and adds it to the game objects.
     * @param inputListener The UserInputListener object for handling user input.
     * @param imageReader The ImageReader object for reading images.
     * @return The created Avatar object.
     */
    private Avatar makeAvatar(UserInputListener inputListener, ImageReader imageReader){
        int index = (int)((windowDimentions.x()/2)/windowDimentions.x());
        Vector2 avatarPlacment = new Vector2(windowDimentions.x()/2
                , terrain.getGroundHeightAtX0(index));
        Avatar avatar = new Avatar(avatarPlacment,inputListener,imageReader);
        gameObjects().addGameObject(avatar,Layer.DEFAULT);
        return avatar;
    }

    /**
     * Creates the Sun and its halo and adds them to the game objects.
     */
    private void makeSunAndHalo(){
        GameObject sun = Sun.create(windowDimentions, CYCLE_LENGTH*2);
        //create sun halo
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
    }

    /**
     * Creates the terrain and adds its blocks to the game objects.
     * @param windowController The WindowController object for managing the game window.
     */
    private void makeTerrain(WindowController windowController){
        terrain = new Terrain(windowDimentions, 0);
        List<Block> blocks = terrain.createInRange(0, (int) windowController.getWindowDimensions().x());

        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

    }

    /**
     * Creates the sky GameObject and adds it to the game objects.
     * @param imageReader The ImageReader object for reading images.
     */
    private void makeSky(ImageReader imageReader){
        GameObject sky = Sky.create(windowDimentions);
        Renderable skyImage = imageReader.readImage("assets/sky.jpg", true);
        sky.renderer().setRenderable(skyImage);
        gameObjects().addGameObject(sky, Layer.BACKGROUND); // made it background so it's drawn first
    }
}
