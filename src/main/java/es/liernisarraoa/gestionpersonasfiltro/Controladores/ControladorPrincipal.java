package es.liernisarraoa.gestionpersonasfiltro.Controladores;

import es.liernisarraoa.gestionpersonasfiltro.Dao.DaoPersonas;
import es.liernisarraoa.gestionpersonasfiltro.GestionPersonas;
import es.liernisarraoa.gestionpersonasfiltro.Modelo.Personas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

/**
 * Controlador principal de la aplicación de gestión de personas.
 * Maneja la interfaz principal y las operaciones sobre la tabla de personas.
 * Todas las acciones se hace mediante una base de datos.
 *
 * @author Lierni
 * @version 1.0
 */
public class ControladorPrincipal implements Initializable {
    private Scene sceneAniadir;
    private Stage modalAniadir;
    private Scene sceneModificar;
    private Stage modalModificar;
    private Personas p;
    private ObservableList<Personas> items = FXCollections.observableArrayList();
    private ResourceBundle bundle;

    /** Tabla que muestra la lista de personas. */
    @FXML
    private TableView<Personas> tablaPersonas;

    /** Columna para mostrar el nombre de la persona. */
    @FXML
    private TableColumn<Personas, String> columnaNombre;

    /** Columna para mostrar el apellido de la persona. */
    @FXML
    private TableColumn<Personas, String> columnaApellido;

    /** Columna para mostrar la edad de la persona. */
    @FXML
    private TableColumn<Personas, Integer> columnaEdad;

    /** TexField para el nombre del filtrado */
    @FXML
    private TextField nombreFiltradoTextField;

    /** ImageView para la imagen antes del filtrado */
    @FXML
    private ImageView imagenContactos;

    /** Boton cambiar idioma ES */
    @FXML
    private Button btnES;

    /** Boton cambiar idioma EU */
    @FXML
    private Button btnEU;

    /** Boton cambiar idioma EN */
    @FXML
    private Button btnEN;

    /** Boton añadir persona */
    @FXML
    private Button btnAgregarPersona;

    /** Boton modificar persona */
    @FXML
    private Button btnModificarPersona;

    /** Boton eliminar persona*/
    @FXML
    private Button btnEliminarPersona;

    /** Label para el filtrado */
    @FXML
    private Label lblFiltro;

    /** Tooltip para buttonEU */
    @FXML
    private Tooltip tooltipEU;

    /** Tooltip para buttonES */
    @FXML
    private Tooltip tooltipES;

    /** Tooltip para buttonEN */
    @FXML
    private Tooltip tooltipEN;


    /**
     * Maneja el evento de agregar una nueva persona.
     * Abre una ventana modal para introducir los datos de la nueva persona.
     *
     * @param actionEvent El evento que desencadena la acción.
     * @throws Exception Si ocurre algún error al cargar la ventana modal.
     */
    public void agregarPersona(ActionEvent actionEvent) throws Exception {
        //Esto si el controlador necesita hacer algo en la ventana principal
        // Cargar el FXML de la ventana modal
        FXMLLoader loader = new FXMLLoader(GestionPersonas.class.getResource("aniadirPersona.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de la ventana modal
        AniadirPersonaController modalControlador = loader.getController();

        // Pasar el TableView al controlador de la ventana modal
        modalControlador.setTablaPersonas(this.tablaPersonas);

        // Crear y mostrar la ventana modal
        modalAniadir = new Stage();
        sceneAniadir = new Scene(root);
        modalAniadir.setScene(sceneAniadir);
        modalAniadir.initModality(Modality.APPLICATION_MODAL);
        modalAniadir.setTitle("Agregar Persona");
        modalAniadir.getIcons().add(new Image(String.valueOf(GestionPersonas.class.getResource("/Imagenes/agenda.png"))));
        modalAniadir.showAndWait();
        items = DaoPersonas.cargarListado();
        tablaPersonas.getItems().setAll(items);
    }

    /**
     * Maneja el evento de modificar una persona existente.
     * Abre una ventana modal para editar los datos de la persona seleccionada.
     *
     * @param actionEvent El evento que desencadena la acción.
     * @throws IOException Si ocurre algún error al cargar la ventana modal.
     */
    public void modificarPersona(ActionEvent actionEvent) throws IOException {
        //Esto si el controlador necesita hacer algo en la ventana principal
        // Cargar el FXML de la ventana modal
        FXMLLoader loader = new FXMLLoader(GestionPersonas.class.getResource("modificarPersona.fxml"));
        Parent root = loader.load();

        p = tablaPersonas.getSelectionModel().getSelectedItem();
        // Obtener el controlador de la ventana modal
        ModificarPersonaController modalControlador = loader.getController();

        // Pasar el TableView al controlador de la ventana modal
        modalControlador.setP(p);
        modalControlador.setTabla(tablaPersonas);

        // Crear y mostrar la ventana modal
        modalModificar = new Stage();
        sceneModificar = new Scene(root);
        modalModificar.setScene(sceneModificar);
        modalModificar.initModality(Modality.APPLICATION_MODAL);
        modalModificar.setTitle("Modificar persona");
        modalModificar.getIcons().add(new Image(String.valueOf(GestionPersonas.class.getResource("/Imagenes/agenda.png"))));
        modalModificar.showAndWait();
        items = DaoPersonas.cargarListado();
        tablaPersonas.getItems().setAll(items);
    }

    /**
     * Maneja el evento de eliminar una persona de la tabla.
     * Elimina la persona seleccionada y muestra una alerta de confirmación.
     *
     * @param actionEvent El evento que desencadena la acción.
     */
    public void eliminarPersona(ActionEvent actionEvent) {
        Personas personaEliminar = tablaPersonas.getSelectionModel().getSelectedItem();
        tablaPersonas.getSelectionModel().clearSelection();
        DaoPersonas.eliminarPersona(personaEliminar);
        items = DaoPersonas.cargarListado();
        tablaPersonas.getItems().setAll(items);
        alertaEliminar();
    }

    /**
     * Muestra una alerta informando que la persona ha sido eliminada.
     */
    private void alertaEliminar() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Persona eliminada");
        alert.setContentText("La persona seleccionada se ha eliminado.");
        alert.showAndWait();
    }


    /**
     * Maneja el evento de filtrar una persona de la tabla.
     * Filtra el nombre de la persona introducida.
     *
     * @param keyEvent El evento que desencadena la acción.
     */
    public void filtrarPorNombre(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            String nombreFiltrar = nombreFiltradoTextField.getText().trim();
            ObservableList<Personas> itemsFilter = FXCollections.observableArrayList();
            if(!nombreFiltrar.isEmpty()){
                for(Personas p : items){
                    if(p.getNombre().equalsIgnoreCase(nombreFiltrar)){
                        itemsFilter.add(p);
                    }
                }
                //Agregamos el observable list y limpiamos la tabla
                tablaPersonas.getItems().removeAll();
                tablaPersonas.setItems(itemsFilter);
            } else {
                tablaPersonas.getItems().removeAll();
                tablaPersonas.setItems(items);
            }
        }
    }
    /**
     * Inicializa el controlador.
     * Configura la tabla y sus columnas para mostrar la información de las personas.
     *
     * @param url La ubicación utilizada para resolver rutas relativas para el objeto raíz.
     * @param resourceBundle Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaPersonas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columnaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaNombre.prefWidthProperty().bind(tablaPersonas.widthProperty().multiply(0.4));
        columnaApellido.prefWidthProperty().bind(tablaPersonas.widthProperty().multiply(0.4));
        columnaEdad.prefWidthProperty().bind(tablaPersonas.widthProperty().multiply(0.2));
        items = DaoPersonas.cargarListado();
        tablaPersonas.getItems().addAll(items);
        Image imagen = new Image(String.valueOf(GestionPersonas.class.getResource("/Imagenes/contactos.jpeg")));
        imagenContactos.setImage(imagen);
        //Iconos botones
        ImageView imageview = new ImageView(new Image("file:C:\\DM2\\DEIN\\ProyectosFX\\DEIN_EJERCICIOI_LSJ\\src\\main\\resources\\Imagenes\\plus.png"));
        imageview.setFitWidth(20); // Ajusta el ancho de la imagen
        imageview.setFitHeight(20); // Ajusta la altura de la imagen
        imageview.setPreserveRatio(true); // Mantener la proporción de la imagen
        btnAgregarPersona.setGraphic(imageview);
        //btnModificar
        imageview = new ImageView(new Image("file:C:\\DM2\\DEIN\\ProyectosFX\\DEIN_EJERCICIOI_LSJ\\src\\main\\resources\\Imagenes\\modificar.png"));
        imageview.setFitWidth(20); // Ajusta el ancho de la imagen
        imageview.setFitHeight(20); // Ajusta la altura de la imagen
        imageview.setPreserveRatio(true); // Mantener la proporción de la imagen
        btnModificarPersona.setGraphic(imageview);
        //btnEliminar
        imageview = new ImageView(new Image("file:C:\\DM2\\DEIN\\ProyectosFX\\DEIN_EJERCICIOI_LSJ\\src\\main\\resources\\Imagenes\\eliminar.png"));
        imageview.setFitWidth(20); // Ajusta el ancho de la imagen
        imageview.setFitHeight(20); // Ajusta la altura de la imagen
        imageview.setPreserveRatio(true); // Mantener la proporción de la imagen
        btnEliminarPersona.setGraphic(imageview);
        //Idiommas
        btnEN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bundle = ResourceBundle.getBundle("messages_en");
                lblFiltro.setText(bundle.getString("texto.filtro"));
                columnaNombre.setText(bundle.getString("columna.nombre"));
                columnaApellido.setText(bundle.getString("columna.apellido"));
                columnaEdad.setText(bundle.getString("columna.edad"));
                btnAgregarPersona.setText(bundle.getString("boton.aniadir"));
                btnModificarPersona.setText(bundle.getString("boton.modificar"));
                btnEliminarPersona.setText(bundle.getString("boton.eliminar"));
                tooltipEN.setText(bundle.getString("tooltip.EN"));
                tooltipES.setText(bundle.getString("tooltip.ES"));
                tooltipEU.setText(bundle.getString("tooltip.EU"));
            }
        });
        btnES.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bundle = ResourceBundle.getBundle("messages_es");
                lblFiltro.setText(bundle.getString("texto.filtro"));
                columnaNombre.setText(bundle.getString("columna.nombre"));
                columnaApellido.setText(bundle.getString("columna.apellido"));
                columnaEdad.setText(bundle.getString("columna.edad"));
                btnAgregarPersona.setText(bundle.getString("boton.aniadir"));
                btnModificarPersona.setText(bundle.getString("boton.modificar"));
                btnEliminarPersona.setText(bundle.getString("boton.eliminar"));
                tooltipEN.setText(bundle.getString("tooltip.EN"));
                tooltipES.setText(bundle.getString("tooltip.ES"));
                tooltipEU.setText(bundle.getString("tooltip.EU"));
            }
        });
        btnEU.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bundle = ResourceBundle.getBundle("messages_eu");
                lblFiltro.setText(bundle.getString("texto.filtro"));
                columnaNombre.setText(bundle.getString("columna.nombre"));
                columnaApellido.setText(bundle.getString("columna.apellido"));
                columnaEdad.setText(bundle.getString("columna.edad"));
                btnAgregarPersona.setText(bundle.getString("boton.aniadir"));
                btnModificarPersona.setText(bundle.getString("boton.modificar"));
                btnEliminarPersona.setText(bundle.getString("boton.eliminar"));
                tooltipEN.setText(bundle.getString("tooltip.EN"));
                tooltipES.setText(bundle.getString("tooltip.ES"));
                tooltipEU.setText(bundle.getString("tooltip.EU"));
            }
        });
    }

    /**
     * Prepara el menu para cada fila de la tabla.
     * @return ContextMenu menu
     */
    private ContextMenu prepararMenu() {
        //Creamos un menu contextual y los elementos del menu
        ContextMenu menu = new ContextMenu();
        MenuItem modificar = new MenuItem("Modificar");
        //Escuchador
        modificar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    modificarPersonaMenu();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        MenuItem eliminar = new MenuItem("Eliminar");
        //Escuchador
        eliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                eliminarPersonaMenu();
            }
        });

        //Añadimos elementos del menu al menu contextual
        menu.getItems().addAll(modificar, eliminar);

        return menu;
    }

    /**
     * Modificar la persona seleccionada
     *
     */
    private void modificarPersonaMenu() throws IOException {
        //Esto si el controlador necesita hacer algo en la ventana principal
        // Cargar el FXML de la ventana modal
        FXMLLoader loader = new FXMLLoader(GestionPersonas.class.getResource("modificarPersona.fxml"));
        Parent root = loader.load();

        p = tablaPersonas.getSelectionModel().getSelectedItem();
        // Obtener el controlador de la ventana modal
        ModificarPersonaController modalControlador = loader.getController();

        // Pasar el TableView al controlador de la ventana modal
        modalControlador.setP(p);
        modalControlador.setTabla(tablaPersonas);

        // Crear y mostrar la ventana modal
        modalModificar = new Stage();
        sceneModificar = new Scene(root);
        modalModificar.setScene(sceneModificar);
        modalModificar.initModality(Modality.APPLICATION_MODAL);
        modalModificar.setTitle("Modificar persona");
        modalModificar.getIcons().add(new Image(String.valueOf(GestionPersonas.class.getResource("/Imagenes/agenda.png"))));
        modalModificar.showAndWait();
        items = DaoPersonas.cargarListado();
        tablaPersonas.getItems().setAll(items);
    }

    /**
     * Elimina la persona seleccionada
     *
     */
    private void eliminarPersonaMenu(){
        Personas personaEliminar = tablaPersonas.getSelectionModel().getSelectedItem();
        tablaPersonas.getSelectionModel().clearSelection();
        DaoPersonas.eliminarPersona(personaEliminar);
        items = DaoPersonas.cargarListado();
        tablaPersonas.getItems().setAll(items);
        alertaEliminar();
    }

    /**
     * Esto para que con el clic derecho se muestre by con el otro no se muestre
     *
     */
    public void alClicar(MouseEvent mouseEvent) {
        ContextMenu menu = prepararMenu();
        tablaPersonas.setContextMenu(menu);
        //Si se pulsa la derecha se muestra  si no, no se muestra
        if(mouseEvent.getButton() == MouseButton.SECONDARY){
            menu.show(tablaPersonas, mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }
}