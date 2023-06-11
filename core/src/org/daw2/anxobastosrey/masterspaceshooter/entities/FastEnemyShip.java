package org.daw2.anxobastosrey.masterspaceshooter.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;

import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public class FastEnemyShip extends Ship implements Enemy{

    public Vector2 directionVector;
    public float timeSinceLastDirectionChange = 0 ;
    public final float directionChangeFrequency = 0.75f;


    public FastEnemyShip(ResourceManager rm){
        super();
        this.rm = rm;

        this.laserDmg = 1;

        this.xCentre = MasterSpaceShooter.random.nextFloat() * (MasterSpaceShooter.WORLD_WIDTH - 10) + 5;
        this.yCentre = MasterSpaceShooter.WORLD_HEIGHT - 5;
        this.width = 10;
        this.height = 10;
        this.movementSpeed = 60;
        this.shield = 500;

        this.shipTextureRegion = this.rm.fastEnemyShip;
        this.laserTextureRegion = this.rm.fastlEnemyShipLaser;

        this.laserWidth = 1.5f;
        this.laserHeight = 5;
        this.laserMovementSpeed = 65;
        this.timeBetweenShots = 0.5f;
        this.timeSinceLastShot = 0;

        this.boundingBox = new Rectangle(this.xCentre - this.width / 2, this.yCentre - this.height / 2, this.width, this.height);
        this.directionVector = new Vector2(0, -1);
    }

    private void randomizeDirectionVector() {
        double bearing = MasterSpaceShooter.random.nextDouble()*6.283185; //0 to 2*PI
        this.directionVector.x = (float)Math.sin(bearing);
    }

    public void update(float deltaTime, SpriteBatch batch) {
        this.timeSinceLastShot += deltaTime;
        this.timeSinceLastDirectionChange += deltaTime;
        if (this.timeSinceLastDirectionChange > this.directionChangeFrequency) {
            randomizeDirectionVector();
            this.timeSinceLastDirectionChange -= this.directionChangeFrequency;
        }
        this.move(deltaTime);
    }

    public boolean intersects(Rectangle otherRectangle) {
        return super.intersects(otherRectangle);
    }

    public boolean canFireLaser() {
        return super.canFireLaser();
    }

    public void translate(float xChange, float yChange) {
        super.translate(xChange, yChange);
    }

    public Laser[] fireLasers() {
        Laser[] firedLasers = new Laser[1];
        firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width/2, this.boundingBox.y - this.laserHeight,
                this.laserWidth, this.laserHeight,
                this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);

        this.timeSinceLastShot = 0;

        return firedLasers;
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void move(float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -this.boundingBox.x;
        rightLimit = MasterSpaceShooter.WORLD_WIDTH - this.boundingBox.x - this.boundingBox.width;

        float xMove = this.directionVector.x * this.movementSpeed * deltaTime;
        float yMove = this.directionVector.y * this.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        this.translate(xMove, yMove);
    }
}
