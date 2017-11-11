/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonandmatt.dbr;

import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.controls.Controller;
import bropals.lib.simplegame.state.GameState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import bropals.lib.simplegame.math.Vector2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
    ArrayList<PlayerBubble> pBubbles = new ArrayList<>();
    
    protected float cameraX;
    protected float cameraY;
    
    @Override
    public void update(int mills) {
        //UPDATE ALL ENVIRONMENT BUBBLES HERE
        for (Iterator<REntity> itr = eBubbles.iterator(); itr.hasNext(); ) {
            itr.next().update(mills);
        }
        
        
        //UPDATE ALL PLAYER BUBBLES HERE
        for (Iterator<PlayerBubble> itr = pBubbles.iterator(); itr.hasNext(); ) {
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
        for (Iterator<PlayerBubble> itr = pBubbles.iterator(); itr.hasNext(); ) {
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
    
    /**** COLLISION SUB-FUNCTIONS ****/
    private void CheckAndHandleWallCollision(REntity environmentBubble) {
        // Do something...
        // THIS IS FOR WALL COLLISIONS
        for(REntity wall : voids) {
            Rectangle2D bounds = wall.collisionShape.getBounds2D();            
            float radius = environmentBubble.getRadius();
            if (((environmentBubble.pos.getX() + radius) <= bounds.getMaxX() ) | ((environmentBubble.pos.getX() - radius) >= bounds.getMinX()))
                environmentBubble.vel.setX(-1 * environmentBubble.vel.getX());

            if (((environmentBubble.pos.getY() + radius) <= bounds.getMaxY() ) | ((environmentBubble.pos.getY() - radius) <= bounds.getMaxY() )) //canv.Height
                environmentBubble.vel.setY(-1 * environmentBubble.vel.getY());
        }

    }
    private void CheckAndHandleEnvironmentBubbleCollision(REntity environmentBubble, REntity environmentBubble2) {
        // Do something...
        boolean isCollision = false;
        float distance = environmentBubble.pos.add(environmentBubble2.pos.scale(-1.0)).magnitude();
        
        //Ellipse2D.Float temporary = (Ellipse2D.Float)environmentBubble.collisionShape;
        //Ellipse2D.Float temporary2 = (Ellipse2D.Float)environmentBubble2.collisionShape;
        float eB1radius = environmentBubble.getRadius(); //temporary.getCenterX() - temporary.getX(); // also true: temporary.getCenterY() - temporary.getY()
        float eB2radius = environmentBubble.getRadius(); //temporary2.getCenterX() - temporary2.getX(); // also true: temporary.getCenterY() - temporary.getY()
        float radiiSum = eB1radius + eB2radius;
        if(distance > radiiSum) {
            isCollision = false; // the circles are too far apart.
        } else if( distance <= radiiSum) {
            isCollision = true;
        } else if( distance == 0. && eB1radius == eB2radius) {
            isCollision = true;
        }
        else isCollision = true;
        if(!isCollision) return;
        
        
        resolveEnvToEnvBubbleCollision(environmentBubble, environmentBubble2);
    }
    private void CheckAndHandleFallingIntoVoid(PlayerBubble playerBubble) {
        // Do something...
        // THIS IS FOR PLAYER FALLING IN THE VOID
        for(REntity chasm : voids) {
            Rectangle2D bounds = chasm.collisionShape.getBounds2D();
            float radius = playerBubble.getRadius();
            if (((playerBubble.pos.getX() + radius) <= bounds.getMaxX() ) | ((playerBubble.pos.getX() - radius) >= bounds.getMinX())) {
                playerBubble.burstBubble();
                System.out.println("Player " + playerBubble.hashCode() + " fell into the void!");
            }

            if (((playerBubble.pos.getY() + radius) <= bounds.getMaxY() ) | ((playerBubble.pos.getY() - radius) <= bounds.getMaxY() )) {
                playerBubble.burstBubble();
                System.out.println("Player " + playerBubble.hashCode() + " fell into the void!");
            }
        }

    }
    private void CheckAndHandlePlayerToPlayerBubbleCollision(PlayerBubble playerBubble, REntity environmentBubble) {
        // Do something...
    }
    private void CheckAndHandlePlayerToEnvironmentBubbleCollision(PlayerBubble playerBubble, REntity environmentBubble) {
        // Do something...
    }
    
    private void handleCollisions() {
        // Environment bubbles' wall collisions
        for(REntity environmentBubble : eBubbles) {
            CheckAndHandleWallCollision(environmentBubble); // environmentBubble.update(mills);
        }
        
        // Environment bubble to Env. bubble collision
        for(REntity environmentBubble : eBubbles) {
            for(REntity environmentBubble2 : eBubbles) {
                if(environmentBubble != environmentBubble2 
                        || environmentBubble.equals(environmentBubble2)) {
                        CheckAndHandleEnvironmentBubbleCollision(environmentBubble, environmentBubble2); // environmentBubble.update(mills);
                }
            }
        }
          
        // Player bubbles' fall into void detection
        for(PlayerBubble playerBubble : pBubbles) {
            CheckAndHandleFallingIntoVoid(playerBubble); // environmentBubble.update(mills);
        }
        
        // Player bubble to either (Pla. or Env.) bubble collision
        for(PlayerBubble playerBubble : pBubbles) {
            for(PlayerBubble playerBubble2 : pBubbles) {
                if(playerBubble != playerBubble2 
                        || playerBubble.equals(playerBubble2)) {
                        CheckAndHandlePlayerToPlayerBubbleCollision(playerBubble, playerBubble2); // environmentBubble.update(mills);
                }
            }
            for(REntity environmentBubble2 : eBubbles) {
                CheckAndHandlePlayerToEnvironmentBubbleCollision(playerBubble, environmentBubble2); // environmentBubble.update(mills);
            }
        }
    } // END handleCollisions
    
    
    
    public void resolveEnvToEnvBubbleCollision(REntity _ball1, REntity _ball2)
    {
        float collisionision_angle = (float)Math.atan2((_ball2.pos.getY() - _ball1.pos.getY()), (_ball2.pos.getX() - _ball1.pos.getX()));         
        float speed1 = _ball1.vel.magnitude();
        float speed2 = _ball2.vel.magnitude();

        float direction_1 = (float)Math.atan2(_ball1.vel.getY(), _ball1.vel.getX());
        float direction_2 = (float)Math.atan2(_ball2.vel.getY(), _ball2.vel.getX());
        float new_xspeed_1 = (float)(speed1 * Math.cos(direction_1 - collisionision_angle));
        float new_yspeed_1 = (float)(speed1 * Math.sin(direction_1 - collisionision_angle));
        float new_xspeed_2 = (float)(speed2 * Math.cos(direction_2 - collisionision_angle));
        float new_yspeed_2 = (float)(speed2 * Math.sin(direction_2 - collisionision_angle));

        float final_xspeed_1 = ((_ball1.mass - _ball2.mass) * new_xspeed_1 + (_ball2.mass + _ball2.mass) * new_xspeed_2) / (_ball1.mass + _ball2.mass);
        float final_xspeed_2 = ((_ball1.mass + _ball1.mass) * new_xspeed_1 + (_ball2.mass - _ball1.mass) * new_xspeed_2) / (_ball1.mass + _ball2.mass);
        float final_yspeed_1 = new_yspeed_1;
        float final_yspeed_2 = new_yspeed_2;

        float cosAngle = (float)Math.cos(collisionision_angle);
        float sinAngle = (float)Math.sin(collisionision_angle);
        _ball1.vel.setX(cosAngle * final_xspeed_1 - sinAngle * final_yspeed_1);
        _ball1.vel.setY(sinAngle * final_xspeed_1 + cosAngle * final_yspeed_1);
        _ball1.vel.setX(cosAngle * final_xspeed_2 - sinAngle * final_yspeed_2);
        _ball1.vel.setY(sinAngle * final_xspeed_2 + cosAngle * final_yspeed_2);

        Vector2D pos1 = new Vector2D(_ball1.pos.getX(), _ball1.pos.getY());
        Vector2D pos2 = new Vector2D(_ball2.pos.getX(), _ball2.pos.getY());

        // get the mtd
        Vector2D posDiff = pos1.add(pos2.scale(-1.0));
//      Vector2D posDiff = minus(pos1, pos2); // pos1 - pos2;
//      float d = posDiff.Length();
        float d = posDiff.magnitude();

        // minimum translation distance to push balls apart after intersecting
        Vector2D mtd =  posDiff.scale( ((_ball1.getRadius() + _ball2.getRadius() ) - d) / d );
//        Vector2D mtd =  multiply(posDiff,  (((_ball1.r + _ball2.r) - d) / d) );
        
        // resolve intersection --
        // computing inverse mass quantities
        float im1 = 1 / _ball1.mass;
        float im2 = 1 / _ball2.mass;
        float factor = (im1 / (im1 + im2));
        // push-pull them apart based off their mass
        pos1.addLocal(mtd.scale(factor));
        pos2.addLocal(mtd.scale(-factor));
        //pos1 =  plus(pos1, multiply( mtd, (im1 / (im1 + im2)) )); // pos1 + mtd * (im1 / (im1 + im2));
        //pos2 =  minus(pos2, multiply( mtd, (im1 / (im1 + im2)) )); // pos2 - mtd * (im2 / (im1 + im2));
        _ball1.pos = pos1;
        _ball2.pos = pos2;
// THIS IS FOR WALL COLLISIONS
//        if (((_ball1.p.X + _ball1.r) >= getWidth()) | ((_ball1.p.X - _ball1.r) <= 0))
//           _ball1.v.X = -1 * _ball1.v.X;
//
//        if (((_ball1.p.Y + _ball1.r) >= getHeight()) | ((_ball1.p.Y - _ball1.r) <= 0)) //canv.Height
//           _ball1.v.Y = -1 * _ball1.v.Y;
//
//        if (((_ball2.p.X + _ball2.r) >= getWidth()) | ((_ball2.p.X - _ball2.r) <= 0)) // canv.Width
//            _ball2.v.X = -1 * _ball2.v.X;
//
//         if (((_ball2.p.Y + _ball2.r) >= getHeight()) | ((_ball2.p.Y - _ball2.r) <= 0))
//            _ball2.v.Y = -1 * _ball2.v.Y;
   }
    /**** END COLLISION SUB-FUNCTIONS ****/
}
