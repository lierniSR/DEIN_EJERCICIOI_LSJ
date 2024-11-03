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
 * Controlador de la ventana modal de modificar persona.
 *
 * @author Lierni
 * @version 1.0
 */
public class ModificarPersonaController {
    private Personas p;
    private String errores = "";
    private TableView<Personas> tabla;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField apellidoTextField;

    @FXML
    private TextField edadTextField;

    /**
     * Guarda los cambios realizados en la persona.
     * Verifica los datos ingresados y actualiza la tabla si son válidos.
     *
     * @param actionEvent El evento que desencadena esta acción.
     */
    public void guardarPersona(ActionEvent actionEvent) {
        verificacionPersona();
        if(errores.isEmpty()){
            String nombreUpdate = nombreTextField.getText();
            String apellidosUpdate = apellidoTextField.getText();
            Integer edadUpdate = Integer.parseInt(edadTextField.getText());
            if(p != null && !tabla.getItems().contains(new Personas(p.getId(), nombreUpdate,apellidosUpdate,edadUpdate))){
                boolean update = DaoPersonas.modificarPersona(p, nombreUpdate, apellidosUpdate, edadUpdate);
                if(update){
                    alertaModificarPersona();
                    //tabla.getSelectionModel().clearSelection();
                    ((Stage) nombreTextField.getScene().getWindow()).close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error al intentar modificar la persona a la Base de Datos.");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Persona duplicada");
                alert.setContentText("La persona no se puede modificar ya que existe una Persona identica en la tabla.");
                alert.showAndWait();
            }
        } else {
            alertaError();
        }
    }

    /**
     * Muestra una alerta de error con los errores de validación.
     */
    private void alertaError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(errores);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta informativa cuando se modifica una persona correctamente.
     */
    private void alertaModificarPersona() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Persona modificada");
        alert.setContentText("Persona modificada correctamente.");
        alert.showAndWait();
    }

    /**
     * Verifica los datos ingresados de la persona.
     * Comprueba que los campos obligatorios estén llenos y que la edad sea un número válido.
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
     * Cierra la ventana modal de modificación de persona.
     *
     * @param actionEvent El evento que desencadena esta acción.
     */
    public void cerrarModal(ActionEvent actionEvent) {
        tabla.getSelectionModel().clearSelection();
        ((Stage) nombreTextField.getScene().getWindow()).close();
    }

    /**
     * Establece la persona a modificar y inicializa los campos del formulario.
     *
     * @param p La persona a modificar.
     */
    public void setP(Personas p){
        this.p = p;
        inicializarCampos();
    }

    /**
     * Establece la referencia a la tabla de personas.
     *
     * @param tabla La tabla de personas.
     */
    public void setTabla(TableView<Personas> tabla){
        this.tabla = tabla;
    }

    /**
     * Inicializa los campos del formulario con los datos de la persona a modificar.
     */
    private void inicializarCampos() {
        if(p != null){
            nombreTextField.setText(p.getNombre());
            apellidoTextField.setText(p.getApellido());
            edadTextField.setText(String.valueOf(p.getEdad()));
        }
    }
}
