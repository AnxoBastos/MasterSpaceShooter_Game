package org.daw2.anxobastosrey.masterspaceshooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.entities.PlayerShip;
import org.daw2.anxobastosrey.masterspaceshooter.managers.InputManager;
import org.daw2.anxobastosrey.masterspaceshooter.managers.LevelManager;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;
import org.daw2.anxobastosrey.masterspaceshooter.screens.Background;
import org.daw2.anxobastosrey.masterspaceshooter.screens.Hud;

public class GameScreen implements Screen {

    //Game
    private final MasterSpaceShooter game;

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;

    //Background
    private Background background;

    //world parameters
    public static final int[] PU_PRICES = {0, 2000, 6000, 10000};
    public static final int MAX_LVL = 3;

    //game objects
    private PlayerShip playerShip;

    //Hud
    private Hud hud;

    //Resource manager (font, sounds, textures)
    private ResourceManager rm;

    //Player input manager
    private InputManager inputManager;

    //Level manager
    private LevelManager levelManager;

    private boolean scoreStored = false;

    public GameScreen(MasterSpaceShooter game, Camera camera, Viewport viewport) {
        Gdx.input.setInputProcessor(null);
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;
        this.rm = new ResourceManager();

        //set up game objects
        playerShip = new PlayerShip(rm);

        batch = new SpriteBatch();

        levelManager = new LevelManager(playerShip, rm, batch);

        //Hud
        hud = new Hud(rm);

        //Input manager
        inputManager = new InputManager(this.game, playerShip, hud);

        //Background
        background = new Background(rm);

    }

    @Override
    public void render(float deltaTime) {
        this.batch.begin();

        this.background.renderBackground(deltaTime, this.batch);
        this.inputManager.detectInput(deltaTime, viewport);
        this.levelManager.update(deltaTime);
        this.levelManager.renderLasers(deltaTime);
        this.levelManager.detectCollisions();
        this.levelManager.updateAndRenderExplosions(deltaTime);
        this.hud.updateAndRenderHUD(batch, levelManager.score, playerShip);
        if (playerShip.lives <= 0 && this.scoreStored == false){
            this.game.api.store(levelManager.score);
            this.scoreStored = true;
        }

        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
    }
}
