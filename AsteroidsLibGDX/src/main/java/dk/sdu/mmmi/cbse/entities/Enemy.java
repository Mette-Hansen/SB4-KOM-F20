package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends SpaceObject {

    private int type;
    private ArrayList<Bullet> bullets;
    private float fireTimer;
    private float fireTime;
    private Player player;
    private float pathTimer;
    private float pathTime1;
    private float pathTime2;
    private int direction;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private boolean remove; //Only if killed


    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    public Enemy(int type, int direction, Player player, ArrayList<Bullet> bullets) {
        this.type = type;
        this.direction = direction;
        this.player = player;
        this.bullets = bullets;

        speed = 70;
        if (direction == LEFT) {
            dx = -speed;
            x = Game.WIDTH;
        } else if (direction == RIGHT) {
            dx = speed;
            x = 0;
        }

        y = MathUtils.random(Game.HEIGHT);
        shapex = new float[6];
        shapey = new float[6];
        setShape();

        fireTimer = 0;
        fireTime = 1;

        pathTimer = 0;
        pathTime1 = 2;
        pathTime2 = pathTime1 + 2;

    }

    //Shape of the enemy
    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void update(float dt) {
        if (!player.ishit()) {
            fireTimer += dt;
            if (fireTimer > fireTime) {
                fireTimer = 0;
            }
            radians = MathUtils.random(2 * 3.1415f);
            bullets.add(new Bullet(x, y, radians));
        }
        pathTimer += dt;
        if (pathTimer < pathTime1) {
            dy = 0;
        }
        if (pathTimer > pathTime1 && pathTimer < pathTime2) {
            dy = -speed;
        }

        if (pathTimer > pathTime1 + pathTime2) {
            dy = 0;
        }

        x += dx * dt;
        y += dy * dt;
        setShape();
        wrap();

        if (direction == RIGHT && x > Game.RIGHT) ||(direction == LEFT && x < 0)){
            remove = true;
        }
    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 0, 0, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapex.length - 1;
             i < shapex.length;
             j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }
        sr.end();
    }
}
