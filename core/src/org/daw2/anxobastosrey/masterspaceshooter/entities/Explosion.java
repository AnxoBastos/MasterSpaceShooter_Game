package org.daw2.anxobastosrey.masterspaceshooter.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;

public class Explosion {

    private float XS[];
    private float YS[];
    private float DX[];
    private float DY[];
    public float timer;
    private final int particlecount = 50;

    public Explosion(float x, float y) {
        this.XS = new float[this.particlecount];
        this.YS = new float[this.particlecount];
        this.DX = new float[this.particlecount];
        this.DY = new float[this.particlecount];
        this.timer = 2;

        for (int i = 0; i < this.particlecount; i++){
            this.XS[i] = x;
            this.YS[i] = y;
            this.DX[i] = 0.5f - MasterSpaceShooter.random.nextFloat();
            this.DY[i] = 0.5f - MasterSpaceShooter.random.nextFloat();
        }
    }

    public void update(float deltaTime){
        this.timer = this.timer - deltaTime;
        for (int i = 0; i < this.XS.length; i++){
            this.XS[i] += this.DX[i] * deltaTime * 50;
            this.YS[i] += this.DY[i] * deltaTime * 50;
        }
    }

    public void draw(SpriteBatch batch, TextureRegion explosionParticle){
        for (int i = 0; i < this.XS.length; i++){
            batch.draw(explosionParticle, this.XS[i], this.YS[i], 2, 2);
        }
    }
}
