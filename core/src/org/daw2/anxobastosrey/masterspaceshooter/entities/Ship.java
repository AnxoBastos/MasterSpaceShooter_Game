package org.daw2.anxobastosrey.masterspaceshooter.entities;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.LinkedList;

import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public abstract class Ship {

    public ResourceManager rm;

    public int laserDmg;

    public float xCentre;
    public float yCentre;
    public float width;
    public float height;
    public float movementSpeed;
    public int shield;

    public Rectangle boundingBox;

    public float laserWidth;
    public float laserHeight;
    public float laserMovementSpeed;
    public float timeBetweenShots;
    public float timeSinceLastShot;

    public LinkedList<Laser> laserList = new LinkedList<>();

    public TextureRegion shipTextureRegion;
    public TextureRegion shieldTextureRegion;
    public TextureRegion laserTextureRegion;

    public abstract void update(float deltaTime, SpriteBatch batch);

    public abstract Laser[] fireLasers();

    public boolean canFireLaser() {
        return (this.timeSinceLastShot - this.timeBetweenShots >= 0);
    }

    public boolean intersects(Rectangle otherRectangle) {
        return boundingBox.overlaps(otherRectangle);
    }

    public boolean hitAndCheckDestroyed() {
        if (this.shield > 0) {
            this.shield--;
            return false;
        }
        return true;
    }

    public void translate(float xChange, float yChange) {
        this.boundingBox.setPosition(this.boundingBox.x+xChange, this.boundingBox.y+yChange);
    }

    public void draw(Batch batch) {
        batch.draw(this.shipTextureRegion, this.boundingBox.x, this.boundingBox.y, this.boundingBox.width, this.boundingBox.height);
        if (this.shield > 0) {
            if (this.shield >= 5){
                this.shieldTextureRegion = this.rm.shieldLevelThree;
            }
            else if(this.shield >= 3){
                this.shieldTextureRegion = this.rm.shieldLevelTwo;
            }
            else this.shieldTextureRegion = this.rm.shieldLevelOne;
            batch.draw(this.shieldTextureRegion, this.boundingBox.x - 2.5f, this.boundingBox.y - 1.5f, this.boundingBox.width+5, this.boundingBox.height+5);
        }
    }
}
