/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apphotel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.eclipse.persistence.internal.databaseaccess.Platform;

/**
 * FXML Controller class
 *
 * @author Juanu
 */
public class MainWindowController implements Initializable {
    
    @FXML
    private MenuItem mi_habitaciones;
    @FXML
    private MenuItem mi_habana;
    @FXML
    private Button bt_x;
    @FXML
    private Button bt_minimizar;
    
    // Offsets. Controlan en punto desde el que se arrastra la ventana en movimiento.
    // 0 , 0 >> Esquina Superior Izquierda.
    private double xOffset = 400;
    private double yOffset = 300;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        
    }   
    

    /**
     * 
     * @param event Evento producido al seleccionar ReservasHabitaciones del MenuItem.
     * 
     */
    @FXML
    private void OnAction_mi_Habitaciones(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("ViewReservasHabitaciones.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Reservas ");
        stage.setScene(new Scene(root));
        stage.show();
        
        //hace que la ventana sea movible        
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    
    /**
     * 
     * @param event Evento producido al seleccionar Salon Habana del MenuItem.
     * 
     */
    @FXML
    private void OnAction_mi_habana(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("ViewHabana.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root));
        stage.show();
        
        //hace que la ventana sea movible        
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    
/*
    @FXML
    private void OnAction_mi_habana(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("ViewHabana.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Salón Habana ");
        stage.setScene(new Scene(root));
        stage.show();
        
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        
        
        
    }
*/

    /**
     * 
     * @param event Evento producido al pulsar el icono de cerrar ventana 'X'.
     */
    @FXML
    private void onActon_bt_x(ActionEvent event) {
        System.exit(0);
    }

    /**
     * 
     * @param event Evento producido al pulsar el icono de minimizar ventana '_'.
     */
    @FXML
    private void onAction_bt_minimizar(ActionEvent event) {
         ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }
    
    
    
    
    
}
