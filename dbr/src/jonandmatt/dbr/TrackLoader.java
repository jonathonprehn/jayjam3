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
            tr = new Track();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                String line;
                int linenum = 1;
                while ((line = br.readLine()) != null) {
                    try {
                        String[] spl = line.split(Pattern.quote(" "));
                        int x = Integer.parseInt(spl[1]);
                        int y = Integer.parseInt(spl[2]);
                        int w = Integer.parseInt(spl[3]);
                        int h = Integer.parseInt(spl[4]);
                        
                        
                       /* System.out.print("Split \"" + line + "\" into { ");
                        for (int i=0; i < spl.length; i++)
                            System.out.print("\"" + spl[i] + "\" ");
                        System.out.println(" }");
                        */
                        
                        
                        REntity trackPiece = new REntity();
                        trackPiece.setToRectangle(x, y, w, h);
                        if (spl[0].equals("TRACK")) {
                            trackPiece.setType(Type.TRACK);
                            tr.tracks.add(trackPiece);
                        } else if (spl[0].equals("VOID")) {
                            trackPiece.setType(Type.VOID);
                            tr.voids.add(trackPiece);
                        }
                    } catch(Exception e) {
                        System.out.println("Line " + linenum + " of " + asset + " gave an error: " + e);
                    }
                    linenum++;
                }
                
                //after loading the file (correctly)
                System.out.println("Loaded a Track with " + tr.tracks.size() +
                        " track pieces and " + tr.voids.size() + " voids");
                add(asset, tr);
            } catch (IOException ioe) {
                System.err.println("Cant load track: " + ioe);
            }
            
        }
    }

}
