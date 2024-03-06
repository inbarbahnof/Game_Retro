package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.image.ImageObserver.HEIGHT;

/**
 * Represents the avatar in the game world.
 * Extends GameObject to inherit position, size, and rendering functionality.
 * Handles avatar movement, energy management, and animations.
 * @author Daniel, inbar
 */
public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final float MAX_ENERGY = 100;
    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    private static final float WIDTH = 50;
    private static final float HEIGHT = 78;
    private AnimationRenderable idleAnimation;
    private AnimationRenderable jumpAnimation;
    private AnimationRenderable runAnimation;
    private String currentAnimation;
    private boolean isFacingRight = true;
    private float energy;
    private UserInputListener inputListener;
    private EnergyLevelCallback energyLevelCallback;
    private ArrayList<AvatarObserver> observers;

    /**
     * Constructs an Avatar object with specified position, input listener, and image reader.
     * @param pos The initial position of the avatar.
     * @param inputListener The UserInputListener object for handling user input.
     * @param imageReader The ImageReader object for reading images.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(new Vector2(pos.x(), pos.y() - HEIGHT), new Vector2(WIDTH, HEIGHT),
                imageReader.readImage("assets/idle_0.png", true));
        energy = MAX_ENERGY;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        makeIdleAnimation(imageReader);
        makeJumpAnimation(imageReader);
        makeRunAnimation(imageReader);
        currentAnimation = "idle";
        renderer().setRenderable(idleAnimation);
        observers = new ArrayList<>();
    }

    /**
     * adda a new observer to the observer list
     * @param observer the observer
     */
    public void addObserver(AvatarObserver observer) {
        observers.add(observer);
    }

    /**
     * Adds energy to the avatar.
     * @param boost The amount of energy to add.
     */
    public void addEnergy(float boost){
        if (energy + boost <= MAX_ENERGY)
            energy += boost;
        else
            energy = MAX_ENERGY;
    }

    /**
     * Sets the callback for energy level changes.
     * @param energyLevelCallback The EnergyLevelCallback object to set.
     */
    public void setEnergyLevelCallback(EnergyLevelCallback energyLevelCallback) {
        this.energyLevelCallback = energyLevelCallback;
    }

    /**
     * Updates the avatar's state based on user input and energy level.
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if (energy >= 0.5f) {
            if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
                xVel -= VELOCITY_X;
                energy -= 0.5f;
                if (isFacingRight) {
                    // If moving left and previously facing right, change direction
                    renderer().setIsFlippedHorizontally(true); // Flip the rendering horizontally
                    isFacingRight = false; // Update the direction flag
                }
                if (!currentAnimation.equals("run")) {
                    currentAnimation = "run";
                    renderer().setRenderable(runAnimation);
                }
            } else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
                xVel += VELOCITY_X;
                energy -= 0.5f;
                if (!isFacingRight) {
                    // If moving right and previously facing left, change direction
                    renderer().setIsFlippedHorizontally(false); // Flip the rendering horizontally
                    isFacingRight = true; // Update the direction flag
                }
                if (!currentAnimation.equals("run")) {
                    currentAnimation = "run";
                    renderer().setRenderable(runAnimation);
                }
            } else {
                if (!currentAnimation.equals("idle")) {
                    currentAnimation = "idle";
                    renderer().setRenderable(idleAnimation);
                }
            }
        }
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy>=10f)
        {
            energy -= 10f;
            transform().setVelocityY(VELOCITY_Y);
            notifyJumpObservers();
            if (!currentAnimation.equals("jump")){
                currentAnimation = "jump";
                renderer().setRenderable(jumpAnimation);
            }
        }
        else if (getVelocity().magnitude()==0 && energy < MAX_ENERGY){
            energy += 1;
            if (!currentAnimation.equals("idle")){
                currentAnimation = "idle";
                renderer().setRenderable(idleAnimation);
            }
        }
        if (energyLevelCallback != null) {
            energyLevelCallback.onEnergyLevelChanged(energy);
        }
    }

    /**
     * notifies the jump observers that a jump happened
     */
    private void notifyJumpObservers() {
        for (AvatarObserver observer : observers) {
            observer.onJump();
        }
    }

    /**
     * Creates the idle animation renderable.
     * @param imageReader The ImageReader object for reading images.
     */
    private void makeIdleAnimation(ImageReader imageReader){
        Renderable[] imageList = new Renderable[4];
        for (int i = 0; i < 4; i++) {
            imageList[i] = imageReader.readImage("assets/idle_" + i + ".png", true);
        }
        idleAnimation = new AnimationRenderable(imageList,0.1f);
    }

    /**
     * Creates the jump animation renderable.
     * @param imageReader The ImageReader object for reading images.
     */
    private void makeJumpAnimation(ImageReader imageReader){
        Renderable[] imageList = new Renderable[4];
        for (int i = 0; i < 4; i++) {
            imageList[i] = imageReader.readImage("assets/jump_" + i + ".png", true);
        }
        jumpAnimation = new AnimationRenderable(imageList,0.1f);
    }

    /**
     * Creates the run animation renderable.
     * @param imageReader The ImageReader object for reading images.
     */
    private void makeRunAnimation(ImageReader imageReader){
        Renderable[] imageList = new Renderable[6];
        for (int i = 0; i < 6; i++) {
            imageList[i] = imageReader.readImage("assets/run_" + i + ".png", true);
        }
        runAnimation = new AnimationRenderable(imageList,0.1f);
    }
}


