/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.controls.Controller;
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
    ArrayList<REntity> eBubbles = new ArrayList<>();
    ArrayList<PlayerBubble> pBubbles = new ArrayList<>();
    
    
    @Override
    public void update(int mills) {
        //UPDATE ALL ENVIRONMENT BUBBLES HERE
        for(REntity environmentBubble : eBubbles) {
            environmentBubble.update(mills);
        }
        
        
        //UPDATE ALL PLAYER BUBBLES HERE
        for(PlayerBubble playerBubble : pBubbles) {
            playerBubble.update(mills);
        }
        
        
        //UPDATE THIS PLAYER'S SPECIFIED PROPERTIES
        
        
        
        //LETS DO SOME COLLISION CHECKING NOW
        handleCollisions();
        
         
        //LETS UPDATE THE CAMERA NOW
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
    
    
    public Surface getSurface(REntity entity) {
        
        
        return Surface.GRASS;
    }

    @Override
    public void mouse(int mousebutton, int x, int y, boolean pressed) {
        super.mouse(mousebutton, x, y, pressed); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void key(int keycode, boolean pressed) {
        //super.key(keycode, pressed); //To change body of generated methods, choose Tools | Templates.
        if(pressed) {
            pBubbles.iterator().next().handleKeyEvent(keycode);
            // currentPlayer.handleKeyEvent(params);
        }
        else {
            // currentPlayer.handleKeyEvent(params);
        }
    }

    @Override
    public void addController(Controller controller) {
        super.addController(controller); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playSoundEffect(String key) {
        super.playSoundEffect(key); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void loadTrack(String trackName) {
        getAssetManager().loadAsset(trackName, trackName, Track.class);
        Track loadedTrack = getAssetManager().getAsset(trackName, Track.class);
        if (loadedTrack != null) {
            tracks.clear();
            voids.clear();
            tracks = loadedTrack.tracks;
            voids = loadedTrack.voids;
        } else {
            System.err.println("Cant load track in RacingScreen: " + trackName);
        }
    }
    
    private void handleCollisions() {
        
    }
}
