/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.io.AssetLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author Jonathon
 */
public class TrackLoader extends AssetLoader<Track> {

    /*
    
    The format of a track file:
    
    <TYPE> <X> <Y> <WIDTH> <HEIGHT>
    
    Where TYPE [= { TRACK, VOID }
          X, Y, WIDTH, HEIGHT [= INTEGERS
    
    */
    
    @Override
    public void loadAsset(String asset, InputStream fs) {
        Track tr = this.getAsset(asset);
        if (tr == null) {
            //load it and then buffer it
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                String line;
                int linenum = 1;
                while ((line = br.readLine()) != null) {
                    String[] spl = line.split(Pattern.quote(" "));
                    try {
                        int x = Integer.parseInt(spl[1]);
                        int y = Integer.parseInt(spl[2]);
                        int w = Integer.parseInt(spl[3]);
                        int h = Integer.parseInt(spl[4]);
                        REntity trackPiece = new REntity();
                        trackPiece.setToRectangle(x, y, w, h);
                        if (spl[0].equals("TRACK")) {
                            trackPiece.setType(Type.TRACK);
                            tr.tracks.add(trackPiece);
                        } else if (spl[1].equals("VOID")) {
                            trackPiece.setType(Type.VOID);
                            tr.voids.add(trackPiece);
                        }
                    } catch(Exception e) {
                        System.out.println("Line " + linenum + " of " + asset + " gave an error");
                    }
                    linenum++;
                }
                
                //after loading the file (correctly)
                add(asset, tr);
            } catch (IOException ioe) {
                System.err.println("Cant load track: " + ioe);
            }
            
        }
    }

}
