package org.daw2.anxobastosrey.masterspaceshooter.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public class HeavyEnemyShip extends Ship implements Enemy{

    public int bulletLvl = 1;

    public Vector2 directionVector;
    public float timeSinceLastDirectionChange = 0 ;
    public final float directionChangeFrequency = 0.75f;


    public HeavyEnemyShip(ResourceManager rm){
        super();
        this.rm = rm;

        this.laserDmg = 2;

        this.xCentre = MasterSpaceShooter.random.nextFloat() * (MasterSpaceShooter.WORLD_WIDTH - 10) + 5;
        this.yCentre = MasterSpaceShooter.WORLD_HEIGHT - 5;
        this.width = 10;
        this.height = 10;
        this.movementSpeed = 38;
        this.shield = 3;

        this.shipTextureRegion = this.rm.heavyEnemyShip;
        this.laserTextureRegion = this.rm.heavyEnemyShipLaser;

        this.laserWidth = 5f;
        this.laserHeight = 5;
        this.laserMovementSpeed = 35;
        this.timeBetweenShots = 0.9f;
        this.timeSinceLastShot = 0;

        this.boundingBox = new Rectangle(this.xCentre - this.width / 2, this.yCentre - this.height / 2, this.width, this.height);
        this.directionVector = new Vector2(0, -1);
    }

    private void randomizeDirectionVector() {
        double bearing = MasterSpaceShooter.random.nextDouble()*6.283185; //0 to 2*PI
        this.directionVector.x = (float)Math.sin(bearing);
        this.directionVector.y = (float)Math.cos(bearing);
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
        Laser[] firedLasers = new Laser[bulletLvl];
        if(bulletLvl == 1){
            firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width/2, this.boundingBox.y + this.boundingBox.height,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
        }
        if(bulletLvl == 2){
            firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.05f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[1] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.95f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
        }
        if(bulletLvl >= 3){
            firedLasers[0] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.05f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[1] = new Laser(this.boundingBox.x + this.boundingBox.width * 0.95f, this.boundingBox.y + this.boundingBox.height * 0.45f,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);
            firedLasers[2] = new Laser(this.boundingBox.x + this.boundingBox.width/2, this.boundingBox.y + this.boundingBox.height,
                    this.laserWidth, this.laserHeight,
                    this.laserMovementSpeed, this.laserTextureRegion, this.laserDmg);

        }

        timeSinceLastShot = 0;
        return firedLasers;
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void move(float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -this.boundingBox.x;
        downLimit = ((float) MasterSpaceShooter.WORLD_HEIGHT/4) * 3 - this.boundingBox.y;
        rightLimit = MasterSpaceShooter.WORLD_WIDTH - this.boundingBox.x - this.boundingBox.width;
        upLimit = MasterSpaceShooter.WORLD_HEIGHT - this.boundingBox.y - this.boundingBox.height;

        float xMove = this.directionVector.x * this.movementSpeed * deltaTime;
        float yMove = this.directionVector.y * this.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        this.translate(xMove, yMove);
    }
}
