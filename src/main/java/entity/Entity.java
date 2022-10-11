package entity;

import collision.CollisionComponent;
import input.InputComponent;
import geometry.Point;
import geometry.Rectangle;
import sprite.Sprite;
import sprite.SpriteData;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public abstract class Entity {
    protected double speed;
    protected Point velocity = new Point(0, 0);
    protected Rectangle hitBox;
    protected Sprite sprite;
    protected boolean collisionState;

    protected InputComponent input;
    protected CollisionComponent collision;

    public Entity(Point spawn, InputComponent input, CollisionComponent collision, SpriteData sprite, GraphicsContext gc) {
        this.input = input;
        this.collision = collision;
        this.collisionState = collision.getDefaultState();
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), sprite.w, sprite.h);
        this.sprite = new Sprite(sprite, gc);
    }

    public Entity(Point spawn, Entity p) {
        this.input = p.input;
        this.collision= p.collision;
        this.collisionState = collision.getDefaultState();
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), p.hitBox.getW(), p.hitBox.getH());
        this.sprite = new Sprite(p.sprite);
    }

    public void update(ArrayList<Entity> world) {
        input.handle(this);
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

    public void setSpeed(double speed) {this.speed = speed;}
    public void setVelocity(Point velocity) {this.velocity = velocity;}
    public void setCollisionState(boolean collisionState) {this.collisionState = collisionState;}

    public abstract void move(double x, double y);
    public abstract void moveTo(double x, double y);
    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void touched(Entity e);
    public abstract void attack();
    public abstract void kill();
}
