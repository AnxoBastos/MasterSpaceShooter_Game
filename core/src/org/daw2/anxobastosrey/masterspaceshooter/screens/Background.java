package org.daw2.anxobastosrey.masterspaceshooter.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.managers.ResourceManager;

public class Background {
    public final TextureRegion[] background;
    public final float backgroundHeight;
    public float[] backgroundOffsets = {0, 0, 0, 0};
    public final float backgroundMaxScrollingSpeed;

    public Background(ResourceManager rm) {
        this.background = rm.background;
        this.backgroundHeight = MasterSpaceShooter.WORLD_HEIGHT * 2;
        this.backgroundMaxScrollingSpeed = (float) (MasterSpaceShooter.WORLD_HEIGHT) / 4;
    }

    public void renderBackground(float deltaTime, SpriteBatch batch) {

        //update position of background images
        this.backgroundOffsets[0] += deltaTime * this.backgroundMaxScrollingSpeed / 8;
        this.backgroundOffsets[1] += deltaTime * this.backgroundMaxScrollingSpeed / 4;
        this.backgroundOffsets[2] += deltaTime * this.backgroundMaxScrollingSpeed / 2;
        this.backgroundOffsets[3] += deltaTime * this.backgroundMaxScrollingSpeed;

        //draw each background layer
        for (int layer = 0; layer < this.backgroundOffsets.length; layer++) {
            if (this.backgroundOffsets[layer] > MasterSpaceShooter.WORLD_HEIGHT) {
                this.backgroundOffsets[layer] = 0;
            }
            batch.draw(this.background[layer], 0, -this.backgroundOffsets[layer],
                    MasterSpaceShooter.WORLD_WIDTH, this.backgroundHeight);
        }
    }
}
