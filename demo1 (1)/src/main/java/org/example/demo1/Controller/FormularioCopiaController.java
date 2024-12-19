package org.example.demo1.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.demo1.HelloApplication;
import org.example.demo1.model.Copia;
import org.example.demo1.model.Usuario;

import java.io.IOException;

public class FormularioCopiaController {
    @FXML
    private TextField txtEstado;
    @FXML private TextField txtCantidad;

    private static Copia copiaActual;
    private static Stage stage;
    private static boolean guardado;
    public static Copia mostrarFormulario(Copia copia, Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("formularioCopia.fxml"));
            Parent root = loader.load();

            FormularioCopiaController controller = loader.getController();
            controller.setCopia(copia);

            stage = new Stage();
            stage.setTitle("Formulario Copia");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            return guardado ? copiaActual : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setCopia(Copia copia) {
        copiaActual = copia;
        txtEstado.setText(copia.getEstado());
        txtCantidad.setText(String.valueOf(copia.getCantidad()));
    }

    @FXML
    public void onGuardar() {
        copiaActual.setEstado(txtEstado.getText());
        copiaActual.setCantidad(Integer.parseInt(txtCantidad.getText()));
        guardado = true;
        stage.close();
    }

    @FXML
    public void onCancelar() {
        guardado = false;
        stage.close();
    }
}
