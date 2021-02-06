/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apphotel;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


/**
 * FXML Controller class
 *
 * @author Juanu
 */
public class SplashScreenController implements Initializable {

    @FXML
    private MediaView mv;
    MediaPlayer mp;
    Media me;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private AnchorPane ap;

    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("SplashScreen.fxml"));        
        File file = new File("src/apphotel/splash.mp4");
        String path = file.toURI().toString();
        me = new Media(path);
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mv.setSmooth(true);
        mp.setAutoPlay(true);

        DoubleProperty width = mv.fitWidthProperty();
        DoubleProperty height = mv.fitHeightProperty();
        
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
    }    
    
}
