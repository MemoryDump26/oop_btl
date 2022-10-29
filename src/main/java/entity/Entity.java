package entity;

import components.Component;
import geometry.Point;
import geometry.Rectangle;
import sprite.Sprite;
import world.World;

import java.util.ArrayList;

public class Entity {
    protected double speed;
    protected Point velocity = new Point(0, 0);
    protected Rectangle hitBox;
    protected boolean collisionState;
    protected boolean destructible;
    protected boolean dead = false;

    protected World w;
    protected ArrayList<Component<Entity>> auxiliaryComponent = new ArrayList<>();
    protected Component<Entity> input;
    protected Component<Entity> collision;
    protected Component<Entity> attack;
    protected Sprite sprite;

    public Entity(Point spawn, World w, Sprite sprite) {
        this(spawn, Component.getNull(), Component.getNull(), Component.getNull(), w, sprite);
    }

    public Entity(
            Point spawn,
            Component<Entity> input,
            Component<Entity> collision,
            Component<Entity> attack,
            World w,
            Sprite sprite
    ) {
        this.input = input;
        this.collision = collision;
        this.attack = attack;
        this.w = w;
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), sprite.getData().getW(), sprite.getData().getH());
        this.sprite = new Sprite(sprite);
        input.onAttach(this);
        collision.onAttach(this);
    }

    public Entity (Entity p) {
        this(Point.ZERO, p);
    }

    public Entity(Point spawn, Entity p) {
        this(
                spawn,
                p.getInput(),
                p.getCollision(),
                p.getAttack(),
                p.getWorld(),
                p.getSprite()
        );
        this.hitBox = new Rectangle(
                spawn.getX(),
                spawn.getY(),
                p.getSprite().getData().getW(),
                p.getSprite().getData().getH()
        );
        this.speed = p.getSpeed();
        this.dead = p.isDead();
        this.auxiliaryComponent = new ArrayList<>(p.auxiliaryComponent);
        for (Component<Entity> aux : auxiliaryComponent) {
            aux.onAttach(this);
        }
    }

    public void update() {
        for (Component<Entity> aux : auxiliaryComponent) {
            aux.handle(this);
        }
        input.handle(this);
        collision.handle(this);
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

    public World getWorld() {return w;}
    public Component<Entity> getInput() {return input;}
    public Component<Entity> getCollision() {return collision;}
    public Component<Entity> getAttack() {return attack;}
    public Sprite getSprite() {return sprite;}

    public void setSpeed(double speed) {this.speed = speed;}
    public void setVelocity(Point velocity) {this.velocity = velocity;}
    public void setHitBox(Rectangle hitBox) {this.hitBox = hitBox;}
    public void setCollisionState(boolean collisionState) {this.collisionState = collisionState;}
    public void setDestructible(boolean destructible) {this.destructible = destructible;}
    public void setDead(boolean dead) {this.dead = dead;}

    public void setWorld(World w) {this.w = w;}

    public void addAuxiliaryComponent(Component<Entity> c) {
        auxiliaryComponent.add(c);
        c.onAttach(this);
    }

    public void setInput(Component<Entity> input) {
        this.input = input;
        input.onAttach(this);
    }

    public void setCollision(Component<Entity> collision) {
        this.collision = collision;
        collision.onAttach(this);
    }

    public void setAttack(Component<Entity> attack) {
        this.attack = attack;
        attack.onAttach(this);
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
        attack.handle(this);
    }

    public void kill() {
        if (!dead && destructible) {
            collisionState = false;
            sprite.setCurrentAnimation("dead");
            sprite.setTickPerFrame(8);
            sprite.setLoop(false);
            dead = true;
        }
    }
}
