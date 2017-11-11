/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.state.GameState;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class RacingScreen extends GameState {

    ArrayList<REntity> tracks = new ArrayList<>();
    ArrayList<REntity> voids = new ArrayList<>();
    ArrayList<REntity> blood = new ArrayList<>();
    ArrayList<REntity> bubbles = new ArrayList<>();
    
    
    @Override
    public void update(int mills) {
    }

    @Override
    public void render(Object graphicsObj) {
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {
    }
    
    Surface getSurface(REntity entity) {
        
        
        return Surface.GRASS;
    }
}
