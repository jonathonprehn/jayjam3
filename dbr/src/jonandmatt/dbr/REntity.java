/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import java.awt.Shape;
import bropals.lib.simplegame.math.Vector2D;
import java.awt.Graphics2D;

/**
 *
 * @author Jonathon
 */
public class REntity extends BaseEntity {

    public REntity(GameWorld par) {
        super(par);
    }

    protected RacingScreen state;
    protected Vector2D pos;
    protected Vector2D vel;
    protected double mass;
    protected double direction; // measured in radians
    protected java.awt.Shape shape;
    //private Shape shape;
    protected Type type;
    
    @Override
    public void update(int mills) {
        Surface mySurface = state.getSurface(this);
        
        //update my position based on my velocity, and my friction
        Vector2D deltaPositionFromVelocityAndTime = vel.scale((double)mills);
        pos.add(deltaPositionFromVelocityAndTime);
        /* USE THIS FOR PLAYERBUBBLES
        double frictionForce = Surface.getFrictionForce(mySurface, this.mass);
        double userForce = 1.0; // Player.getForce();.....
        direction += -1.0; // Player.getDeltaDirection
        double netForce = userForce - frictionForce;
        double acceleration = netForce / this.mass;
        Vector2D vectorAcceleration = new Vector2D(acceleration * Math.cos(direction), acceleration * Math.sin(direction));
        Vector2D deltaPositionFromAcceleration = vectorAcceleration.scale(0.5 * mills * mills);
         pos.add(deltaPositionFromAcceleration);
         */
        //oh, am I colliding with something?
    }
    
    // THESE NEED TO BE IN SCOPE OF BOTH THE SET OF ALL BUBBLES AND
    // AND THE SET OF SURFACES/-- A HIGH SCOPE!
//
//    
//    void updateAllEnvironmentBubblePositions {
//        
//    }
//    void updateAllPlayerBubblesPositions() {
//        
//    }
//    void updateCollisionChecking() {
//        
//    }
    @Override
    public void render(Object graphicsObj) {
        Graphics2D g2d = (Graphics2D)graphicsObj;
        
        
    }
    
    void giveState(RacingScreen state) {
        this.state = state;
    }
}
