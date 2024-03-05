package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
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
import java.util.List;

public class PepseGameManager extends GameManager {

    private int skyLayer = 10;
    private int CYCLE_LENGTH = 30;

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

//        // create sky
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        Renderable skyImage = imageReader.readImage("assets/sky.jpg", true);
        sky.renderer().setRenderable(skyImage);
        gameObjects().addGameObject(sky, Layer.BACKGROUND); // made it background so it's drawn first
        // create terrain
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 0);
        List<Block> blocks = terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        // create night
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);


        // create sun
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH*2);
        //create sun halo
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        // add avatar
        Vector2 avatarPlacment = new Vector2(windowController.getWindowDimensions().x()/2
                , windowController.getWindowDimensions().y()*2/3);
        Avatar avatar = new Avatar(avatarPlacment,inputListener,imageReader);
        gameObjects().addGameObject(avatar,Layer.DEFAULT);
        // create energy UI
        Vector2 energyUIPosition = new Vector2(20, 20);
        Vector2 energyUISize = new Vector2(200, 30);
        Renderable energyUIRenderable = new TextRenderable("Energy: 100");
        EnergyUI energyUI = new EnergyUI(energyUIPosition, energyUISize, energyUIRenderable);
        avatar.setEnergyLevelCallback(energyUI::updateEnergy);
        gameObjects().addGameObject(energyUI, Layer.UI);

    }
}
