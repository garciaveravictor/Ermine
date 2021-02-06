/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apphotel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Profe2 , JuanV
 */
public class AppHotel extends Application {
    
    // Offsets. Controlan en punto desde el que se arrastra la ventana en movimiento.
    // 0 , 0 >> Esquina Superior Izquierda.
    private final double splash_xOffset = 240;
    private final double splash_yOffset = 240;
    private final double mainWin_xOffset = 400;
    private final double mainWin_yOffset = 300;
    
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoaderSplash=new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
        
        root = (Parent) fxmlLoaderSplash.load();
        stage.initStyle(StageStyle.TRANSPARENT);
        
        
        stage.initStyle(StageStyle.UNDECORATED);
        
        
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {                
                stage.setX(event.getScreenX() - splash_xOffset);
                stage.setY(event.getScreenY() - splash_yOffset);
            }
        });
        
        Timeline t1 = new Timeline();
        //t1.setCycleCount(1);
        t1.setCycleCount(Animation.INDEFINITE);
        KeyFrame kf_fruit = new KeyFrame(Duration.seconds(5),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        
                        try {
                                Stage stageMain = new Stage();
                                FXMLLoader fxmlLoaderMAIN=new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                                Parent root = (Parent) fxmlLoaderMAIN.load();

                                stageMain.initModality(Modality.APPLICATION_MODAL);
                                stageMain.initStyle(StageStyle.TRANSPARENT);
                                
                                stageMain.setScene(new Scene(root));
                                stageMain.show();

                                //hace que la ventana sea movible

                                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {                                        
                                        stageMain.setX(event.getScreenX() - mainWin_xOffset);
                                        stageMain.setY(event.getScreenY() - mainWin_yOffset);
                                    }
                                });
                                t1.stop();
                                stage.hide();
                            
                            
                        } catch (IOException ex) {
                            Logger.getLogger(AppHotel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                    }  
        });

        t1.getKeyFrames().add(kf_fruit);
        t1.play();       

        stage.setScene(new Scene(root));
        stage.show();
 
    }    

    public static void main(String[] args) {
        
        launch(args);
                
    }
    //FXMLLoader fxmlLoaderMAIN=new FXMLLoader(getClass().getResource("MainWindow.fxml"));

    
}
