package chapter15;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class BounceBallControl extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    BallPane ballPane = new BallPane(); // Create a ball pane
    // Pause and resume animation
    ballPane.setOnMousePressed(e -> ballPane.pause());
    ballPane.setOnMouseReleased(e -> ballPane.play());

    // Increase and decrease animation   
    ballPane.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.UP) {
        ballPane.increaseSpeed();
      } 
      else if (e.getCode() == KeyCode.DOWN) {
        ballPane.decreaseSpeed();
      }
    });
      
      
//      BallPane ballPane2 = new BallPane(Color.RED, 40, 40, 1, 1); // Create a ball pane
//      // Pause and resume animation
//      ballPane2.setOnMousePressed(e -> ballPane2.pause());
//      ballPane2.setOnMouseReleased(e -> ballPane2.play());

//      // Increase and decrease animation   
//      ballPane2.setOnKeyPressed(e -> {
//        if (e.getCode() == KeyCode.UP) {
//        	ballPane2.increaseSpeed();
//        } 
//        else if (e.getCode() == KeyCode.DOWN) {
//        	ballPane2.decreaseSpeed();
//        }
//      });

//    // Create a scene and place it in the stage
//    Pane g = new Pane(); //250.0, 150.0);
//    g.resize(250.0, 150.0);
//    g.getChildren().addAll(ballPane);
//    
    Scene scene = new Scene(ballPane, 450, 500);
    primaryStage.setTitle("BounceBallControl"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    // Must request focus after the primary stage is displayed
    ballPane.requestFocus();
    //ballPane2.requestFocus();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
