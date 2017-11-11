/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.io.AssetManager;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Dbr {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameWindow win = new AWTGameWindow("DEATH BUBBLE RACING", 1024, 720, false);
        AssetManager assets = new AssetManager(new File("assets"), true);        
        assets.addAssetLoader(new TrackLoader(), Track.class);
        GameStateRunner runner = new GameStateRunner(win, assets, new RacingScreen());
        //start!
        runner.loop();
        
    }
    
}
