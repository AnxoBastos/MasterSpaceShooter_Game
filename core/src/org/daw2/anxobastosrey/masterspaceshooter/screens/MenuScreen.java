package org.daw2.anxobastosrey.masterspaceshooter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.listeners.MenuClickListener;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public class MenuScreen implements Screen {

    public final MasterSpaceShooter game;
    private final ResourceManager rm;

    private final Stage stage = new Stage();
    private final Table root = new Table();

    //Common menu actors
    private final Label tittle;
    private final Label[] title = new Label[3];

    //Connect menu actors
    private final TextField email;
    private final TextField password;
    private final TextButton connectButton;
    private Label errorLabel;

    //Play menu actors
    private final TextButton play;
    private final TextButton settings;
    private final TextButton exit;

    public MenuScreen(MasterSpaceShooter game, ResourceManager rm){
        this.game = game;
        this.rm = rm;

        this.rm.skin.getFont("font").getData().setScale(0.40f);
        this.rm.skin.getFont("font-title").getData().setScale(0.40f);

        this.stage.setViewport(this.game.viewport);
        this.root.setBounds(0, 0, this.game.WORLD_WIDTH, this.game.WORLD_HEIGHT);

        this.tittle = new Label("MASTER  SPACE  SHOOTER", this.rm.skin, "title");
        this.title[0] = new Label("MASTER", this.rm.skin, "title");
        this.title[1] = new Label("SPACE", this.rm.skin, "title");
        this.title[2] = new Label("SHOOTER", this.rm.skin, "title");

        this.play = new TextButton("Play", this.rm.skin, "orange-small-toggle");
        this.settings = new TextButton("Settings", this.rm.skin, "orange-small-toggle");
        this.exit = new TextButton("Exit", this.rm.skin, "orange-small-toggle");

        this.email = new TextField("email", this.rm.skin, "login");
        this.password = new TextField("password", this.rm.skin, "password");
        this.connectButton = new TextButton("Connect", this.rm.skin, "orange-small-toggle");

        this.connectButton.addListener(new MenuClickListener(this.game, this.game.camera, this.game.viewport) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try{
                    this.game.api.connect(email.getText(), password.getText());
                }
                catch (Exception e){
                    errorLabel.setText("ERROR");
                    return super.touchDown(event, x, y, pointer, button);
                }
                setPlayMenu();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                System.exit(-1);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.play.addListener(new MenuClickListener(this.game, this.game.camera, this.game.viewport) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                this.game.setScreen(new GameScreen(this.game, this.camera, this.viewport));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.errorLabel = createLabel(null, Color.RED);
        this.stage.addActor(this.root);
        this.setConnectMenu();
    }

    private Label createLabel(final String text, final Color color) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = this.rm.skin.getFont("font");
        style.fontColor = color;

        return new Label(text, style);
    }

    public void setConnectMenu() {
        this.root.clear();
        //this.root.add(this.tittle).width(76).padTop(10).row();
        this.root.add(this.title[0]).width(76).padLeft(32).row();
        this.root.add(this.title[1]).width(76).padTop(-5).padLeft(37).row();
        this.root.add(this.title[2]).width(76).padTop(-5).padLeft(25).row();
        this.root.add(this.email).width(76).height(29).padTop(5).row();
        this.root.add(this.password).width(76).height(29).padTop(5).row();
        this.root.add(this.connectButton).size(60, 20).padTop(5).row();
        this.root.add(this.errorLabel).row();
    }

    public void setPlayMenu(){
        this.root.clear();
        this.root.add(this.title[0]).width(76).padTop(10).padLeft(31).row();
        this.root.add(this.title[1]).width(76).padTop(-5).padLeft(36).row();
        this.root.add(this.title[2]).width(76).padTop(-5).padLeft(25).row();
        this.root.add(this.play).width(70).padTop(10).row();
        this.root.add(this.settings).width(70).padTop(10).row();
        this.root.add(this.exit).size(60, 20).padTop(10).padBottom(10).row();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        this.game.batch.begin();
        this.game.background.renderBackground(delta, this.game.batch);
        this.game.batch.end();
        this.stage.draw();
        this.stage.act(delta);
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
    public void dispose() {
    }
}
