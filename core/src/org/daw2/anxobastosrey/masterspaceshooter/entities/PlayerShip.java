package org.daw2.anxobastosrey.masterspaceshooter.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.LinkedList;

import org.daw2.anxobastosrey.masterspaceshooter.screens.GameScreen;
import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public class PlayerShip extends Ship {

    //Stats
    public int lives = 2;
    public final int defaultShield = 2;

    //Power up levels
    public int shieldLvl = 0;
    public int speedLvl = 0;
    public int bulletLvl = 0;

    //Power up points
    public int points = 1000;

    //Shield
    public final float timeRegenerateShield = 15;
    public float timeSinceLastShield = 0;

    public PlayerShip(ResourceManager rm) {
        super();
        this.rm = rm;

        this.laserDmg = 1;

        this.xCentre = MasterSpaceShooter.WORLD_WIDTH / 2;
        this.yCentre = MasterSpaceShooter.WORLD_HEIGHT / 4;
        this.width = 12;
        this.height = 10;
        this.movementSpeed = 35;
        this.shield = defaultShield;

        this.shipTextureRegion = this.rm.playerShip;
        this.laserTextureRegion = this.rm.playerShipLaser;

        this.laserWidth = 1f;
        this.laserHeight = 4;
        this.laserMovementSpeed = 45;
        this.timeBetweenShots = 0.5f;
        this.timeSinceLastShot = 0;

        this.laserList = new LinkedList<>();

        this.boundingBox = new Rectangle(this.xCentre - this.width / 2, this.yCentre - this.height / 2, this.width, this.height);
    }

    public void update(float deltaTime, SpriteBatch batch) {
        this.timeSinceLastShot += deltaTime;
        this.timeSinceLastShield += deltaTime;
        if (this.shield < this.defaultShield + (2 * this.shieldLvl) && this.timeSinceLastShield > timeRegenerateShield) {
            this.shield = this.defaultShield + (2 * this.shieldLvl);
            this.timeSinceLastShield -= timeRegenerateShield;
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public Laser[] fireLasers() {
        Laser[] firedLasers = new Laser[bulletLvl + 1];
        if (bulletLvl == 0) {
            firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width / 2, this.boundingBox.y + this.boundingBox.height,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
        }
        if (bulletLvl == 1) {
            firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.07f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[1] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.93f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
        }
        if (bulletLvl == 2) {
            firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.07f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[1] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.93f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[2] = new Laser(this.boundingBox.x + this.boundingBox.width / 2, this.boundingBox.y + this.boundingBox.height,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);

        }
        if (bulletLvl == 3) {
            firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.07f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[1] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.93f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[2] = new Laser(this.boundingBox.x + this.boundingBox.width / 4, this.boundingBox.y + this.boundingBox.height * 0.65f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[3] = new Laser(this.boundingBox.x + this.boundingBox.width / 4 * 3, this.boundingBox.y + this.boundingBox.height * 0.65f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);

        }
        this.timeSinceLastShot = 0;
        return firedLasers;
    }

    public void translate(float xChange, float yChange) {
        super.translate(xChange, yChange);
    }

    public boolean canFireLaser() {
        return super.canFireLaser();
    }

    public boolean intersects(Rectangle otherRectangle) {
        return this.boundingBox.overlaps(otherRectangle);
    }

    public boolean hitAndCheckDestroyed(int dmgLevel) {
        if (this.shield > 0) {
            if (dmgLevel == 3) return true;
            else if (dmgLevel == 2) this.shield = 0;
            else this.shield--;
            return false;
        }
        return true;
    }
}

