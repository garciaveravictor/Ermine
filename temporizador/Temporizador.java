package temporizador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Temporizador extends AnchorPane{


    // Atributos
    private static final Integer TIME = 60; //constante de tiempo
    private Timeline timeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(TIME);
    private BooleanProperty timeout; 

    
    //interfaz
    public Temporizador() {
        //crea el timeline
        timeSeconds.set(TIME);
        timeline = new Timeline();
        timeline.setCycleCount(1); //ciclos que realizará
        timeout = new SimpleBooleanProperty(false); //iniciamos el timeout a false cambiará a true cuando pase el tiempo
 
        //timeline.play();

    }

    public BooleanProperty getTimeout() {
        return timeout;
    }

    public void start(){
      
        
        KeyFrame kf_haswell = new KeyFrame(Duration.seconds(TIME + 1),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        timeline.stop();
                        timeout.set(true); //cuando pase el tiempo cambia a true

                    }
                }
        );

        timeline.getKeyFrames().add(kf_haswell);      
        timeline.play();

    }
    
    public void stop(){
        timeline.stop();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public IntegerProperty getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(IntegerProperty timeSeconds) {
        this.timeSeconds = timeSeconds;
    }
    
    
    
    

}
