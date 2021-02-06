package apphotel;

import entidades.Cliente;
import entidades.Provincia;
import entidades.ReservaSalon;
import java.net.URL;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.ScrollEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import temporizador.Temporizador;


/**
 * FXML Controller class
 *
 * @author Juanu
 *
 * @version 0.1
 * 
 */
public class ViewHabanaController implements Initializable, EventHandler<Event>{
    
    @FXML
    private TextField tf_dni;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_direccion;
    @FXML
    private TextField tf_telefono;
    @FXML
    private RadioButton rb_banquete;
    @FXML
    private ToggleGroup tg_tipoEvento;
    @FXML
    private RadioButton rb_jornada;
    @FXML
    private RadioButton rb_congreso;
    @FXML
    private TextField tf_numPersonas;
    @FXML
    private ComboBox<String> cb_tipoCocina;
    @FXML
    private CheckBox cb_necesitasHabitaciones;
    @FXML
    private TextField tf_cuantasHabitaciones;
    @FXML
    private DatePicker dp_fechaEvento;
    @FXML
    private Button bt_limpiar;
    @FXML
    private Button bt_aceptar;
    @FXML
    private Button bt_cancelar;
    
    //booleano para ver si la persona existe
    private boolean persona_existe = false;
    //private Cliente cliente;
    
    private static final String VACIO = null;
    @FXML
    private TextField tf_numDias;
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppHotelPU");
    private EntityManager em = emf.createEntityManager();
    private Cliente cliente;
    @FXML
    private TextField tf_localidad;
    @FXML
    private ComboBox cb_provincia;
    
    private ReservaSalon salon;
    private Provincia provincia;
    
    private  Temporizador temp;
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        
        disableAll();
        /**
         *Listener de el campo dni
         */
        tf_dni.focusedProperty().addListener((ov, oldV, newV) ->{
            
            
            
            if (newV) {
                //focus onn
            }else{
                //focus off
                //comprueba que el dni sea correcto
                
                //añadimos el temporizador que luego de un minuto hace timeout y "caduca" la sesión
                temp = new Temporizador();
                temporizador();
                
                
                if (validarDNI(tf_dni.getText()) ) {
                    
                    //busque a la persona

                    
                    this.cliente = em.find(Cliente.class, tf_dni.getText());
                    
                    //si encuentra a la persona coge los datos de la BD
                    if (cliente != null) {
                        
                        persona_existe = true;
                        tf_dni.setEditable(false);
                        tf_nombre.setText(cliente.getNombre());
                        tf_direccion.setText(cliente.getDireccion());
                        tf_telefono.setText(cliente.getTelefono());
                        cb_provincia.setValue(cliente.getProvincia().getNombre());
                        tf_localidad.setText(cliente.getProvincia().getLocalidad());
                        
                        
                        
                        enableFieldsReserva();
                        enableFieldsCliente();
                        tf_nombre.setEditable(false);
                        tf_direccion.setEditable(false);
                        tf_localidad.setEditable(false);
                        cb_provincia.setEditable(false);
                        cb_provincia.setDisable(true);
                        tf_telefono.setEditable(false);
                        
                        
                        
                        //si no encuentra al cliente habliita los campos para rellenarlo
                    }else{
                        cliente = new Cliente(tf_dni.getText());
                        
                        enableFieldsCliente();
                        enableFieldsReserva();
                        
                    }
                }else{
                    if (!tf_dni.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "-El DNI no es correcto o el formato es inadecuado Ej:12345678Z (la letra en mayúsculas)");
                        alert.setHeaderText("Error de sintáxis");
                        alert.showAndWait();
                        
                    }
                    
                }
                
            }
            

            
            
        });
        
        /**
         * Listener del ToggleGroup
         */
        
        tg_tipoEvento.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,Toggle old_toggle, Toggle new_toggle) {
                
                
                
                if (tg_tipoEvento.getSelectedToggle() == rb_banquete) {
                    
                    disableCleanOpcionales();
                    cb_tipoCocina.getSelectionModel().select(0);
                    cb_tipoCocina.setDisable(false);
                    rb_congreso.setDisable(true);
                    rb_jornada.setDisable(true);
                    
                }
                
                if (tg_tipoEvento.getSelectedToggle() == rb_jornada) {
                    disableCleanOpcionales();
                    cb_tipoCocina.getSelectionModel().select(0);
                    rb_banquete.setDisable(true);
                    rb_congreso.setDisable(true);
                }
                
                if (tg_tipoEvento.getSelectedToggle() == rb_congreso) {
                    disableCleanOpcionales();
                    rb_banquete.setDisable(true);
                    rb_jornada.setDisable(true);
                    cb_tipoCocina.getSelectionModel().select(0);
                    
                    //los campos bindeados si intento deshabilitarlos y ya lo estaban, generan error
                    
                    cb_necesitasHabitaciones.setDisable(false);
                    
                    tf_cuantasHabitaciones.setDisable(false);
                    
                    tf_numDias.setDisable(false);
                    
                    tf_cuantasHabitaciones.disableProperty().bind(cb_necesitasHabitaciones.selectedProperty().not());
                    
                }
                
            }
        });
        
        //cargo los datos en los combobox
        ObservableList<String> provincias = FXCollections.observableArrayList(
                "Alava","Albacete","Alicante","Almería","Asturias","Avila","Badajoz","Barcelona","Burgos","Cáceres",
                "Cádiz","Cantabria","Castellón","Ciudad Real","Córdoba","La Coruña","Cuenca","Gerona","Granada","Guadalajara",
                "Guipúzcoa","Huelva","Huesca","Islas Baleares","Jaén","León","Lérida","Lugo","Madrid","Málaga","Murcia","Navarra",
                "Orense","Palencia","Las Palmas","Pontevedra","La Rioja","Salamanca","Segovia","Sevilla","Soria","Tarragona",
                "Santa Cruz de Tenerife","Teruel","Toledo","Valencia","Valladolid","Vizcaya","Zamora","Zaragoza"
        );
        
        cb_provincia.setItems(provincias);
        
        
        
        ObservableList<String> menu = FXCollections.observableArrayList(
                "Buffet","Vegetariano","Menú a la Carta","Cita con el Chef","No Precisa"
        );
        
        cb_tipoCocina.setItems(menu);
        cb_tipoCocina.getSelectionModel().select(0);
        
        cb_provincia.setTooltip(new Tooltip());
        new CBAutoComplete<>(cb_provincia);
        
        
        
        
        
    }
    
    
    /**
     * Implementación del boton limpiar
     * @param event el evento generado al pulsar el boton limpiar
     */
    @FXML
    private void onAction_bt_limpiar(ActionEvent event) {
        
        tf_dni.setText(VACIO);
        tf_nombre.setText(VACIO);
        tf_direccion.setText(VACIO);
        tf_telefono.setText(VACIO);
        
        tf_numPersonas.setText(VACIO);
        tf_cuantasHabitaciones.setText("1");
        tf_numDias.setText("1");
        tf_localidad.setText(VACIO);
        cb_provincia.getSelectionModel().clearSelection();
        
        
        rb_banquete.setSelected(false);
        rb_congreso.setSelected(false);
        rb_jornada.setSelected(false);
        
        disableAll();
        bt_aceptar.setDisable(false);
        temporizador();
        
        
    }
    
    /**
     * Implementación del boton aceptar
     * @param event el evento generado al pulsar el boton aceptar
     */
    @FXML
    private void onAction_bt_aceptar(ActionEvent event) {
        
        
        
        //emf = Persistence.createEntityManagerFactory("AppHotelPU");
        //em = emf.createEntityManager();
        
        //si no valida, no continua la funcion
        if (!validar()) {
            return;
        }
        
        //recogemos los datos
        recogeProvincia();
        recogerCliente();
        recogerReservaHabana();
        
        // BLoque de Prueba Alert con Distintas Señales.
        // ==================
        
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "");
        alert2.setHeaderText("¿Desea guardar los datos?");
        //recogemos el tipo de evento
        provincia = recogeProvincia();
        cliente = recogerCliente();
        salon = recogerReservaHabana();
        
        String datos = cliente.toString() + cliente.getProvincia().toString() + salon.toString();
        
        alert2.setContentText(datos);
        
        Optional<ButtonType> result = alert2.showAndWait();
        
        if (!result.isPresent()){
            // Alerta Cerrada. Ningun boton pulsado.
            // Interpretado como cierre con CANCEL.
            return;
        }else if (result.get() == ButtonType.OK){
            //si la persona existe solo cargamos la reserva de habitación
            if (persona_existe) {
                em.getTransaction().begin();
                em.merge(salon);
                em.getTransaction().commit();
                
            }else{
                //si es una nueva persona
                //cargamos provincia
                em.getTransaction().begin();
                em.merge(provincia);
                em.getTransaction().commit();
                //cliente
                em.getTransaction().begin();
                em.merge(cliente);
                em.getTransaction().commit();
                //y reserva
                em.getTransaction().begin();
                em.merge(salon);
                em.getTransaction().commit();
            }
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Datos guardados correctamente");
            alert.setHeaderText(VACIO);
            alert.showAndWait();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            
            
        }else if (result.get() == ButtonType.CANCEL){
            // Cancelar Accion.
            return;
        }
        // =========================
        
        
        
    }
    
    /**
     * Implementación del boton cancelar
     * @param event el evento generado al pulsar el boton cancelar
     */
    @FXML
    private void onAction_bt_cancelar(ActionEvent event) {
        temp.stop(); //detiene el temporizador
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
    
    
    /**
     * método que  habilita los campos del cliente
     *
     */
    private void enableFieldsCliente(){
        tf_nombre.setDisable(false);
        tf_direccion.setDisable(false);
        tf_localidad.setDisable(false);
        cb_provincia.setDisable(false);
        tf_telefono.setDisable(false);
        
        tf_nombre.setEditable(true);
        tf_direccion.setEditable(true);
        tf_localidad.setEditable(true);
        tf_telefono.setEditable(true);
    }
    
    
    /**
     * método que  habilita los campos de la reserva
     */
    private void enableFieldsReserva(){
        
        rb_banquete.setDisable(false);
        rb_jornada.setDisable(false);
        rb_congreso.setDisable(false);
        tf_numPersonas.setDisable(false);
        
        dp_fechaEvento.getEditor().setEditable(false);
        dp_fechaEvento.setDisable(false);
        tf_numDias.setDisable(false);
        
        
    }
    
    
    /**
     * inhabilita y limpia los campos interactivos del formulario
     */
    private void disableCleanOpcionales(){
        
        cb_tipoCocina.setDisable(true);
        cb_necesitasHabitaciones.setDisable(true);
        if (!tf_cuantasHabitaciones.isDisabled()) {
            tf_cuantasHabitaciones.setDisable(true);
        }
        
        tf_numDias.setDisable(true);
        
        cb_tipoCocina.setValue(null);
        cb_necesitasHabitaciones.selectedProperty().set(false);
        
        tf_cuantasHabitaciones.setText("1");
        
        tf_numDias.setText("1");
        
    }
    
    /**
     * deshabilita todos los campos
     */
    private void disableAll(){
        
        tf_dni.setEditable(true);
        tf_nombre.setDisable(true);
        
        tf_direccion.setDisable(true);
        tf_telefono.setDisable(true);
        tf_localidad.setDisable(true);
        cb_provincia.setDisable(true);
        
        rb_banquete.setDisable(true);
        rb_jornada.setDisable(true);
        rb_congreso.setDisable(true);
        tf_numPersonas.setDisable(true);
        cb_tipoCocina.setDisable(true);
        cb_necesitasHabitaciones.setDisable(true);
        tf_cuantasHabitaciones.setDisable(true);
        dp_fechaEvento.setDisable(true);
        tf_numDias.setDisable(true);
        
    }
    
    /**
     * Valida los campos uno a uno
     * @return si son todos correctos
     */
    private boolean validar(){
        Calendar cal = Calendar.getInstance();
        //comprueba DNI
        if (!validarDNI(tf_dni.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "El DNI no es correcto\no el formato debe ser: 12345678Z (la letra en mayúsculas)");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
        }
        //comprueba nombre
        if (tf_nombre.getText().isEmpty() || !esSoloLetras(tf_nombre.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "El campo de nombre no pueste estar vacío ni contener números");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
        }
        
        //comprueba telefono
        if (!validarSoloNumerosEnteros(tf_telefono.getText()) || tf_telefono.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "El campo Teléfono sólamente admite 9 números");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
        }
        
        // Comprobar CB_Provincia
        if (cb_provincia.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campo provincia no puede estar vacío");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        }
        
        //comprueba el numero de personas
        //TODO UNIFICAR ESTOS DOS
        if ( tf_numPersonas.getText().isEmpty() ||!validarSoloNumerosEnteros(tf_numPersonas.getText()) || !validarNumPersonas(Integer.parseInt(tf_numPersonas.getText())) ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "-El campo NUMERO DE PERSONAS sólamente admite números\n-Numero de personas fuera de rango");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
        }
        
        if (tf_localidad.getText().isEmpty() || !esSoloLetras(tf_localidad.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campo localidad no puede estar vacío\nSolo contiene letras");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        }
        
        //comprueba el numero de personas
        /*
        if (!validarNumPersonas(Integer.parseInt(tf_numPersonas.getText()))) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Numero de personas fuera de rango");
        alert.setHeaderText("Error de sintáxis");
        alert.showAndWait();
        return false;
        }
        */
        
        //comprueba habitaciones
        if (tf_cuantasHabitaciones.getText().isEmpty() ||!validarSoloNumerosEnteros(tf_cuantasHabitaciones.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "El campo NUMERO DE HABITACIONES sólamente admite números");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
        }
        
        //comprueba numero de dias
        if (!validarSoloNumerosEnteros(tf_numDias.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "El campo NUMERO DE DIAS sólamente admite números");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
        }
        
        //comprueba fecha
        if (dp_fechaEvento.getValue() == null || dp_fechaEvento.getValue().compareTo(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) < 0) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "-Debe indicar una fecha válida.\n-No puede ser anterior a la fecha actual");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
            
        }
        
        return true;
    }
    
    /**
     * Valida el DNI
     * @param dni:  la cadena de entrada que vamos a analizar
     * @return si el dni es correcto
     */
    private boolean validarDNI(String dni){
        
        
        String dniTemp = "";
        
        char[] letraDni = {
            'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',  'X',  'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
        };
        
        if(dni.length() != 9 || !Character.isLetter(dni.charAt(8))){
            return false;
        }
        
        if (dni.length() != 9 || !Character.isLetter(dni.charAt(8))) {
            return false;
        }
        
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                return false;
            }
            dniTemp += dni.charAt(i);
            
        }
        
        if (!Character.isUpperCase(dni.charAt(8))) {
            return false;
            
        }
        
        
        
        int dniNumber = Integer.parseInt(dniTemp);
        
        if (Character.toUpperCase(dni.charAt(8)) == letraDni[dniNumber % 23]) {
            return true;
        }else
            return false;
        
    }
    
    
    /**
     * método que valida si son números enteros
     * @return true si son numeros enteros, false si no lo son
     * @param cadena La cadena que vamos a analizar
     */
    private boolean validarSoloNumerosEnteros(String cadena){
        
        for (int i = 0; i < cadena.length(); i++) {
            
            if (!Character.isDigit(cadena.charAt(i))) {
                return false;
            }
            
        }
        return true;
    }
    
    
    /**
     * método que valida la cantidad de personas
     * @return true si dentro de rango, false si fuera de rango
     * @param cantidad el numro que vamos a comprobar si esta en rango
     */
    private boolean validarNumPersonas(int cantidad){
        
        if(rb_banquete.isSelected()){
            
            if(cantidad < 1 || cantidad >100) {
                return false;
            }
            
        }else{
            
            if(cantidad < 1 || cantidad >50) {
                return false;
            }
        }
        
        return true;
    }
    
    
    
    /**
     * Recoge todos los datos y  los guarda en un Cliente nuevo
     * @return el cliente con todos sus datos
     */
    private Cliente recogerCliente(){
        Cliente clienteNuevo = new Cliente();
             
        
        clienteNuevo.setDni(tf_dni.getText());
        clienteNuevo.setDireccion(tf_direccion.getText());
        clienteNuevo.setNombre(tf_nombre.getText());
        clienteNuevo.setTelefono(tf_telefono.getText());
        
        
        clienteNuevo.setProvincia(recogeProvincia());
        
        return clienteNuevo;
        
    }
    
    
    /**
     * Recoge todos los datos y  los guarda en una Provincia nueva
     * @return la provincia con todos sus datos
     */
    private Provincia recogeProvincia() {
        
        provincia = new Provincia();
        
        //
        if (cb_provincia.getSelectionModel().getSelectedItem().toString() != null) {
            provincia.setNombre(cb_provincia.getSelectionModel().getSelectedItem().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Provincia no ha sido introducida");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
        }
        if (tf_localidad.getText() != null) {
            provincia.setLocalidad(tf_localidad.getText());
        } else {
            
        }
        
        return  provincia;
    }
    
    
    /**
     * Recoge los datos y los guarda en nuevo ReservaSalon
     * @return la la reserva con todos sus datos
     */
    private ReservaSalon recogerReservaHabana(){
        
        
        salon = new ReservaSalon();
        salon.setDni(recogerCliente());
        
        //recogemos el tipo de evento
        RadioButton seleccionado = (RadioButton)tg_tipoEvento.getSelectedToggle();
        String tempTipoReserva = seleccionado.getText().toUpperCase();
        char tipoEvento = tempTipoReserva.charAt(0);
        salon.setTipoEvento(tipoEvento);
        
        
        //recogemos menú
        //comprobar si está vacio
        String tempMenu = cb_tipoCocina.getSelectionModel().getSelectedItem().toString().toUpperCase();
        char menu = tempMenu.charAt(0);
        salon.setTipoCocina(menu);
        
        //recogemos numero de personas
        if (tf_numPersonas.getText().isEmpty()) {
            salon.setNumPersona((short)0);
            
        }else
            salon.setNumPersona((short) Integer.parseInt(tf_numPersonas.getText()));
        
        //recogemos el cliente
        
        salon.setDni(recogerCliente());
        
        
        
        
        // recogemos la fecha del evento
        if (dp_fechaEvento.getValue() != null) {
            try {
                LocalDate localDate = dp_fechaEvento.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date date = Date.from(instant);
                salon.setFechaEvento(date);
            } catch (DateTimeException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar una fecha de llegada");
                alert.showAndWait();
                dp_fechaEvento.requestFocus();
            }
            
        }
        
        //recogemos el necesita habitacion y cuantas habitaciones
        
        if (cb_necesitasHabitaciones.isSelected()) {
            salon.setNecesitaHabitacion(cb_necesitasHabitaciones.isSelected());
            if (Integer.parseInt(tf_numPersonas.getText()) < 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "La cantidad de habitaciones no puede ser menos de 1");
                alert.setHeaderText(null);
                alert.showAndWait();
                tf_numPersonas.requestFocus();
            }else
                salon.setCuantasHab((short)Integer.parseInt(tf_numPersonas.getText()));
            
        }
        
        //recogemos numero de dias
        
        salon.setNumDias((short)Integer.parseInt(tf_numDias.getText()));
        
        
        return salon;
        
    }
    
    
    //metodo de pruebas
    @FXML
    private void onScroll_bt_limpiar(ScrollEvent event) {
        
        //validar();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "¿Qué miras curiosín?");
        alert.setHeaderText(null);
        alert.showAndWait();
        
        
    }
    
    /**
     * método que valida si lo el parametro de entrada son solo letras
     * @return true si son solo letras, false si contiene otros caracteres
     * @param cadena parametro a analizar
     */
    private boolean esSoloLetras(String cadena) {
        
        for (int i = 0; i < cadena.length(); i++) {
            if(!Character.isLetter(cadena.charAt(i)) && cadena.charAt(i) != ' ') {
                return false;
            }
        }
        
        //Terminado el bucle sin que se haya retornado false, es que todos los caracteres son letras
        return true;
    }
    
    @Override
    public void handle(Event event) {
        
        System.out.println("han pasado x segundos");
        
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Este metodo se encarga de crear el temporizador que luego de 1 minuto reinica el formulario
     * no recibe parametros ni tiene salida
     * se basa principalmente en el manejo del evento de BolleanProperty timeout
     */
    private void temporizador(){
           
        //la propiedad donde nos indicará si ha pasado el tiempo o no
        BooleanProperty timeout = temp.getTimeout();
        
        Alert sesion = new Alert(Alert.AlertType.ERROR, "");
        sesion.setHeaderText("Sesión Caducada");
        
        timeout.addListener(e ->{
            
            disableAll();
            bt_aceptar.setDisable(true);
            
            sesion.show();
   
        });
        
        temp.start();
        
    }
  
}
