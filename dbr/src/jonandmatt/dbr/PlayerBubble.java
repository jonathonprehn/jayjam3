/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.math.Vector2D;
import jonandmatt.dbr.REntity; 
/**
 *
 * @author Matt Weiss
 */
public class PlayerBubble extends REntity {
    
    @Override
    public void update(int mills) {
        Surface mySurface = state.getSurface(this);
        
        //update my position based on my velocity, and my friction
        Vector2D deltaPositionFromVelocityAndTime = vel.scale((double)mills);
        pos.add(deltaPositionFromVelocityAndTime);
        // USE THIS FOR PLAYERBUBBLES
        double frictionForce = Surface.getFrictionForce(mySurface, this.mass);
        double userForce = 1.0; // Player.getForce();.....
        direction += -1.0; // Player.getDeltaDirection
        double netForce = userForce - frictionForce;
        double acceleration = netForce / this.mass;
        Vector2D vectorAcceleration = new Vector2D(acceleration * Math.cos(direction), acceleration * Math.sin(direction));
        Vector2D deltaPositionFromAcceleration = vectorAcceleration.scale(0.5 * mills * mills);
         pos.add(deltaPositionFromAcceleration);
         
        //oh, am I colliding with something?
    }
}
