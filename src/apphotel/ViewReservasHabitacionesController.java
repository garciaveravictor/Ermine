package apphotel;

import entidades.Cliente;
import entidades.Provincia;
import entidades.ReservaHab;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Sebastián Fernández
 *
 * @version 0.1
 *
 */
public class ViewReservasHabitacionesController implements Initializable {

    // Variables static
    private static final String VACIO = "";
    private boolean persona_existe = false;

    // Variables Clientes
    private Cliente cliente;
    private Provincia provincia;
    private ReservaHab reservaHab;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    // Variables Régimen
    // Alojamiento y desayuno
    public static final char AlojDes = 'A';

    //Media pensión
    public static final char MedPens = 'M';

    // Pensión completa
    public static final char PensComp = 'C';

    // Tipo de abitación
    public static final char DoubleUse = 'D';
    public static final char OneUse = 'O';

    @FXML
    private TextField tf_dni;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_direccion;
    @FXML
    private TextField tf_localidad;
    @FXML
    private ComboBox<String> cb_Provincia;
    @FXML
    private DatePicker dp_llegada;
    @FXML
    private DatePicker dp_salida;
    @FXML
    private Spinner<Integer> sp_numerohabitaciones;
    @FXML
    private ComboBox<String> cb_tipohabitacion;
    @FXML
    private CheckBox ckb_fumador;
    @FXML
    private RadioButton rb_alojamiento;
    @FXML
    private RadioButton rb_mediapension;
    @FXML
    private RadioButton rb_pensioncompleta;
    @FXML
    private TextField tf_Telefono;
    @FXML
    private ToggleGroup tg_RadioButton;

    // Llamamos a la clase Cliente
    public void setCliente() {

    }

    /**
     * Metodo Setter para para realizar operaciones de carga/descarga de datos.
     * @param provincia Requiere de un Objeto de Clase Provincia por parametro.
     */
    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        entityManagerFactory = Persistence.createEntityManagerFactory("AppHotelPU");
        entityManager = entityManagerFactory.createEntityManager();

        // Muestra los número en cuantas habitaciones
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        sp_numerohabitaciones.setValueFactory(spinnerValueFactory);

        // Muestra los valores del tipo de habitacion
        ObservableList<String> list = FXCollections.observableArrayList("Un uso", "Doble uso");
        cb_tipohabitacion.setItems(list);

        disableAll();

        /**
         * Listener del campo DNI
         */
        tf_dni.focusedProperty().addListener((ov, oldV, newV) -> {

            if (newV) {
                //focus onn
            } else {
                //focus off
                //busque a la persona

                if (validarDNI(tf_dni.getText())) {
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppHotelPU");
                    EntityManager em = emf.createEntityManager();

                    this.cliente = em.find(Cliente.class, tf_dni.getText().toUpperCase());

                    if (cliente != null) {
                        persona_existe = true;
                        tf_nombre.setText(cliente.getNombre());
                        tf_direccion.setText(cliente.getDireccion());
                        cb_Provincia.setValue(cliente.getProvincia().getNombre());
                        tf_localidad.setText(cliente.getProvincia().getLocalidad());
                        tf_Telefono.setText(cliente.getTelefono());

                        enableFieldsReserva();
                        enableFieldsCliente();
                        tf_dni.setEditable(false);
                        tf_nombre.setEditable(false);
                        tf_direccion.setEditable(false);
                        tf_localidad.setEditable(false);
                        cb_Provincia.setDisable(true);
                        tf_Telefono.setEditable(false);

                    } else {
                        cliente = new Cliente(tf_dni.getText());

                        enableFieldsCliente();
                        enableFieldsReserva();

                    }
                } else {
                    if (!tf_dni.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "-El DNI no es correcto\n-El formato debe ser: 12345678Z (la letra en mayúsculas)");
                        alert.setHeaderText("Error de sintáxis");
                        alert.showAndWait();

                    }
                }

            }

        });

        ObservableList<String> provincias = FXCollections.observableArrayList(
                "Alava", "Albacete", "Alicante", "Almería", "Asturias", "Avila", "Badajoz", "Barcelona", "Burgos", "Cáceres",
                "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara",
                "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra",
                "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona",
                "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"
        );

        cb_Provincia.setItems(provincias);

        cb_Provincia.setTooltip(new Tooltip());
        new CBAutoComplete<>(cb_Provincia);
    }

    /**
     * Este metodo se encarga de limpiar/vaciar los campos a rellenar.
     * @param event Evento producido al pulsar el boton limpiar.
     */
    @FXML
    private void onActionButtonLimpiar(ActionEvent event) {
        tf_dni.setText(VACIO);
        tf_nombre.setText(VACIO);
        tf_direccion.setText(VACIO);
        tf_localidad.setText(VACIO);
        tf_Telefono.setText(VACIO);
        cb_Provincia.getSelectionModel().select(null);
        cb_tipohabitacion.setValue(VACIO);
        rb_alojamiento.isDisabled();
        rb_mediapension.isDisabled();
        rb_pensioncompleta.isDisabled();
        ckb_fumador.isDisabled();
        disableAll();

    }

    /**
     * Este metodo Valida se encarga de aceptar y guardar los datos en la BD.
     * @param event Evento producido al pulsar el boton Aceptar.
     */
    @FXML
    private void onActionButtonAceptar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppHotelPU");
        EntityManager em = emf.createEntityManager();

        if (!validarDatos()) {
            return;
        }

        //recogemos los datos
        recogeProvincia();
        recogerCliente();
        recogerReservaHabitacion();
        
        // Bloque de Prueba Alert con Distintas Señales.
        // ==================
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "");
        String fumador;
        String tipReg = null;
        alert2.setHeaderText("¿Desea guardar los datos?");
        //recogemos el tipo de evento
        provincia = recogeProvincia();
        cliente = recogerCliente();
        reservaHab = recogerReservaHabitacion();

        String datos = cliente.toString() + cliente.getProvincia().toString() + reservaHab.toString();

        alert2.setContentText(datos);

        Optional<ButtonType> result = alert2.showAndWait();

        if (!result.isPresent()) {
            // Alerta Cerrada. Ningun boton pulsado.
            // Interpretado como cierre con CANCEL.
            return;
        } else if (result.get() == ButtonType.OK) {

            // Si la persona existe solo hacemos la reserva de la habitación
            if (persona_existe) {
                em.getTransaction().begin();
                em.merge(recogerReservaHabitacion());
                em.getTransaction().commit();
            } // En caso de nueva persona
            else {
                // Introduce los datos de provincia
                em.getTransaction().begin();
                em.merge(recogeProvincia());
                em.getTransaction().commit();

                // Introduce los datos de cliente
                em.getTransaction().begin();
                em.merge(recogerCliente());
                em.getTransaction().commit();

                // Introduce los datos de la reserva de habitación
                em.getTransaction().begin();
                em.merge(recogerReservaHabitacion());
                em.getTransaction().commit();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Datos guardado correcatmente");
            alert.showAndWait();

            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        } else if (result.get() == ButtonType.CANCEL) {
            // Cancelar Accion.
            return;
        }
    }

    /**
     * Este metodo cierra la ventana actual de reservas.
     * @param event Evento producido al pulsar el boton cancelar.
     */
    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    // Validar DNI
    /**
     * Este metodo valida el DNI introducido y que cumple los estandares.
     * @param dni  Cadena String con el DNI completo.
     * @return true El DNI es valido.
     */
    private boolean validarDNI(String dni) {

        String dniTemp = "";

        char[] letraDni = {
            'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
        };

        if (dni.length() != 9 || !Character.isLetter(dni.charAt(8))) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) {
                return false;
            }
            dniTemp += dni.charAt(i);

        }

        int dniNumber = Integer.parseInt(dniTemp);

        if (Character.toUpperCase(dni.charAt(8)) == letraDni[dniNumber % 23]) {
            return true;
        } else {
            return false;
        }

    }

    // Validamos el nombre que se introduzca
    /**
     * Este metodo se encarga de validar el nombre introducido.
     * @param mNombre Cadena String con nombre.
     * @return true El Nombre es valido.
     */
    private boolean validarNombre(String mNombre) {
        boolean checkStatus = false;

        /*Verificamos que no sea null*/
        if (mNombre != null) {
            /* 1ª Condición: que la letra inicial sea mayúscula*/
            boolean isFirstUpper = Character.isUpperCase(mNombre.charAt(0));

            /* 2ª Condición: que el tamaño sea >= 3 y <= 20*/
            int stringSize = mNombre.length();
            boolean isValidSize = (stringSize >= 3 && stringSize <= 20);


            /* Verificamos que las tres condiciones son verdaderas*/
            checkStatus = ((isFirstUpper == true) && (isFirstUpper && isValidSize));
        }
        /*Devolvemos el estado de la validación*/
        return checkStatus;
    }

    // Valida que lo que ha sido introducido sea solo número
    /**
     * Este metodo comprueba que todos los elementos de un string son numeros.
     * @param cadena Cadena de Numeros.
     * @return true Todos los elementos de la cadena son numeros.
     */
    private boolean validarSoloNumerosEnteros(String cadena) {

        for (int i = 0; i < cadena.length(); i++) {

            if (!Character.isDigit(cadena.charAt(i))) {
                return false;
            }

        }
        return true;
    }

    /**
     * Método que valida si lo el parametro de entrada son solo letras
     *
     * @param cadena parametro a analizar
     *
     * @return true si son solo letras, false si contiene otros caracteres
     */
    private boolean esSoloLetras(String cadena) {

        for (int i = 0; i < cadena.length(); i++) {
            if (!Character.isLetter(cadena.charAt(i)) && cadena.charAt(i) != ' ') {
                return false;
            }
        }

        //Terminado el bucle sin que se haya retornado false, es que todos los caracteres son letras
        return true;
    }

    /**
     * Método que habilita los campos del cliente
     */
    private void enableFieldsCliente() {
        tf_dni.setDisable(false);
        tf_nombre.setDisable(false);
        tf_direccion.setDisable(false);
        tf_localidad.setDisable(false);
        tf_nombre.setDisable(false);
        cb_Provincia.setDisable(false);
        tf_Telefono.setDisable(false);

        tf_dni.setEditable(true);
        tf_nombre.setEditable(true);
        tf_direccion.setEditable(true);
        tf_localidad.setEditable(true);
        cb_Provincia.setEditable(false);
        tf_Telefono.setEditable(true);
    }

    /**
     * Habilita los campos especificos referidos a los datos de Reserva.
     */
    private void enableFieldsReserva() {
        dp_llegada.setDisable(false);
        dp_salida.setDisable(false);
        ckb_fumador.setDisable(false);
        sp_numerohabitaciones.setDisable(false);
        cb_tipohabitacion.setDisable(false);
        rb_alojamiento.setDisable(false);
        rb_mediapension.setDisable(false);
        rb_pensioncompleta.setDisable(false);
        dp_llegada.getEditor().setEditable(false);
        dp_salida.getEditor().setEditable(false);
    }

    /**
     * Deshabilita todos los campos
     */
    private void disableAll() {
        tf_dni.setEditable(true);
        tf_nombre.setDisable(true);
        tf_direccion.setDisable(true);
        tf_localidad.setDisable(true);
        cb_Provincia.setDisable(true);
        tf_Telefono.setDisable(true);
        dp_llegada.setDisable(true);
        dp_salida.setDisable(true);
        sp_numerohabitaciones.setDisable(true);
        cb_tipohabitacion.setDisable(true);
        ckb_fumador.setDisable(true);
        rb_alojamiento.setDisable(true);
        rb_mediapension.setDisable(true);
        rb_pensioncompleta.setDisable(true);

    }

    /**
     * Recoge todos los datos y los guarda en una Provincia nueva
     *
     * @return La provincia con todos sus datos
     */
    private Provincia recogeProvincia() {
        Provincia provinciaNueva = new Provincia();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppHotelPU");
        EntityManager em = emf.createEntityManager();

        provinciaNueva.setNombre(cb_Provincia.getSelectionModel().getSelectedItem().toString());
        provinciaNueva.setLocalidad(tf_localidad.getText());

        return provinciaNueva;
    }

    /**
     * Recoge todos los datos y los guarda en un Cliente nuevo
     *
     * @return El cliente con todos sus datos
     */
    private Cliente recogerCliente() {
        Cliente clienteNuevo = new Cliente();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppHotelPU");
        EntityManager em = emf.createEntityManager();

        clienteNuevo.setDni(tf_dni.getText());
        clienteNuevo.setDireccion(tf_direccion.getText());
        clienteNuevo.setNombre(tf_nombre.getText());
        clienteNuevo.setProvincia(recogeProvincia());
        clienteNuevo.setTelefono(tf_Telefono.getText());

        return clienteNuevo;

    }

    /**
     * Recoge los datos y los guarda en nuevo ReservaHabitación
     *
     * @return La reserva con todos sus datos
     */
    private ReservaHab recogerReservaHabitacion() {

        ReservaHab reservaHabNuevo = new ReservaHab();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppHotelPU");
        EntityManager em = emf.createEntityManager();

        // Guardamos el DNI en la tabla reserva Habitacion
        reservaHabNuevo.setDni(recogerCliente());

        // Añadimos la fecha de llegada
        LocalDate localDateLlegada = dp_llegada.getValue();
        ZonedDateTime zonedDateTimeLlegada = localDateLlegada.atStartOfDay(ZoneId.systemDefault());
        Instant instantLlegada = zonedDateTimeLlegada.toInstant();
        Date dateLlegada = Date.from(instantLlegada);
        reservaHabNuevo.setFechaLlegada(dateLlegada);

        // Añadimos la fecha de salida
        LocalDate localDateSalida = dp_salida.getValue();
        ZonedDateTime zonedDateTimeSalida
                = localDateSalida.atStartOfDay(ZoneId.systemDefault());
        Instant instantSalida = zonedDateTimeSalida.toInstant();
        Date dateSalida = Date.from(instantSalida);
        reservaHabNuevo.setFechaSalida(dateSalida);

        // Añade el número de habitaciones
        short num;
        num = (short) Integer.parseInt(sp_numerohabitaciones.getValue().toString());
        reservaHabNuevo.setNumHabitaciones(num);

        // Comprueba el tipo de habitación que necesita
        if (!cb_tipohabitacion.getSelectionModel().isEmpty()) {
            if (cb_tipohabitacion.getValue().equals("Doble uso")) {
                reservaHabNuevo.setTipoHabitaciones(DoubleUse);
            } else if (cb_tipohabitacion.getValue().equals("Un uso")) {
                reservaHabNuevo.setTipoHabitaciones(OneUse);
            }
        }

        // Añade si el campo fumador ha sido seleccionado
        reservaHabNuevo.setFumador(ckb_fumador.isSelected());

        // Recogemos el valor de tipo de regimen
        RadioButton seleccionado = (RadioButton) tg_RadioButton.getSelectedToggle();
        String tempTipoReserva = seleccionado.getText().toUpperCase();
        char tipoEvento = tempTipoReserva.charAt(0);
        reservaHabNuevo.setRegimen(tipoEvento);

        return reservaHabNuevo;
    }

    /**
     * Este metodo valida todos los datos de la Reserva para su posterior guardado en la BD.
     * @return true Todos Datos son validos.
     */
    private boolean validarDatos() {
        Calendar cal = Calendar.getInstance();

        // Valida el DNI
        if (!validarDNI(tf_dni.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "DNI incorrecto\nFormato debe ser : 12345678Z (Letra en mayúscula)");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida el nombre
        else if (tf_nombre.getText().isEmpty() || !esSoloLetras(tf_nombre.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "El campo de nombre no pueste estar vacío ni contener números");
            alert.setHeaderText("Error de sintáxis");
            alert.showAndWait();
            return false;
        } // Valida la direccion,  Valida que el tamaño de direccion no supere los 50 carácteres 
        else if (tf_direccion.getText().isEmpty() || tf_direccion.getText().length() > 50) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campo dirección no puede estar vacío");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } else if (tf_localidad.getText().isEmpty() || !esSoloLetras(tf_localidad.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campo localidad no puede estar vacío");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida la provincia
        else if (cb_Provincia.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campo provincia no puede estar vacío");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida número de teléfono que solo sea número, Valida que el teléfono tenga una longitud de 9
        else if (!validarSoloNumerosEnteros(tf_Telefono.getText()) || tf_Telefono.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Campo teléfono solo admite 9 números");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida la fecha de llegada no sea nula, Valida que la fecha de llegada no sea anterior a la actual
        else if (dp_llegada.getValue() == null || dp_llegada.getValue().compareTo(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar una fecha de llegada, la cual no debe de ser anterior a la actual");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida la fecha de salida no sea nula, Valida que la fecha de salida no sea anteior a la actual, Valida que la fehca de salida no sea anterior a la fecha de llegada
        else if (dp_salida.getValue() == null || dp_salida.getValue().isBefore(dp_llegada.getValue()) || dp_salida.getValue().isBefore(cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar una fecha de salida, la cual no puede ser anterior a la de llegada");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida el numero de habitaciones
        else if (sp_numerohabitaciones.getValue().equals(0)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Indique el número de habitaciones que necesita");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida el tipo de habitación seleccionado
        else if (cb_tipohabitacion.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Indique el tipo de habitación que usted necesita");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        } // Valida que se ha seleccionado almenos un tipo de régimen
        else if (!rb_alojamiento.isSelected() && !rb_mediapension.isSelected() && !rb_pensioncompleta.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Indique un tipo de regimén");
            alert.setHeaderText("Error de sintaxis");
            alert.showAndWait();
            return false;
        }

        return true;
    }

}
