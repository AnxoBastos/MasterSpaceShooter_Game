package org.daw2.anxobastosrey.masterspaceshooter.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import org.daw2.anxobastosrey.masterspaceshooter.MasterSpaceShooter;
import org.daw2.anxobastosrey.masterspaceshooter.entities.BossShip;
import org.daw2.anxobastosrey.masterspaceshooter.entities.FastEnemyShip;
import org.daw2.anxobastosrey.masterspaceshooter.entities.HeavyEnemyShip;
import org.daw2.anxobastosrey.masterspaceshooter.entities.Explosion;
import org.daw2.anxobastosrey.masterspaceshooter.entities.Laser;
import org.daw2.anxobastosrey.masterspaceshooter.entities.NormalEnemyShip;
import org.daw2.anxobastosrey.masterspaceshooter.entities.PlayerShip;
import org.daw2.anxobastosrey.masterspaceshooter.entities.Ship;

public class LevelManager {

    private final MasterSpaceShooter game;

    private final PlayerShip playerShip;
    private final LinkedList<Ship> enemyShipList = new LinkedList<>();
    private final ArrayList<Explosion> explosionList = new ArrayList<>();

    public int score = 0;

    public float timeSinceLastLevel = 0;
    public final float timeBetweenLevels = 7;

    public int enemiesPerLevel = 3;
    public float enemyMultiplier = 1;
    public final int maxBosses = 1;

    public int scoreToUpgradeEnemies = 2500;

    private final LinkedList<Laser> playerLaserList = new LinkedList<>();
    private final LinkedList<Laser> enemyLaserList = new LinkedList<>();

    public LevelManager(MasterSpaceShooter game, PlayerShip playerShip, ResourceManager rm, SpriteBatch batch){
        this.game = game;
        this.playerShip = playerShip;
        this.generateLevel();
    }

    public void update(float deltaTime){
        if (playerShip.lives > 0){
            this.playerShip.update(deltaTime, this.game.batch);
            this.playerShip.draw(this.game.batch);
            this.timeSinceLastLevel += deltaTime;
            if(this.timeSinceLastLevel > this.timeBetweenLevels){
                this.generateLevel();
                this.timeSinceLastLevel -= this.timeBetweenLevels;
            }
            ListIterator<Ship> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Ship enemyShip = enemyShipListIterator.next();
                enemyShip.update(deltaTime, this.game.batch);
                enemyShip.draw(this.game.batch);
            }
        }
    }

    public void detectCollisions() {
        ListIterator<Ship> enemyShipListIterator = this.enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            Ship enemyShip = enemyShipListIterator.next();
            if(Intersector.overlaps(enemyShip.boundingBox, playerShip.boundingBox)){
                enemyShipListIterator.remove();
                this.explosionList.add(new Explosion(this.playerShip.boundingBox.getX(), this.playerShip.boundingBox.getY()));
                this.score += 100;
                this.playerShip.points += 50;
                this.playerShip.lives--;
                this.playerShip.shield = this.playerShip.defaultShield + (2 * this.playerShip.shieldLvl);
                break;
            }
        }

        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Laser> laserListIterator = this.playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            enemyShipListIterator = this.enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Ship enemyShip = enemyShipListIterator.next();
                if (enemyShip.intersects(laser.boundingBox)) {
                    //contact with enemy ship
                    if (enemyShip.hitAndCheckDestroyed()) {
                        enemyShipListIterator.remove();
                        this.explosionList.add(new Explosion(enemyShip.boundingBox.getX(), enemyShip.boundingBox.getY()));
                        this.score += 100;
                        this.playerShip.points += 50;
                    }
                    laserListIterator.remove();
                    break;
                }
            }
        }

        //for each enemy laser, check whether it intersects the player ship
        laserListIterator = this.enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (this.playerShip.intersects(laser.boundingBox)) {
                //contact with player ship
                if (this.playerShip.hitAndCheckDestroyed(laser.bulletDmg)) {
                    this.explosionList.add(new Explosion(this.playerShip.boundingBox.getX(), this.playerShip.boundingBox.getY()));
                    this.playerShip.lives--;
                    this.playerShip.shield = this.playerShip.defaultShield + (2 * this.playerShip.shieldLvl);
                }
                laserListIterator.remove();
            }
        }
    }

    public void renderLasers(float deltaTime) {
        //create new lasers
        //player lasers
        if (this.playerShip.canFireLaser()) {
            Laser[] lasers = this.playerShip.fireLasers();
            this.playerLaserList.addAll(Arrays.asList(lasers));
        }
        //enemy lasers
        ListIterator<Ship> enemyShipListIterator = this.enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            Ship enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLasers();
                this.enemyLaserList.addAll(Arrays.asList(lasers));
            }
            if (enemyShip.boundingBox.y + enemyShip.boundingBox.height < 0) {
                enemyShipListIterator.remove();
            }
        }

        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = this.playerLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(this.game.batch);
            laser.boundingBox.y += laser.movementSpeed * deltaTime;
            if (laser.boundingBox.y > MasterSpaceShooter.WORLD_HEIGHT) {
                iterator.remove();
            }
        }
        iterator = this.enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(this.game.batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;
            if (laser.boundingBox.y + laser.boundingBox.height < 0) {
                iterator.remove();
            }
        }
    }

    public void updateAndRenderExplosions(float deltaTime) {
        for(int i = this.explosionList.size() - 1; i >= 0; i--){
            this.explosionList.get(i).update(deltaTime);
            if(explosionList.get(i).timer <= 0){
                this.explosionList.remove(i);
            }
            else{
                this.explosionList.get(i).draw(this.game.batch, this.game.rm.explosionParticle);
            }
        }
    }

    private void generateLevel(){
        if(this.scoreToUpgradeEnemies < this.score){
            this.enemyMultiplier += 0.1;
            this.scoreToUpgradeEnemies = this.scoreToUpgradeEnemies * 2;
        }
        int enemyCounter = Math.round(this.enemiesPerLevel * this.enemyMultiplier);
        int[] enemies = new int[4];
        for (int i = 0; i < enemies.length; i++){
            enemies[i] = MasterSpaceShooter.random.nextInt(enemyCounter) + 1;
            enemyCounter -= enemies[i];
            if(enemyCounter == 0) break;
        }
        if( (MasterSpaceShooter.random.nextInt(100) + 1) > 97) enemies[3] = this.maxBosses;
        for (int i = 0; i < enemies.length; i++){
            if(i == 0){
                for(int j = 0; j < enemies[i]; j++){
                    NormalEnemyShip ship = new NormalEnemyShip(this.game.rm);
                    ship.shield = Math.round(ship.shield * this.enemyMultiplier);
                    this.enemyShipList.add(ship);
                }
            }
            if(i == 1){
                for(int j = 0; j < enemies[i]; j++){
                    FastEnemyShip ship = new FastEnemyShip(this.game.rm);
                    ship.movementSpeed = Math.round(ship.movementSpeed * this.enemyMultiplier);
                    this.enemyShipList.add(ship);
                }
            }
            if(i == 2){
                for(int j = 0; j < enemies[i]; j++){
                    HeavyEnemyShip ship = new HeavyEnemyShip(this.game.rm);
                    ship.bulletLvl = Math.round(ship.bulletLvl * this.enemyMultiplier);
                    this.enemyShipList.add(ship);
                }
            }
            if(i == 3){
                for(int j = 0; j < enemies[i]; j++){
                    BossShip boss = new BossShip(this.game.rm);
                    boss.protectorsNumber = Math.round(boss.protectorsNumber * this.enemyMultiplier);
                    this.enemyShipList.add(boss);
                    this.enemyShipList.addAll(boss.generateProtectors());
                }
            }
        }
    }
}
