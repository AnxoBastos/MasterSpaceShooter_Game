package org.daw2.anxobastosrey.masterspaceshooter.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager {
    public final AssetManager assetManager;

    //Atlas
    public final TextureAtlas atlas;

    //TextureRegions

    //Background
    public final TextureRegion[] background = new TextureRegion[4];;

    //Player
    public final TextureRegion playerShip;
    public final TextureRegion playerShipLaser;

    //Enemies
    public final TextureRegion normalEnemyShip;
    public final TextureRegion normalEnemyShipLaser;

    public final TextureRegion fastEnemyShip;
    public final TextureRegion fastlEnemyShipLaser;

    public final TextureRegion heavyEnemyShip;
    public final TextureRegion heavyEnemyShipLaser;

    public final TextureRegion bossEnemyShip;
    public final TextureRegion bossEnemyShipCores;


    //Habilities and stamps
    public final TextureRegion shieldLevelOne;
    public final TextureRegion shieldLevelTwo;
    public final TextureRegion shieldLevelThree;

    public final TextureRegion speedUpButton;
    public final TextureRegion speedStampOne;
    public final TextureRegion speedStampTwo;
    public final TextureRegion speedStampThree;

    public final TextureRegion bulletUpButton;
    public final TextureRegion bulletStampOne;
    public final TextureRegion bulletStampTwo;
    public final TextureRegion bulletStampThree;

    public final TextureRegion shieldUpButton;
    public final TextureRegion shieldStampOne;
    public final TextureRegion shieldStampTwo;
    public final TextureRegion shieldStampThree;

    //Explosion and particles
    public final TextureRegion explosionParticle;

    //Fonts
    public final BitmapFont font = generateFont();

    //Sounds
    public final Sound music = Gdx.audio.newSound(Gdx.files.internal("game/spaceship.wav"));

    //MenuSkin
    public final Skin skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

    public ResourceManager(){
        //Carga de las texturas, sonidos y fuentes
        this.assetManager = new AssetManager();
        this.assetManager.load("game/MasterSpaceShooter.atlas", TextureAtlas.class);
        this.assetManager.finishLoading();
        this.atlas = this.assetManager.get("game/MasterSpaceShooter.atlas", TextureAtlas.class);

        //Texture regions

        //Background
        this.background[0] = this.atlas.findRegion("starscape00");
        this.background[1] = this.atlas.findRegion("starscape01");
        this.background[2] = this.atlas.findRegion("starscape02");
        this.background[3] = this.atlas.findRegion("starscape03");

        //Player
        this.playerShip = this.atlas.findRegion("playerShip1");
        this.playerShipLaser = this.atlas.findRegion("laserBlue04");

        //Enemies
        this.normalEnemyShip = this.atlas.findRegion("enemyShip1");
        this.normalEnemyShipLaser = this.atlas.findRegion("laserRed04");

        this.fastEnemyShip = this.atlas.findRegion("enemyShip2");
        this.fastlEnemyShipLaser = this.atlas.findRegion("laserRed05");

        this.heavyEnemyShip = this.atlas.findRegion("enemyShip3");
        this.heavyEnemyShipLaser = this.atlas.findRegion("laserRed08");

        this.bossEnemyShip = this.atlas.findRegion("enemyShip4");
        this.bossEnemyShipCores = this.atlas.findRegion("ufoRed");

        //Habilities and stamps
        this.shieldLevelOne = this.atlas.findRegion("shield1");
        this.shieldLevelTwo = this.atlas.findRegion("shield2");
        this.shieldLevelThree = this.atlas.findRegion("shield3");

        this.bulletUpButton = this.atlas.findRegion("powerupBlue_things");
        this.bulletStampOne = this.atlas.findRegion("things_bronze");
        this.bulletStampTwo = this.atlas.findRegion("things_silver");
        this.bulletStampThree = this.atlas.findRegion("things_gold");

        this.speedUpButton = this.atlas.findRegion("powerupBlue_bolt");
        this.speedStampOne = this.atlas.findRegion("bolt_bronze");
        this.speedStampTwo = this.atlas.findRegion("bolt_silver");
        this.speedStampThree = this.atlas.findRegion("bolt_gold");

        this.shieldUpButton = this.atlas.findRegion("powerupBlue_shield");
        this.shieldStampOne = this.atlas.findRegion("shield_bronze");
        this.shieldStampTwo = this.atlas.findRegion("shield_silver");
        this.shieldStampThree = this.atlas.findRegion("shield_gold");

        //Explosion and particles
        this.explosionParticle = this.atlas.findRegion("star3");

    }

    private static BitmapFont generateFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("game/EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        return fontGenerator.generateFont(fontParameter);
    }
}
