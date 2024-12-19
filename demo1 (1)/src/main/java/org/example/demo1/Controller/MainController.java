package org.example.demo1.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo1.dao.CopiaDAO;
import org.example.demo1.model.Copia;
import org.example.demo1.model.Usuario;

/**
 * Controlador para la gestión de copias en la aplicación.
 */
public class MainController {
    @FXML private TableView<Copia> tablaCopias;
    @FXML private TableColumn<Copia, Long> colId;
    @FXML private TableColumn<Copia, String> colEstado;
    @FXML private TableColumn<Copia, Integer> colCantidad;
    @FXML private Button btnAnadirCopia;
    @FXML private Button btnEliminarCopia;
    @FXML private Button btnModificarCopia;

    private Usuario usuarioLogueado;
    private final CopiaDAO copiaDAO = new CopiaDAO();

    @FXML
    public void initialize() {
        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Cargar datos del usuario logueado
        cargarCopias();
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        cargarCopias();
    }

    private void cargarCopias() {
        tablaCopias.getItems().clear();
        if (usuarioLogueado != null) {
            tablaCopias.getItems().addAll(copiaDAO.findByUsuario(usuarioLogueado));
        }
    }

    @FXML
    public void onAnadirCopia() {
        // Mostrar formulario para añadir una copia
        Copia copia = FormularioCopiaController.mostrarFormulario(new Copia(), usuarioLogueado);
        if (copia != null) {
            copiaDAO.save(copia);
            tablaCopias.getItems().add(copia);
        }
    }

    @FXML
    public void onEliminarCopia() {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            copiaDAO.delete(seleccionada);
            tablaCopias.getItems().remove(seleccionada);
        } else {
            mostrarAlerta("Debe seleccionar una copia para eliminar.");
        }
    }

    @FXML
    public void onModificarCopia() {
        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Copia copiaModificada = FormularioCopiaController.mostrarFormulario(seleccionada, usuarioLogueado);
            if (copiaModificada != null) {
                copiaDAO.save(copiaModificada);
                cargarCopias();
            }
        } else {
            mostrarAlerta("Debe seleccionar una copia para modificar.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void onSalir(ActionEvent actionEvent) {
        System.out.println("Ha salido");
    }
}
