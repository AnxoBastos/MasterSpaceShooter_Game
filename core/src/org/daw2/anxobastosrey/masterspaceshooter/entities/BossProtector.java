package org.daw2.anxobastosrey.masterspaceshooter.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public class BossProtector extends Ship{

    private final BossShip boss;

    public BossProtector(ResourceManager rm, BossShip boss){
        super();

        this.rm = rm;
        this.boss = boss;

        float xMultiplier = MasterSpaceShooter.random.nextFloat();
        float yMultiplier = MasterSpaceShooter.random.nextFloat();
        if (xMultiplier < 0.1f || xMultiplier > 0.9f) xMultiplier = 0.45f;
        if (yMultiplier < 0.4f || yMultiplier > 0.8f) yMultiplier = 0.5f;

        this.xCentre = xMultiplier * MasterSpaceShooter.WORLD_WIDTH;
        this.yCentre = yMultiplier * MasterSpaceShooter.WORLD_HEIGHT;
        this.width = 10;
        this.height = 10;

        this.shield = 3;

        this.shipTextureRegion = this.rm.bossEnemyShipCores;

        this.boundingBox = new Rectangle(this.xCentre - this.width / 2, this.yCentre - this.height / 2, this.width, this.height);
    }

    @Override
    public void update(float deltaTime, SpriteBatch batch) {

    }

    @Override
    public Laser[] fireLasers() {
        return new Laser[0];
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(this.shipTextureRegion, this.boundingBox.x, this.boundingBox.y, this.boundingBox.width, this.boundingBox.height);
    }

    @Override
    public boolean hitAndCheckDestroyed() {
        if (this.shield > 0) {
            this.shield--;
            return false;
        }
        this.boss.protectorsNumber--;
        return true;
    }
}
