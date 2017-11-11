package chapter15;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
//import java.util.exception;

public class BallPane extends Pane {
	public class Vector
	{
	   private double X, Y;
	   public Vector(double x, double y) { X = x; Y = y; }// { _x = x; _y = y; }

//	   public double X;
//	   {
//	       get { return _x; }
//	       set { _x = value; }
//	   }

//	   public double Y;
//	   {
//	       get { return _y; }
//	       set { _y = value; }
//	   }
	   public double Length() {
		   return ((double)Math.sqrt(X * X + Y * Y)); 
	   }
//	   public double Length(double value) {
//	          if (value < 0) {
//	              throw new Exception("value is " + value + "; The magnitude of a Vector must be a positive value greater than 0");
//	              //Exception("value", value, 
//	              //"The magnitude of a Vector must be a positive value greater than 0");
//	          }
//	   }
	}
	
   public Vector plus(Vector v1, Vector v2) {
       return new Vector(v1.X + v2.X, v1.Y + v2.Y);
   }
   public Vector minus(Vector v1, Vector v2) {
       return new Vector(v1.X - v2.X, v1.Y - v2.Y);
   }
   public Vector multiply(Vector v1, double scale) {
       return new Vector(v1.X * scale, v1.Y * scale);
   }
   public Vector multiply( double scale, Vector v1) {
       return new Vector(v1.X * scale, v1.Y * scale);
   }
   public Vector divide(double scale, Vector v1) {
       return new Vector(scale / v1.X, scale / v1.Y);
   }
	   
  protected class Ball {
//	  // Position
//	  public class P {
//		  public double X;
//		  public double Y;
//	  }
//	  // Velocity
//	  public class V {
//		  public double X;
//		  public double Y;
//	  }
	  public Ball(double px, double py, double vx, double vy, 
			  double rad, double mass, Color color){ 
		  p = new Vector(px, py);
		  v = new Vector(vx, vy);
		  r = rad;
		  m = mass; 
		  circle = new Circle(p.X, p.Y, r);
		  circle.setFill(color);  // Set ball color
	  }
	  public Ball(Ball b){
		  p = new Vector(b.p.X, b.p.Y);
		  v = new Vector(b.v.X, b.v.Y);
		  r = b.r;
		  m = b.m; 
		  circle = new Circle(p.X, p.Y, r);
		  circle.setFill(b.circle.getFill());  // Set ball color
	  }
	  public Vector p;
	  public Vector v;
	  public double m; // mass
	  public double r; // radius;
	  public Circle circle;
  }
  
  
  
  private Timeline animation;
  public static final int X = 10;
  public static final int Y = 10;
  public static final int TotNumCircles = X*Y; // old: 18+4+10;
  private Ball[] balls; // = new Ball[TotNumCircles];
  
  public BallPane() {
	  balls = new Ball[TotNumCircles];
	  // collisions do not take into account rotations of the disks on the surface-- disks' energy is solely linear.
	  // multiball collisions do not work as idealized... as shown with this example.
//	  balls[0] = new Ball(0+40, 100+40, 1.0, 0.0, 		20, 20, Color.GREEN);
//	  balls[1] = new Ball(200+40, 100+40, -1.0, 0.0, 	20, 20, Color.RED);
//	  balls[2] = new Ball(100+40, 0+40, 0.0, 1.0, 		20, 20, Color.BLUE); 
//	  balls[3] = new Ball(100+40, 200+40, 0.0, -1.0, 	20, 20, Color.YELLOW);
	  
	  // old
//	  balls[0] = new Ball(20, 20, 1.0, 1.0, 	20, 10, Color.GREEN); //, pX, pY, vX, vY, radius, mass, color
//	  balls[1] = new Ball(60, 60, 0.5, 1.5, 	10, 50, Color.RED);
//	  balls[2] = new Ball(100, 100, -1, -1, 	10, 5, Color.BLUE); 
//	  
//	  balls[3] = new Ball(399, 399, 1.5, 0.5, 	20, 100, Color.YELLOW); // r = 200
//	  for(int i = 4; i < 18+4; i++){
//		  balls[i] = new Ball(100 + i*40, 300, 1.5, 0.5, 	20, i, Color.rgb((i*i) % 256, Math.abs(256 - i*8) % 256, (i*11) % 256));
//	  }
//	  for(int i = 18+4; i < 18+4+10; i++){
//		  balls[i] = new Ball(100 + (i - 18 - 4)*40, 400, 1.5, 0.5, 	20, i, Color.rgb((i*i) % 256, Math.abs(256 - i*8) % 256, (i*11) % 256));
//	  }
	  
	  for(int x = 0; x < X; x++){
		  for(int y = 0; y < Y; y++){
			  int i = Y * x + y;
			  balls[i] = new Ball(40 + 40*x, 40 + 40*y, 1, 1, 	i/X + 10, i + 1 >= 40 ? 20 : i+ 0.5, Color.rgb((i*i) % 256, Math.abs(256 - i*8) % 256, (i*11) % 256)); // (i > 100 ? 50 - i/4 : 100 - i) //  (y >= 5 ? y : Y/(y+1) )
		  }
	  }
	  ObservableList<Node> kids = getChildren();
	  for(Ball ball : balls){
		  kids.add(ball.circle);
	  }
	  //billiards[0] = new Ball(Color.GREEN, Ball.radius, Ball.radius, 1, 1);
	  //billiards[1] = new Ball(Color.RED, 4*Ball.radius, 4*Ball.radius, 0.1, 1);
	  //getChildren().addAll(billiards[0].circle, billiards[1].circle);
	  //getChildren().add(circle); // Place a ball into this pane
	  
	  
	  
    // Create an animation for moving the ball
    animation = new Timeline(
      new KeyFrame(Duration.millis(50), e -> moveBalls()));
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play(); // Start animation
  }

  public void play() {
    animation.play();
  }

  public void pause() {
    animation.pause();
  }

  public void increaseSpeed() {
    animation.setRate(animation.getRate() + 0.1);
  }

  public void decreaseSpeed() {
    animation.setRate(
      animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
  }

  public DoubleProperty rateProperty() {
    return animation.rateProperty();
  }
  
  protected void moveBalls() {
	  RenderFrame();
  }
  public boolean colliding3(Ball _ball1, Ball _ball2, Ball _ball3) {
	  double r1 = _ball1.r;
	  double r2 = _ball2.r;
	  double r3 = _ball3.r;
	  //double dist11 =  0.0;
	  double dist12 =  minus(_ball1.p, _ball2.p).Length();
	  double dist13 =  minus(_ball1.p, _ball3.p).Length();
	  //double dist22 =  0.0;
	  double dist23 =  minus(_ball2.p, _ball3.p).Length();
	  //double dist33 =  0.0;
	  
     if (dist12 > (r1 + r2) && dist13 > (r1 + r3) && dist23 > (r2 + r3))
     {
         // No solutions, the circles are too far apart.  
         return false;
      }
      else if (dist12 <= (r1 + r2) || dist13 <= (r1 + r3) || dist23 <= (r2 + r3))
      {
         // One circle contains the other.
         return true;
      }
      else if (((dist12 == 0) && (r1 == r2)) || ((dist13 == 0) && (r1 == r3)) || ((dist23 == 0) && (r2 == r3)))
      {
          // the circles coincide.
          return true;
      }
      else return true;
  }
  public double timestep = 1;
  public boolean collidingNew(Ball _ball1old, Ball _ball2old, Ball _ball1new, Ball _ball2new)
  {
	  boolean ball1isGtrY = _ball1old.p.Y > _ball2old.p.Y;
      boolean ball1isGtrX = _ball1old.p.X > _ball2old.p.X;
      Ball gtrY = ball1isGtrY ? _ball1new : _ball2new;
      Ball lsrY = !ball1isGtrY ? _ball1new : _ball2new;
      Ball gtrX = ball1isGtrX ? _ball1new : _ball2new;
      Ball lsrX = !ball1isGtrX ? _ball1new : _ball2new;
      // if the ball1 was higher before, and now its less than ball two, 
      // then its a possible collision if also ball1 was [more east] than ball2, and
      // now its more west. Simply put, if their PATHS HAVE CROSSED. 
      // IF PATHS' CROSSED, and they have collidedOLD(ball 1, ball2); then do the old collision revision w/o changing timestep
      // but have not collidedOLD(ball 1, ball2), then that means that we need to decrease the time step. 
      // suppose that 'a' moves, but does not collide with b-z. Then b moves, and collides with a. 
      // we need to allocate memory for all new positions, then finding them all, then 
      // detecting collisions in a triangular collision table, then finding the extrema para
      // FIND THE COLLISION THAT IS HAPPENING SOONEST/ORDER THE COLLISION TIMES. Delete all new positions 
      // generate so far. Reset the timestep to that collision value if there are no PATHS' CROSSED 
      // collision types. Else, increase the timestep according to the TIMESTEPINCREASEFUNCTION(timestep) for 
      // the next iteration. This way we can ensure that we calculate collisions of 2 balls in the correct
      // topological-then-chronological order. 
      // There should be a global, permanent shortest timestep to ensure accuracy, 
      // and to ensure that in situations where 4 balls on the x and y axis' collide at the 
      // same time. In this situation, we would want each ball to have a vector inverse of the one
      // before the collision. Thus a timestep is not necessarily the best way to solve it-- using
      // mathematics is. How could I do that? IDK, b/c there is always error in calculation.
      // Suppose we have 1024 threads, and 1024 particles. where is particle memory? 
      // we need to allocate shared memory for 1024 particle's positions and velocities at time t+1
      // a shared collision table. We need some extra threads available for iterations, sleeping 
      // until woken. 
      
      //if(gtrY.p.Y - gtrY.r <)
     double r1 = _ball1new.r;
     double r2 = _ball2new.r;
     double dist =  minus(_ball1new.p, _ball2new.p).Length(); // (_ball1.p - _ball2.p).Length;
     if (dist > (r1 + r2))
     {
         // No solutions, the circles are too far apart.  
         return false;
      }
      else if (dist <= r1 + r2)
      {
         // One circle contains the other.
         return true;
      }
      else if ((dist == 0) && (r1 == r2))
      {
          // the circles coincide.
          return true;
      }
      else return true;
  }
  
  public double G = 0.001;
  
  public void RenderFrameNew()
  {
     for (int j = 0; j < TotNumCircles; j++)
     {
        //  balls[j].p.X = balls[j].p.X + balls[j].v.X;
        //  balls[j].p.Y = balls[j].p.Y + +balls[j].v.Y;
    	Ball ballsJ = new Ball(balls[j]);
    	ballsJ.p = plus(balls[j].p, multiply(balls[j].v,timestep) ); //balls[j].p + balls[j].v;

        // balls[j].p = 
	    if ((ballsJ.p.X + ballsJ.r) >= getWidth()) // canv.Width
	    {
	    	ballsJ.v.X = -1 * balls[j].v.X;
	       ballsJ.p.X = getWidth() - ballsJ.r; // canv.Width
	    }
	    if ((ballsJ.p.X - ballsJ.r) <= 0)
	    {
	       ballsJ.v.X = -1 * ballsJ.v.X;
	       ballsJ.p.X = ballsJ.r;
	    }
	
	    if ((ballsJ.p.Y + ballsJ.r) >= getHeight()) // canv.Height
	    {
	    	ballsJ.v.Y = -1 * ballsJ.v.Y;
	        ballsJ.p.Y = getHeight() - ballsJ.r; // canv.Height
	    }
	    if ((ballsJ.p.Y - ballsJ.r) <= 0)
	    {
	    	ballsJ.v.Y = -1 * ballsJ.v.Y;
	    	ballsJ.p.Y = ballsJ.r;
	    }
	       // Canvas.SetLeft(balls[j].ellip, balls[j].p.X - balls[j].r);
	       // Canvas.SetTop(balls[j].ellip, balls[j].p.Y - balls[j].r);
        for (int k = j + 1; k < TotNumCircles; k++)
        {
        	Ball ballsK = new Ball(balls[j]);
        	
//        	if(G > 0.){
//        		Vector distance = minus(balls[j].p - balls
//        		double magnitude = 0.5*timestep*timestep*G*balls[j].m/();// multiply(balls[j].p, balls[j].p)
//        		Vector changeXY = divide(magnitude, balls[j].p);
//        		
//        	}
        	
        	
        	if (collidingNew(balls[j], balls[k], ballsJ, ballsK))
        	{
              // TextBoxCollision.Text = "Collision pair: " + j.ToString() + "-" + k.ToString();
        		System.out.println("Collision pair: " + j + "-" + k );
        		resolveCollision(balls[k], balls[j]);
        		//k = j + 1; // resolve post collision-collisions for very dense mappings?
        	}
        }
     }
     for(Ball ball : balls){
    	 ball.circle.setCenterX(ball.p.X);
    	 ball.circle.setCenterY(ball.p.Y);
    	 ball.circle.setRadius(ball.r);
     }
  }
  
  
  
  
  public boolean colliding(Ball _ball1, Ball _ball2)
  {
     double r1 = _ball1.r;
     double r2 = _ball2.r;
     double dist =  minus(_ball1.p, _ball2.p).Length(); // (_ball1.p - _ball2.p).Length;
     if (dist > (r1 + r2))
     {
         // No solutions, the circles are too far apart.  
         return false;
      }
      else if (dist <= r1 + r2)
      {
         // One circle contains the other.
         return true;
      }
      else if ((dist == 0) && (r1 == r2))
      {
          // the circles coincide.
          return true;
      }
      else return true;
  }
  
  public void resolveCollision(Ball _ball1, Ball _ball2)
  {
     double collisionision_angle = Math.atan2((_ball2.p.Y - _ball1.p.Y), (_ball2.p.X - _ball1.p.X));         
     double speed1 = _ball1.v.Length();
     double speed2 = _ball2.v.Length();

     double direction_1 = Math.atan2(_ball1.v.Y, _ball1.v.X);
     double direction_2 = Math.atan2(_ball2.v.Y, _ball2.v.X);
     double new_xspeed_1 = speed1 * Math.cos(direction_1 - collisionision_angle);
     double new_yspeed_1 = speed1 * Math.sin(direction_1 - collisionision_angle);
     double new_xspeed_2 = speed2 * Math.cos(direction_2 - collisionision_angle);
     double new_yspeed_2 = speed2 * Math.sin(direction_2 - collisionision_angle);

     double final_xspeed_1 = ((_ball1.m - _ball2.m) * new_xspeed_1 + (_ball2.m + _ball2.m) * new_xspeed_2) / (_ball1.m + _ball2.m);
     double final_xspeed_2 = ((_ball1.m + _ball1.m) * new_xspeed_1 + (_ball2.m - _ball1.m) * new_xspeed_2) / (_ball1.m + _ball2.m);
     double final_yspeed_1 = new_yspeed_1;
     double final_yspeed_2 = new_yspeed_2;

     double cosAngle = Math.cos(collisionision_angle);
     double sinAngle = Math.sin(collisionision_angle);
     _ball1.v.X = cosAngle * final_xspeed_1 - sinAngle * final_yspeed_1;
     _ball1.v.Y = sinAngle * final_xspeed_1 + cosAngle * final_yspeed_1;
     _ball2.v.X = cosAngle * final_xspeed_2 - sinAngle * final_yspeed_2;
     _ball2.v.Y = sinAngle * final_xspeed_2 + cosAngle * final_yspeed_2;

     Vector pos1 = new Vector(_ball1.p.X, _ball1.p.Y);
     Vector pos2 = new Vector(_ball2.p.X, _ball2.p.Y);

     // get the mtd
     Vector posDiff = minus(pos1, pos2); // pos1 - pos2;
     double d = posDiff.Length();

     // minimum translation distance to push balls apart after intersecting
     Vector mtd =  multiply(posDiff,  (((_ball1.r + _ball2.r) - d) / d) );

     // resolve intersection --
     // computing inverse mass quantities
     double im1 = 1 / _ball1.m;
     double im2 = 1 / _ball2.m;

     // push-pull them apart based off their mass
     pos1 =  plus(pos1, multiply( mtd, (im1 / (im1 + im2)) )); // pos1 + mtd * (im1 / (im1 + im2));
     pos2 =  minus(pos2, multiply( mtd, (im1 / (im1 + im2)) )); // pos2 - mtd * (im2 / (im1 + im2));
     _ball1.p = pos1;
     _ball2.p = pos2;

     if (((_ball1.p.X + _ball1.r) >= getWidth()) | ((_ball1.p.X - _ball1.r) <= 0))
        _ball1.v.X = -1 * _ball1.v.X;

     if (((_ball1.p.Y + _ball1.r) >= getHeight()) | ((_ball1.p.Y - _ball1.r) <= 0)) //canv.Height
        _ball1.v.Y = -1 * _ball1.v.Y;

     if (((_ball2.p.X + _ball2.r) >= getWidth()) | ((_ball2.p.X - _ball2.r) <= 0)) // canv.Width
         _ball2.v.X = -1 * _ball2.v.X;

      if (((_ball2.p.Y + _ball2.r) >= getHeight()) | ((_ball2.p.Y - _ball2.r) <= 0))
         _ball2.v.Y = -1 * _ball2.v.Y;
      //_ball1.r *= 0.95;
      //_ball2.r *= 0.95;
   }
  private int i = 0;
  public void RenderFrame()
  {
	 System.out.println("Iteration: " + i++);
     for (int j = 0; j < TotNumCircles; j++)
     {
        //  balls[j].p.X = balls[j].p.X + balls[j].v.X;
        //  balls[j].p.Y = balls[j].p.Y + +balls[j].v.Y;
        balls[j].p = plus(balls[j].p, balls[j].v); //balls[j].p + balls[j].v;

	    if ((balls[j].p.X + balls[j].r) >= getWidth()) // canv.Width
	    {
	       balls[j].v.X = -1 * balls[j].v.X;
	       balls[j].p.X = getWidth() - balls[j].r; // canv.Width
	    }
	    if ((balls[j].p.X - balls[j].r) <= 0)
	    {
	       balls[j].v.X = -1 * balls[j].v.X;
	       balls[j].p.X = balls[j].r;
	    }
	
	    if ((balls[j].p.Y + balls[j].r) >= getHeight()) // canv.Height
	    {
	    	balls[j].v.Y = -1 * balls[j].v.Y;
	        balls[j].p.Y = getHeight() - balls[j].r; // canv.Height
	    }
	    if ((balls[j].p.Y - balls[j].r) <= 0)
	    {
	    	balls[j].v.Y = -1 * balls[j].v.Y;
	    	balls[j].p.Y = balls[j].r;
	    }
	       // Canvas.SetLeft(balls[j].ellip, balls[j].p.X - balls[j].r);
	       // Canvas.SetTop(balls[j].ellip, balls[j].p.Y - balls[j].r);
        for (int k = j + 1; k < TotNumCircles; k++)
        {
        	if (colliding(balls[j], balls[k]))
        	{
              // TextBoxCollision.Text = "Collision pair: " + j.ToString() + "-" + k.ToString();
        		System.out.println("Collision pair: " + j + "-" + k );
        		resolveCollision(balls[k], balls[j]);
        		//k = j + 1; // resolve post collision-collisions for very dense mappings?
        	}
        }
     }
     for(Ball ball : balls){
    	 ball.circle.setCenterX(ball.p.X);
    	 ball.circle.setCenterY(ball.p.Y);
    	// ball.circle.setRadius(ball.r);
     }
  }
}