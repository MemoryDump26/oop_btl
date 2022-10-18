package entity;

import attack.AttackComponent;
import collision.CollisionComponent;
import input.InputComponent;
import geometry.Point;
import geometry.Rectangle;
import sprite.Sprite;
import sprite.SpriteData;

import javafx.scene.canvas.GraphicsContext;
import world.World;

import java.util.ArrayList;

public class Entity {
    protected double speed;
    protected Point velocity = new Point(0, 0);
    protected Rectangle hitBox;
    protected boolean collisionState;
    protected boolean destructible;
    protected boolean harmful = false;
    protected boolean dead = false;
    public ArrayList<World.Direction> availMove = new ArrayList<>();
    public World.Direction currentDirection = World.Direction.UP;

    protected World w;
    protected InputComponent input;
    protected CollisionComponent collision;
    protected AttackComponent attack;
    protected Sprite sprite;

    public Entity(
        Point spawn,
        InputComponent input,
        CollisionComponent collision,
        AttackComponent attack,
        World w,
        SpriteData sprite,
        GraphicsContext gc
    ) {
        this.input = input;
        this.collision = collision;
        this.attack = attack;
        this.w = w;
        this.collisionState = collision.getDefaultState();
        this.destructible = collision.isDestructibles();
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), sprite.w, sprite.h);
        this.sprite = new Sprite(sprite, gc);
    }

    public Entity(Point spawn, Entity p) {
        this.input = p.input;
        this.collision = p.collision;
        this.attack = p.attack;
        this.w = p.w;
        this.collisionState = collision.getDefaultState();
        this.destructible = collision.isDestructibles();
        this.harmful = p.harmful;
        this.dead = p.dead;
        this.speed = p.speed;
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), p.hitBox.getW(), p.hitBox.getH());
        this.sprite = new Sprite(p.sprite);
    }

    public void update() {
        input.handle(this, w);
        collision.handle(this, w.getNearbyEntities(this));
        velocity.zero();
    }

    public void render() {
        sprite.render(hitBox.getX(), hitBox.getY());
    }

    public Rectangle getHitBox() {return hitBox;}
    public Sprite getSprite() {return sprite;}
    public double getSpeed() {return speed;}
    public Point getVelocity() {return velocity;}
    public boolean getCollisionState() {return collisionState;}
    public boolean isDead() {return dead;}
    public boolean clearable() {
        return dead && sprite.isPausing();
    }
    public boolean isHarmful() {
        return harmful;
    }

    public void setSpeed(double speed) {this.speed = speed;}
    public void setVelocity(Point velocity) {this.velocity = velocity;}
    public void setCollisionState(boolean collisionState) {this.collisionState = collisionState;}
    public void setHarmful(boolean harmful) {
        this.harmful = harmful;
    }

    public void move(double x, double y) {
        velocity.add(x, y);
    }

    public void moveTo(double x, double y) {
        this.getHitBox().setX(x);
        this.getHitBox().setY(y);
    }

    public void moveUp() {
        sprite.setCurrentAnimation("up");
        move(0, -speed);
    }

    public void moveDown() {
        sprite.setCurrentAnimation("down");
        move(0, speed);
    }

    public void moveLeft() {
        sprite.setCurrentAnimation("left");
        move(-speed, 0);
    }

    public void moveRight() {
        sprite.setCurrentAnimation("right");
        move(speed, 0);
    }

    public void attack() {
        attack.handle(this, w);
    }

    public void kill() {
        if (destructible) {
            sprite.setCurrentAnimation("dead");
            sprite.setTickPerFrame(10);
            sprite.setLoop(false);
            dead = true;
        }
    }
}
