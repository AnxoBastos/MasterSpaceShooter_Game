package org.daw2.anxobastosrey.masterspaceshooter.entities;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Laser {

    //position and dimensions
    public Rectangle boundingBox;

    //laser physical characteristics
    public float xCentre;
    public float yBottom;
    public float width;
    public float height;
    public float movementSpeed; //world units per second
    public int bulletDmg;

    //graphics
    public TextureRegion laserTexture;

    public Laser(float xCentre, float yBottom, float width, float height, float movementSpeed, TextureRegion laserTexture, int bulletDmg) {
        this.xCentre = xCentre;
        this.yBottom = yBottom;
        this.width = width;
        this.height =  height;
        this.boundingBox = new Rectangle(this.xCentre - this.width / 2, this.yBottom, this.width, this.height);

        this.movementSpeed = movementSpeed;
        this.laserTexture = laserTexture;
        this.bulletDmg = bulletDmg;
    }

    public void draw(Batch batch) {
        batch.draw(this.laserTexture, this.boundingBox.x, this.boundingBox.y, this.boundingBox.width, this.boundingBox.height);
    }
}
