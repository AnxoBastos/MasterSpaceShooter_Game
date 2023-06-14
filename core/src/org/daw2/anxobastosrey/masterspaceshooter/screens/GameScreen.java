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

public class GameScreen implements Screen {

    //Game
    private final MasterSpaceShooter game;

    //Background
    private final Background background;

    //world parameters
    public static final int[] PU_PRICES = {0, 2000, 6000, 10000};
    public static final int MAX_LVL = 3;

    //game objects
    private final PlayerShip playerShip;

    //Hud
    private final Hud hud;

    //Player input manager
    private final InputManager inputManager;

    //Level manager
    private final LevelManager levelManager;

    private boolean scoreStored = false;

    public GameScreen(MasterSpaceShooter game) {
        Gdx.input.setInputProcessor(null);
        this.game = game;

        //set up game objects
        this.playerShip = new PlayerShip(this.game.rm);

        this.levelManager = new LevelManager(this.game, this.playerShip, this.game.rm, this.game.batch);

        //Hud
        this.hud = new Hud(this.game.rm);

        //Input manager
        this.inputManager = new InputManager(this.game, this.playerShip);

        //Background
        this.background = new Background(this.game.rm);

    }

    @Override
    public void render(float deltaTime) {
        this.game.batch.begin();

        this.background.renderBackground(deltaTime, this.game.batch);
        this.inputManager.detectInput(deltaTime, this.game.viewport, this.hud);
        this.levelManager.update(deltaTime);
        this.levelManager.renderLasers(deltaTime);
        this.levelManager.detectCollisions();
        this.levelManager.updateAndRenderExplosions(deltaTime);
        this.hud.updateAndRenderHUD(this.game.batch, this.levelManager.score, this.playerShip);
        if (this.playerShip.lives <= 0 && this.scoreStored == false){
            this.game.api.store(levelManager.score);
            this.scoreStored = true;
        }

        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.game.viewport.update(width, height, true);
        this.game.batch.setProjectionMatrix(this.game.camera.combined);
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
