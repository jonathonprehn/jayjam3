/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.state.GameState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jonathon
 */
public class RacingScreen extends GameState {

    ArrayList<REntity> tracks = new ArrayList<>();
    ArrayList<REntity> voids = new ArrayList<>();
    ArrayList<REntity> blood = new ArrayList<>();
    ArrayList<REntity> eBubbles = new ArrayList<>();
    ArrayList<REntity> pBubbles = new ArrayList<>();
    
    protected float cameraX;
    protected float cameraY;
    
    @Override
    public void update(int mills) {
        //UPDATE ALL ENVIRONMENT BUBBLES HERE
        for (Iterator<REntity> itr = eBubbles.iterator(); itr.hasNext(); ) {
            itr.next().update(mills);
        }
        
        
        //UPDATE ALL PLAYER BUBBLES HERE
        for (Iterator<REntity> itr = pBubbles.iterator(); itr.hasNext(); ) {
            itr.next().update(mills);
        }
        
        
        //UPDATE THIS PLAYER'S SPECIFIED PROPERTIES
        
        
        
        //LETS DO SOME COLLISION CHECKING NOW
        handleCollisions();
        
         
        //LETS UPDATE THE CAMERA NOW
    }

    @Override
    public void render(Object graphicsObj) {
        Graphics2D g2d = (Graphics2D)graphicsObj;
        GameWindow theWindow = this.getWindow();
        
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, theWindow.getScreenWidth(), theWindow.getScreenHeight());
        
        g2d.setColor(Color.BLACK);
        for (Iterator<REntity> itr = voids.iterator(); itr.hasNext(); ) {
            REntity re = itr.next();
            if (shouldDrawPart(re)) {
                g2d.fillRect((int)(re.asRectangle().getX()-cameraX),
                    (int)(re.asRectangle().getY()-cameraY),
                    (int)re.asRectangle().getWidth(),
                    (int)re.asRectangle().getHeight());
            }
        }
        g2d.setColor(Color.ORANGE);
        for (Iterator<REntity> itr = tracks.iterator(); itr.hasNext(); ) {
            REntity re = itr.next();
            if (shouldDrawPart(re)) {
                g2d.fillRect((int)(re.asRectangle().getX()-cameraX),
                    (int)(re.asRectangle().getY()-cameraY),
                    (int)re.asRectangle().getWidth(),
                    (int)re.asRectangle().getHeight());
            }
        }
        g2d.setColor(Color.RED);
        for (Iterator<REntity> itr = blood.iterator(); itr.hasNext(); ) {
            REntity re = itr.next();
            if (shouldDrawPart(re)) {
                g2d.fillOval((int)(re.asCircle().getX()-cameraX),
                    (int)(re.asCircle().getY()-cameraY),
                    (int)re.asCircle().getWidth(),
                    (int)re.asCircle().getHeight());
            }
        }
        g2d.setColor(Color.YELLOW);
        for (Iterator<REntity> itr = eBubbles.iterator(); itr.hasNext(); ) {
            REntity re = itr.next();
            if (shouldDrawPart(re)) {
                g2d.fillOval((int)(re.asCircle().getX()-cameraX),
                    (int)(re.asCircle().getY()-cameraY),
                    (int)re.asCircle().getWidth(),
                    (int)re.asCircle().getHeight());
            }
        }
        g2d.setColor(Color.BLUE);
        for (Iterator<REntity> itr = pBubbles.iterator(); itr.hasNext(); ) {
            REntity re = itr.next();
            if (shouldDrawPart(re)) {
                g2d.fillOval((int)(re.asCircle().getX()-cameraX),
                    (int)(re.asCircle().getY()-cameraY),
                    (int)re.asCircle().getWidth(),
                    (int)re.asCircle().getHeight());
            }
        }
        
        //draw the center point
        g2d.setColor(Color.RED);
        g2d.fillOval(theWindow.getScreenWidth()/2, theWindow.getScreenHeight()/2, 5, 5);
        g2d.drawString("( "
                + (cameraX + theWindow.getScreenWidth()/2) + ", "
                + (cameraY + theWindow.getScreenHeight()/2) + ")"
                
                , theWindow.getScreenWidth()/2 - 50, theWindow.getScreenHeight()/2 - 50);
    }

    @Override
    public void onEnter() {
        loadTrack("track1.txt");
    }

    @Override
    public void onExit() {
    }
    
    
    public Surface getSurface(REntity entity) {
        
        
        return Surface.GRASS;
    }

    public float getCameraY() {
        return cameraY;
    }

    public float getCameraX() {
        return cameraX;
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
    
    /*
        Don't draw anything if it is not going to be in the screen
        Clipping box coordinates:
        
    */
    private boolean shouldDrawPart(REntity re) {
        if (re.isRectangle()) {
            return re.asRectangle().intersects(cameraX, cameraY, 
                    getWindow().getScreenWidth(), getWindow().getScreenHeight());
        } else if (re.isCircle()) {
            return re.asCircle().intersects(cameraX, cameraY, 
                    getWindow().getScreenWidth(), getWindow().getScreenHeight());
        } else {
            return false;
        }
    }
    
    private void handleCollisions() {
        /*
            Every environment bubble with every environment bubble
            Every player bubble with every environment bubble
            Every player bubble with every player bubble
        
            E = number of environment bubbles
            P = number of player bubbles
        
            T(E, P) = E*E + E*P + P*P)
                = E*(E+P)+ P*P
            
            If we have 1000 environment bubbles and 10 player bubbles, then
            T(1000, 10) = 1010100
            If the framerate is a desired 30 milliseconds, then must handle
                T(1000, 10) = 33670 collision checks per second.
        */
        
        //We can safely assume that voids and tracks are rectangle
        //and environment bubbles, player bubbles and blood are circles
        
        
    }
}
