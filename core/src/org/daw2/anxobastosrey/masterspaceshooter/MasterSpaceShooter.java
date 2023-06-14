package org.daw2.anxobastosrey.masterspaceshooter;

import static javax.swing.text.StyleConstants.Background;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.daw2.anxobastosrey.masterspaceshooter.api.Connection;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;
import org.daw2.anxobastosrey.masterspaceshooter.screens.MenuScreen;
import org.daw2.anxobastosrey.masterspaceshooter.screens.Background;

import java.util.Random;

public class MasterSpaceShooter extends Game {
	public MenuScreen menuScreen;

	public static ResourceManager rm;
	public static SpriteBatch batch;
	public static Background background;

	public static final float WORLD_WIDTH = 92;//72
	public static final float WORLD_HEIGHT = 148;//128

	public static final Camera camera = new OrthographicCamera();
	public static final Viewport viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

	public static final Random random = new Random();

	public static final Connection api = new Connection();

	@Override
	public void create() {
		this.rm = new ResourceManager();
		this.rm.music.loop(1);
		this.background = new Background(this.rm);
		this.batch = new SpriteBatch();
		this.menuScreen = new MenuScreen(this, this.rm);
		this.setScreen(menuScreen);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}
}
