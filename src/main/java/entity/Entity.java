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

public class Entity {
    protected double speed;
    protected Point velocity = new Point(0, 0);
    protected Rectangle hitBox;
    protected boolean collisionState;
    protected boolean destructible;
    protected boolean dead = false;

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
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), sprite.w, sprite.h);
        this.sprite = new Sprite(sprite, gc);
        input.onAttach(this);
        collision.onAttach(this);
    }

    public Entity(Point spawn, Entity p) {
        this.input = p.input;
        this.collision = p.collision;
        this.attack = p.attack;
        this.w = p.w;
        this.destructible = p.destructible;
        this.dead = p.dead;
        this.speed = p.speed;
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), p.hitBox.getW(), p.hitBox.getH());
        this.sprite = new Sprite(p.sprite);
        input.onAttach(this);
        collision.onAttach(this);
    }

    public void update() {
        input.handle(this, w);
        collision.handle(this, w);
        velocity.zero();
    }

    public void render() {
        sprite.render(hitBox.getX(), hitBox.getY());
    }

    public double getSpeed() {return speed;}
    public Point getVelocity() {return velocity;}
    public Rectangle getHitBox() {return hitBox;}
    public boolean getCollisionState() {return collisionState;}
    public boolean isDestructible() {return destructible;}
    public boolean isDead() {return dead;}
    public boolean clearable() {
        return dead && sprite.isPausing();
    }

    public InputComponent getInput() {return input;}
    public CollisionComponent getCollision() {return collision;}
    public AttackComponent getAttack() {return attack;}
    public Sprite getSprite() {return sprite;}

    public void setSpeed(double speed) {this.speed = speed;}
    public void setVelocity(Point velocity) {this.velocity = velocity;}
    public void setHitBox(Rectangle hitBox) {this.hitBox = hitBox;}
    public void setCollisionState(boolean collisionState) {this.collisionState = collisionState;}
    public void setDestructible(boolean destructible) {this.destructible = destructible;}
    public void setDead(boolean dead) {this.dead = dead;}

    public void setInput(InputComponent input) {
        this.input = input;
        input.onAttach(this);
    }

    public void setCollision(CollisionComponent collision) {
        this.collision = collision;
        collision.onAttach(this);
    }

    public void setAttack(AttackComponent attack) {
        this.attack = attack;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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
        if (!dead && destructible) {
            collisionState = false;
            sprite.setCurrentAnimation("dead");
            sprite.setTickPerFrame(15);
            sprite.setLoop(false);
            dead = true;
        }
    }
}
