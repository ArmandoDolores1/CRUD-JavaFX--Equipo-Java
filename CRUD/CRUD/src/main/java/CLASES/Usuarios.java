/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASES;

import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Armando
 */
public class Usuarios {
    public void mostrarSexoCombo(ComboBox<String> comboSexo){
        CLASES.Conexion objetoConexion = new CLASES.Conexion();
        
        comboSexo.getItems().clear();
        
        comboSexo.setValue("Seleccione sexo");
        
        String sql ="select * from sexo;";
        
        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {    
                int idSexo = rs.getInt("id");
                
                String nombreSexo = rs.getString("sexo");
                
                comboSexo.getItems().add(nombreSexo);
                comboSexo.getProperties().put(nombreSexo, idSexo);
                
            }
        } catch (Exception e) {
            
            showAlert("ERROR", "Error al mostrar sexos: " + e.toString());
        }
             
        finally{
            objetoConexion.cerrarConexion();
        }
    }
    
        private void showAlert(String title, String content){
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    
}
}
