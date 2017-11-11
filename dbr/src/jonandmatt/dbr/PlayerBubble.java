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
    
    protected float deltaDirection = 0.0F;
    protected float userForce = 0.0F;
    protected static float floatMaxVelocityMagnitude = 10.0F;
    
    
    
    @Override
    public void update(int mills) {
        if(removeMeDead) return;
        //update my position based on my velocity, and my friction
        Surface mySurface = state.getSurface(this);
        direction += deltaDirection;
        
       
        
        // USE THIS FOR PLAYERBUBBLES
        float frictionForce = Surface.getFrictionForce(mySurface, this.mass);
        //float userForce = 1.0; // Player.getForce();.....
        //direction += -1.0; // Player.getDeltaDirection
        float netForce = userForce - frictionForce;
        float acceleration = netForce / this.mass;
        
        Vector2D vectorAcceleration = new Vector2D(acceleration * Math.cos(direction), acceleration * Math.sin(direction));
        Vector2D deltaVelocityFromAcceleration = vectorAcceleration.scale(mills); //)new Vector2D(vectorAcceleration.getX() * mills, vectorAcceleration.getY() * mills);
        Vector2D newVelocity = this.vel.add(deltaVelocityFromAcceleration);
        if(newVelocity.magnitude() > floatMaxVelocityMagnitude) {
            newVelocity.scaleLocal(floatMaxVelocityMagnitude/newVelocity.magnitude());
        }
        //Vector2D deltaPositionFromAcceleration = vectorAcceleration.scale(0.5 * mills * mills);
        Vector2D deltaPositionFromNewVelocity = newVelocity.scale(0.5 * mills);
        // everything is all set to change the position and the velocity
         Vector2D deltaPositionFromOldVelocity = vel.scale((float)mills);
        pos.addLocal(deltaPositionFromOldVelocity);
        pos.addLocal(deltaPositionFromNewVelocity);
        
        vel = newVelocity; //vel.addLocal(deltaVelocityFromAcceleration);
    }
    
    //protected boolean keysPressed[] = new boolean[3];
    protected void handleKeyEvent(int keycode) {
        switch(keycode) {
            case bropals.lib.simplegame.KeyCode.KEY_LEFT:
                deltaDirection -= 1.0;
                break;
            case bropals.lib.simplegame.KeyCode.KEY_RIGHT:  
                deltaDirection += 1.0;            
                break;
            case bropals.lib.simplegame.KeyCode.KEY_UP:  
                userForce += 1.0;
                break;
            default:
             // this.state.key(bropals.lib.simplegame.KeyCode.KEY_LEFT, keysPressed[0]);   
        }
    }
    
    @Override
    protected void handleSoundEvent(String key) {
        this.state.playSoundEffect(key);

    }
    
    public void burstBubble() {
        super.burstBubble();
        this.handleSoundEvent("String key");
    }
}
