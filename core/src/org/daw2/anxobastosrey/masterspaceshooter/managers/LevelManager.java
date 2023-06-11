package org.daw2.anxobastosrey.masterspaceshooter.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private final PlayerShip player;
    private final LinkedList<Ship> enemyShipList = new LinkedList<>();
    private final ArrayList<Explosion> explosionList = new ArrayList<>();

    private final ResourceManager rm;
    private final SpriteBatch batch;

    public int score = 0;

    public float timeSinceLastLevel = 0;
    public final float timeBetweenLevels = 7;

    public int enemiesPerLevel = 3;
    public float enemyMultiplier = 1;
    public final int maxBosses = 1;

    public int scoreToUpgradeEnemies = 3000;

    private final LinkedList<Laser> playerLaserList = new LinkedList<>();
    private final LinkedList<Laser> enemyLaserList = new LinkedList<>();

    public LevelManager(PlayerShip player, ResourceManager rm, SpriteBatch batch){
        this.player = player;
        this.rm = rm;
        this.batch = batch;
        this.generateLevel();
    }

    public void update(float deltaTime){
        if (player.lives > 0){
            this.player.update(deltaTime, this.batch);
            this.player.draw(batch);
            this.timeSinceLastLevel += deltaTime;
            if(this.timeSinceLastLevel > this.timeBetweenLevels){
                this.generateLevel();
                this.timeSinceLastLevel -= this.timeBetweenLevels;
            }
            ListIterator<Ship> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Ship enemyShip = enemyShipListIterator.next();
                enemyShip.update(deltaTime, batch);
                enemyShip.draw(batch);
            }
        }
    }

    public void detectCollisions() {
        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Laser> laserListIterator = this.playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<Ship> enemyShipListIterator = this.enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                Ship enemyShip = enemyShipListIterator.next();
                if (enemyShip.intersects(laser.boundingBox)) {
                    //contact with enemy ship
                    if (enemyShip.hitAndCheckDestroyed()) {
                        enemyShipListIterator.remove();
                        this.explosionList.add(new Explosion(enemyShip.boundingBox.getX(), enemyShip.boundingBox.getY()));
                        this.score += 100;
                        this.player.points += 50;
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
            if (this.player.intersects(laser.boundingBox)) {
                //contact with player ship
                if (this.player.hitAndCheckDestroyed(laser.bulletDmg)) {
                    this.explosionList.add(new Explosion(this.player.boundingBox.getX(), this.player.boundingBox.getY()));
                    this.player.shield = this.player.defaultShield + (2 * this.player.shieldLvl);
                    this.player.lives--;
                }
                laserListIterator.remove();
            }
        }
    }

    public void renderLasers(float deltaTime) {
        //create new lasers
        //player lasers
        if (this.player.canFireLaser()) {
            Laser[] lasers = this.player.fireLasers();
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
        }

        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = this.playerLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed * deltaTime;
            if (laser.boundingBox.y > MasterSpaceShooter.WORLD_HEIGHT) {
                iterator.remove();
            }
        }
        iterator = this.enemyLaserList.listIterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(this.batch);
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
                this.explosionList.get(i).draw(this.batch, this.rm.explosionParticle);
            }
        }
    }

    private void generateLevel(){
        if(this.scoreToUpgradeEnemies < this.score){
            this.enemyMultiplier += 0.1;
            this.scoreToUpgradeEnemies = this.scoreToUpgradeEnemies * 2;
            System.out.println(this.enemyMultiplier);
            System.out.println(this.scoreToUpgradeEnemies);
        }
        int enemyCounter = Math.round(this.enemiesPerLevel * this.enemyMultiplier);
        int[] enemies = new int[4];
        for (int i = 0; i < enemies.length; i++){
            enemies[i] = MasterSpaceShooter.random.nextInt(enemyCounter) + 1;
            enemyCounter -= enemies[i];
            if(enemyCounter == 0) break;
        }
        if( (MasterSpaceShooter.random.nextInt(100) + 1) > 95) enemies[3] = this.maxBosses;
        for (int i = 0; i < enemies.length; i++){
            if(i == 0){
                for(int j = 0; j < enemies[i]; j++){
                    this.enemyShipList.add(new NormalEnemyShip(this.rm));
                }
            }
            if(i == 1){
                for(int j = 0; j < enemies[i]; j++){
                    FastEnemyShip ship = new FastEnemyShip(this.rm);
                    ship.movementSpeed = Math.round(ship.movementSpeed * this.enemyMultiplier);
                    this.enemyShipList.add(ship);
                }
            }
            if(i == 2){
                for(int j = 0; j < enemies[i]; j++){
                    HeavyEnemyShip ship = new HeavyEnemyShip(this.rm);
                    ship.bulletLvl = Math.round(ship.bulletLvl * this.enemyMultiplier);
                    this.enemyShipList.add(ship);
                }
            }
            if(i == 3){
                for(int j = 0; j < enemies[i]; j++){
                    BossShip boss = new BossShip(this.rm);
                    boss.protectorsNumber = Math.round(boss.protectorsNumber * this.enemyMultiplier);
                    this.enemyShipList.add(boss);
                    this.enemyShipList.addAll(boss.generateProtectors());
                }
            }
        }
    }
}
