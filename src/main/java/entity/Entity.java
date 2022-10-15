package entity;

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
    protected Sprite sprite;
    protected boolean collisionState;
    protected boolean destructible;
    protected boolean dead = false;

    protected InputComponent input;
    protected CollisionComponent collision;

    public Entity(Point spawn, InputComponent input, CollisionComponent collision, SpriteData sprite, GraphicsContext gc) {
        this.input = input;
        this.collision = collision;
        this.collisionState = collision.getDefaultState();
        this.destructible = collision.isDestructibles();
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), sprite.w, sprite.h);
        this.sprite = new Sprite(sprite, gc);
    }

    public Entity(Point spawn, Entity p) {
        this.input = p.input;
        this.collision = p.collision;
        this.collisionState = collision.getDefaultState();
        this.destructible = collision.isDestructibles();
        this.dead = p.dead;
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), p.hitBox.getW(), p.hitBox.getH());
        this.sprite = new Sprite(p.sprite);
    }

    public void update(ArrayList<Entity> world, World w) {
        input.handle(this, w);
        collision.handle(this, world);
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
    public boolean isDead() {
        return dead && sprite.isPausing();
    }

    public void setSpeed(double speed) {this.speed = speed;}
    public void setVelocity(Point velocity) {this.velocity = velocity;}
    public void setCollisionState(boolean collisionState) {this.collisionState = collisionState;}

    /*public abstract void move(double x, double y);
    public abstract void moveTo(double x, double y);
    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void touched(Entity e);
    public abstract void attack();
    public abstract void kill();*/

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

    public void kill() {
        if (destructible) {
            sprite.setCurrentAnimation("dead");
            sprite.setLoop(false);
            dead = true;
        }
    }
}
