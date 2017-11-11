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

/**
 *
 * @author Jonathon
 */
public class REntity extends BaseEntity {

    public REntity() {
        super(null);
    }

    private RacingScreen state;
    private Vector2D pos;
    private Vector2D vel;
    private Shape shape;
    private Type type;
    
    @Override
    public void update(int mills) {
        Surface mySurface = state.getSurface(this);
        
        //update my position based on my velocity
        pos.add(vel);
        
        //oh, am I colliding with something?
    }

    @Override
    public void render(Object graphicsObj) {
        Graphics2D g2d = (Graphics2D)graphicsObj;
        
        
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
    
    public void setToRectangle(float x, float y, float w, float h) {
        //sets the shape to a rectangle and sets its position and
        //size to the specified values
        //FOR TRACK CREATION CODE
        
        //TODO
    }
}
