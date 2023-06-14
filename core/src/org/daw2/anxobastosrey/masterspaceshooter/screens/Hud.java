package org.daw2.anxobastosrey.masterspaceshooter.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import java.awt.Rectangle;
import java.util.Locale;
import org.daw2.anxobastosrey.masterspaceshooter.screens.GameScreen;
import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.entities.PlayerShip;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public class Hud {

    private ResourceManager rm;
    private final BitmapFont font;

    public final float hudVerticalMargin;
    public final float hudLeftX;
    public final float hudRightX;
    public final float hudCentreX;
    public final float hudRow1Y;
    public final float hudRow2Y;
    public final float hudSectionWidth;

    public final Rectangle pUSpeedButton;
    public final Rectangle pUBulletsButton;
    public final Rectangle pUShieldButton;
    public final int pUButtonMargin = 3;

    //LvlUp stamps
    public final float lvlUpStampsY = MasterSpaceShooter.WORLD_HEIGHT - 22;
    public final float lvlUpStampsWidth = 4;
    public final float lvlUpStampsHeight = 4;
    public final float lvlStampMargin = 2;

    public Hud(ResourceManager rm) {
        this.rm = rm;
        this.font = this.rm.font;

        //scale the font to fit world
        this.font.getData().setScale(0.08f);

        //calculate hud margins, etc.
        this.hudVerticalMargin = this.font.getCapHeight() / 2;
        this.hudLeftX = this.hudVerticalMargin;
        this.hudRightX = MasterSpaceShooter.WORLD_WIDTH * 2 / 3 - this.hudLeftX;
        this.hudCentreX = MasterSpaceShooter.WORLD_WIDTH / 3;
        this.hudRow1Y = MasterSpaceShooter.WORLD_HEIGHT - this.hudVerticalMargin;
        this.hudRow2Y = hudRow1Y - this.hudVerticalMargin - this.font.getCapHeight();
        this.hudSectionWidth = MasterSpaceShooter.WORLD_WIDTH / 3;

        //buttons
        this.pUBulletsButton = new Rectangle(pUButtonMargin, 2, this.rm.bulletUpButton.getRegionWidth()/4, this.rm.bulletUpButton.getRegionHeight()/4);
        this.pUSpeedButton = new Rectangle(this.rm.bulletUpButton.getRegionWidth()/4 + (pUButtonMargin * 2), 2, this.rm.speedUpButton.getRegionWidth()/4 , this.rm.speedUpButton.getRegionHeight()/4);
        this.pUShieldButton = new Rectangle(this.rm.bulletUpButton.getRegionWidth()/4 + this.rm.speedUpButton.getRegionWidth()/4 + (pUButtonMargin * 3),
                2, this.rm.shieldUpButton.getRegionWidth()/4, this.rm.shieldUpButton.getRegionHeight()/4);
    }

    public void updateAndRenderHUD(SpriteBatch batch, int score, PlayerShip playerShip) {
        this.font.setColor(Color.WHITE);
        if(playerShip.lives <= 0){
            this.font.setColor(Color.RED);
            this.font.draw(batch, "GAME OVER", MasterSpaceShooter.WORLD_WIDTH/2 - 12, MasterSpaceShooter.WORLD_HEIGHT/2, MasterSpaceShooter.WORLD_WIDTH/2, Align.left, false);
        }
        //render top row labels
        this.font.draw(batch, "Score", this.hudLeftX, this.hudRow1Y, this.hudSectionWidth, Align.left, false);
        this.font.draw(batch, "Shield", this.hudCentreX, this.hudRow1Y, this.hudSectionWidth, Align.center, false);
        this.font.draw(batch, "Lives", this.hudRightX, this.hudRow1Y, this.hudSectionWidth, Align.right, false);
        //render second row values
        this.font.draw(batch, String.format(Locale.getDefault(), "%06d", score), this.hudLeftX, this.hudRow2Y, this.hudSectionWidth, Align.left, false);
        this.font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), this.hudCentreX, this.hudRow2Y, this.hudSectionWidth, Align.center, false);
        this.font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), this.hudRightX, this.hudRow2Y, this.hudSectionWidth, Align.right, false);

        //powerups states
        if (playerShip.bulletLvl == 1){
            batch.draw(this.rm.bulletStampOne, lvlStampMargin, lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }
        else if (playerShip.bulletLvl == 2) {
            batch.draw(this.rm.bulletStampTwo, lvlStampMargin, lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }
        else if (playerShip.bulletLvl == 3){
            batch.draw(this.rm.bulletStampThree, lvlStampMargin, lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }

        if (playerShip.speedLvl == 1){
            batch.draw(this.rm.speedStampOne, (lvlStampMargin * 2) + lvlUpStampsWidth, lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }
        else if (playerShip.speedLvl == 2) {
            batch.draw(this.rm.speedStampTwo, (lvlStampMargin * 2) + lvlUpStampsWidth, lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }
        else if (playerShip.speedLvl == 3){
            batch.draw(this.rm.speedStampThree, (lvlStampMargin * 2) + lvlUpStampsWidth, lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }

        if (playerShip.shieldLvl == 1){
            batch.draw(this.rm.shieldStampOne, (lvlStampMargin * 3) + (lvlUpStampsWidth * 2), lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }
        else if (playerShip.shieldLvl == 2) {
            batch.draw(this.rm.shieldStampTwo, (lvlStampMargin * 3) + (lvlUpStampsWidth * 2), lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }
        else if (playerShip.shieldLvl == 3){
            batch.draw(this.rm.shieldStampThree, (lvlStampMargin * 3) + (lvlUpStampsWidth * 2), lvlUpStampsY, lvlUpStampsWidth, lvlUpStampsHeight);
        }

        //buttons
        if ( (playerShip.bulletLvl + 1) <= GameScreen.MAX_LVL && playerShip.points <= GameScreen.PU_PRICES[playerShip.bulletLvl + 1]) batch.setColor(Color.GRAY);
        batch.draw(rm.bulletUpButton, pUBulletsButton.x, pUBulletsButton.y, pUBulletsButton.width, pUBulletsButton.height);
        batch.setColor(Color.WHITE);
        if ( (playerShip.bulletLvl + 1) <= GameScreen.MAX_LVL && playerShip.points <= GameScreen.PU_PRICES[playerShip.speedLvl + 1]) batch.setColor(Color.GRAY);
        batch.draw(rm.speedUpButton, pUSpeedButton.x, pUSpeedButton.y, pUSpeedButton.width, pUSpeedButton.height);
        batch.setColor(Color.WHITE);
        if ( (playerShip.bulletLvl + 1) <= GameScreen.MAX_LVL && playerShip.points <= GameScreen.PU_PRICES[playerShip.speedLvl + 1]) batch.setColor(Color.GRAY);
        batch.draw(rm.shieldUpButton, pUShieldButton.x, pUShieldButton.y, pUShieldButton.width, pUShieldButton.height);
        batch.setColor(Color.WHITE);

        //points
        this.font.draw(batch, "Points", 35, 8.5f, this.hudSectionWidth, Align.right, false);
        this.font.draw(batch, String.format(Locale.getDefault(), "%06d", playerShip.points), 70, 8.5f, this.hudSectionWidth, Align.left, false);
    }
}
