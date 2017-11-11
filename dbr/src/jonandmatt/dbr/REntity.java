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

    public REntity(GameWorld par) {
        super(par);
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
}
