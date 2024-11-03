package es.liernisarraoa.gestionpersonasfiltro;

import es.liernisarraoa.gestionpersonasfiltro.Controladores.ControladorPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Clase principal de la aplicación de Gestión de Personas.
 * Esta clase extiende de Application y es el punto de entrada de la aplicación JavaFX.
 *
 * @author Lierni
 * @version 1.0
 */
public class GestionPersonas extends Application {
    /**
     * Método que inicia la aplicación JavaFX.
     * Carga el archivo FXML, configura la escena y muestra la ventana principal.
     *
     * @param stage El escenario principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(GestionPersonas.class.getResource("gestionDePersonas.fxml"));

        // Crea la escena con el contenido del FXML y establece sus dimensiones
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Configura el título de la ventana
        stage.setTitle("Gestion de personas");

        // Establece el icono de la aplicación
        stage.getIcons().add(new Image(String.valueOf(GestionPersonas.class.getResource("/Imagenes/agenda.png"))));

        // Asigna la escena al escenario
        stage.setScene(scene);

        // Muestra la ventana
        stage.show();
    }

    /**
     * Método principal que lanza la aplicación JavaFX.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        launch();
    }
}