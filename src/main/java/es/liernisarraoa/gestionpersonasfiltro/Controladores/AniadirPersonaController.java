package es.liernisarraoa.gestionpersonasfiltro.Controladores;

import es.liernisarraoa.gestionpersonasfiltro.Dao.DaoPersonas;
import es.liernisarraoa.gestionpersonasfiltro.Modelo.Personas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Controlador de la ventana modal de añadir persona.
 *
 * @author Lierni
 * @version 1.0
 */
public class AniadirPersonaController {
    /** Persona que se está agregando actualmente. */
    private static Personas personaAgregada = null;
    /** Cadena para almacenar los mensajes de error. */
    private String errores = "";
    /** Referencia a la tabla de personas en la ventana principal. */
    private TableView<Personas> tablaPersonas;

    /** Campo de texto para el nombre de la persona. */
    @FXML
    private TextField nombreTextField;

    /** Campo de texto para el apellido de la persona. */
    @FXML
    private TextField apellidoTextField;

    /** Campo de texto para la edad de la persona. */
    @FXML
    private TextField edadTextField;

    /**
     * Guarda la persona introducida en el formulario.
     * Verifica los datos, crea la persona y la añade a la tabla si es válida.
     * @param actionEvent El evento que desencadena esta acción.
     */
    public void guardarPersona(ActionEvent actionEvent) {
        verificacionPersona();
        if(errores.isEmpty()){
            Integer id = tablaPersonas.getItems().getLast().getId() + 1;
            personaAgregada = new Personas(id, nombreTextField.getText(), apellidoTextField.getText(), Integer.valueOf(edadTextField.getText()));
            if (!tablaPersonas.getItems().contains(personaAgregada) && personaAgregada != null) {
                boolean agregada = DaoPersonas.aniadirPersona(personaAgregada);
                if(agregada){
                    alertaAniadirPersona();
                    ((Stage) nombreTextField.getScene().getWindow()).close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error al intentar añadir la persona a la Base de Datos.");
                    alert.showAndWait();
                }
            } else {
                if(personaAgregada != null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Persona duplicada");
                    alert.setHeaderText(null);
                    alert.setContentText("La persona no se puede añadir ya que existe en la tabla.");
                    alert.showAndWait();
                }
            }
        } else {
            alertaError();
        }
    }

    /**
     * Muestra una alerta informando que la persona ha sido añadida correctamente.
     */
    private void alertaAniadirPersona() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Persona añadida");
        alert.setHeaderText(null);
        alert.setContentText("Persona añadida correctamente.");
        alert.showAndWait();
    }

    /**
     * Muestra una alerta con los errores encontrados durante la validación.
     */
    private void alertaError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errores);
        alert.showAndWait();
    }

    /**
     * Verifica que los datos introducidos sean válidos.
     * Almacena los mensajes de error en la variable 'errores'.
     */
    private void verificacionPersona() {
        errores = "";
        if (nombreTextField.getText().isEmpty()) {
            errores += "El campo nombre es obligatorio.\n";
        }
        if (apellidoTextField.getText().isEmpty()) {
            errores += "El campo apellidos es obligatorio.\n";
        }
        if (edadTextField.getText().isEmpty()) {
            errores += "El campo edad es obligatorio";
        } else {
            try {
                Integer edad = Integer.parseInt(edadTextField.getText());
            } catch (NumberFormatException e) {
                errores += "El campo edad tiene que ser númerico.\n";
            }
        }
    }

    /**
     * Cierra la ventana modal de añadir persona.
     * @param actionEvent El evento que desencadena esta acción.
     */
    public void cerrarModal(ActionEvent actionEvent) {
        personaAgregada = null;
        ((Stage) nombreTextField.getScene().getWindow()).close();
    }

    /**
     * Establece la referencia a la tabla de personas de la ventana principal.
     * @param tabla La tabla de personas a la que se añadirán las nuevas personas.
     */
    public void setTablaPersonas(TableView<Personas> tabla){
        this.tablaPersonas = tabla;
    }
}
