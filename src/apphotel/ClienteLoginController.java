/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apphotel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Marcos
 */
public class ClienteLoginController implements Initializable {

    // Esta clase pretende añadir la posibilidad de que el usuario pueda ver
    // desde la base de datos las reservas e informacion respecta a el.
    
    // Update: Esta APP no se planea destinar a usuarios finales sino a gestores.
    
    @FXML
    private AnchorPane clienteWindow;
    @FXML
    private Label lb_user;
    @FXML
    private Label lb_pass;
    @FXML
    private TextField tf_user;
    @FXML
    private PasswordField pf_pass;
    @FXML
    private Button bt_iniciarsesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onAction_bt_iniciarsesion(ActionEvent event) {
        
    }
    
}
