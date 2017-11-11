/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;

import bropals.lib.simplegame.math.Vector2D;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Jonathon
 */
public class REntity extends BaseEntity {

    public REntity() {
        super(null);
    }
    
    public REntity(
            float posX, float posY, 
            float velX, float velY, 
            float m, float dir, 
            Type ty, Shape colShape) {
        super(null);
        pos = new Vector2D(posX, posY);
        vel = new Vector2D(velX, velY);
        mass = m;
        direction = dir;
        type = ty;
        if(type.equals(Type.PLAYER_BUBBLE)) {
            //collisionShape = new Ellipse2D.Float(colShape);
            collisionShape = (Ellipse2D.Float)(colShape);
        }
        else if(type.equals(Type.WALL_BUBBLE)) {
            //collisionShape = new Ellipse2D.Float(colShape);
            collisionShape = (Ellipse2D.Float)(colShape);
        }
    }
    
    
    protected boolean removeMeDead = false;
    protected RacingScreen state;
    protected Vector2D pos;
    protected Vector2D vel;
    protected float mass;
    protected float direction; // measured in radians
    protected Type type;
    protected Shape collisionShape;
    
    @Override
    public void update(int mills) {
        if(removeMeDead) return;
        //update my position based on my velocity
        Vector2D deltaPositionFromVelocityAndTime = vel.scale((float)mills);
        pos.add(deltaPositionFromVelocityAndTime);
    }
    
    public void burstBubble() {
        if(this.type.equals(Type.PLAYER_BUBBLE) || this.type.equals(Type.WALL_BUBBLE)) {
            removeMeDead = true;
            this.vel.setValues(0.0, 0.0);
            this.type = Type.BLOOD;
        }
        else {
            System.out.print("There was an attempt to delete a non-bubble REntity!! Error!");
        }
        handleSoundEvent("String key");
    }
    
    protected void handleSoundEvent(String key) {
        this.state.playSoundEffect(key);
    }
    
    @Override
    public void render(Object graphicsObj) {
        
        
        
    }
    
    void giveState(RacingScreen state) {
        this.state = state;
    }
    
    public Vector2D getPosition() {
        return pos;
    }
    
    public Vector2D getVelocity() {
        return vel;
    }
    
    public Type getType() {
        return type;
    }
    
    //FOR OBJECT CREATION CODE
    public void setType(Type t) {
        this.type = t;
    }
    
    /**
     * 
     * @param x x world position
     * @param y y world position
     * @param w width
     * @param h height
     */
    public void setToRectangle(float x, float y, float w, float h) {
        //sets the shape to a rectangle and sets its position and
        //size to the specified values
        //FOR CREATION CODE
        
        collisionShape = new Rectangle.Float(x, y, w, h);
    }
    
    /**
     * 
     * @param x x world position
     * @param y y world position
     * @param r radius of the circle
     */
    public void setToCircle(float x, float y, float r) {
        //sets the shape to a rectangle and sets its position and
        //size to the specified values
        //FOR CREATION CODE
        
        collisionShape = new Ellipse2D.Float(x, y, r, r);
    }
    
    public boolean isRectangle() {
        return collisionShape instanceof Rectangle.Float;
    }
    
    public boolean isCircle() {
        return collisionShape instanceof Ellipse2D.Float;
    }
    public float getRadius() {
        return (float)(((Ellipse2D.Float)collisionShape).getCenterX() - ((Ellipse2D.Float)collisionShape).getX());
    }
}
