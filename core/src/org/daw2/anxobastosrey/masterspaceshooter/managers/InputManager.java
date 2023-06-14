package org.daw2.anxobastosrey.masterspaceshooter.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.screens.GameScreen;
import org.daw2.anxobastosrey.masterspaceshooter.entities.PlayerShip;
import org.daw2.anxobastosrey.masterspaceshooter.screens.Hud;

public class InputManager {

    private static final float TOUCH_MOVEMENT_THRESHOLD = 5f;

    private final MasterSpaceShooter game;
    private final PlayerShip playerShip;
    private float gameOverTimer = 0;

    public InputManager(MasterSpaceShooter game, PlayerShip player){
        this.game = game;
        this.playerShip = player;
    }

    public void detectInput(float deltaTime, Viewport viewport, Hud hud) {
        //keyboard input

        //strategy: determine the max distance the ship can move
        //check each key that matters and move accordingly

        float leftLimit = -this.playerShip.boundingBox.x;
        float downLimit = (float) (-this.playerShip.boundingBox.y + hud.pUShieldButton.getHeight() + 4);
        float rightLimit = this.game.WORLD_WIDTH - this.playerShip.boundingBox.x - this.playerShip.boundingBox.width;
        float upLimit = (float) this.game.WORLD_HEIGHT * 3/4 - this.playerShip.boundingBox.y - this.playerShip.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
            this.playerShip.translate(Math.min(this.playerShip.movementSpeed * deltaTime, rightLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            this.playerShip.translate(0f, Math.min(this.playerShip.movementSpeed * deltaTime, upLimit));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            this.playerShip.translate(Math.max(-this.playerShip.movementSpeed * deltaTime, leftLimit), 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            this.playerShip.translate(0f, Math.max(-this.playerShip.movementSpeed * deltaTime, downLimit));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && (this.playerShip.bulletLvl + 1) <= GameScreen.MAX_LVL && this.playerShip.points >= GameScreen.PU_PRICES[this.playerShip.bulletLvl + 1]){
            this.playerShip.bulletLvl++;
            this.playerShip.points -= GameScreen.PU_PRICES[playerShip.bulletLvl];
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && (this.playerShip.speedLvl + 1) <= GameScreen.MAX_LVL && this.playerShip.points >= GameScreen.PU_PRICES[this.playerShip.speedLvl + 1]){
            this.playerShip.speedLvl++;
            this.playerShip.movementSpeed += 10;
            this.playerShip.points -= GameScreen.PU_PRICES[this.playerShip.speedLvl];
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && (this.playerShip.shieldLvl + 1) <= GameScreen.MAX_LVL && this.playerShip.points >= GameScreen.PU_PRICES[this.playerShip.shieldLvl + 1]){
            this.playerShip.shieldLvl++;
            this.playerShip.shield = this.playerShip.defaultShield + (2 * this.playerShip.shieldLvl);
            this.playerShip.points -= GameScreen.PU_PRICES[this.playerShip.shieldLvl];
        }

        //touch input (also mouse)
        if (Gdx.input.isTouched()) {
            //get the screen position of the touch
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //convert to world position
            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            //calculate the x and y differences
            Vector2 playerShipCentre = new Vector2(
                    this.playerShip.boundingBox.x + this.playerShip.boundingBox.width / 2,
                    this.playerShip.boundingBox.y + this.playerShip.boundingBox.height / 2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD) {
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                //scale to the maximum speed of the ship
                float xMove = xTouchDifference / touchDistance * this.playerShip.movementSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * this.playerShip.movementSpeed * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                this.playerShip.translate(xMove, yMove);
            }
        }

        if(this.playerShip.lives <= 0){
            this.gameOverTimer += deltaTime;
            if((Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) && this.gameOverTimer >= 2){
                this.game.rm.font.setColor(Color.WHITE);
                this.game.setScreen(this.game.menuScreen);
            }
        }
    }
}
