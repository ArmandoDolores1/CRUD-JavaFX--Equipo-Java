/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASES;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.scene.control.Alert;

/**
 *
 * @author Armando
 */
public class Conexion {
    Connection conectar = null;
    
    String usuario ="root";
    String contraseña="21012006";
    String bd = "bdusuarios";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contraseña);
            showAlert("Mensaje", "Se conecto a la base de datos");
        } catch (Exception e) {
            showAlert("Mensaje", "No se conecto a la base de datos, error: " + e.toString());
        }
        return conectar;
    }
    private void showAlert(String title, String content){
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void cerrarConexion(){
        try{
            if(conectar != null && !conectar.isClosed()){
                conectar.close();
                showAlert("Mensaje","Conexion cerrada");
            }
        }catch (Exception e){
            showAlert("Mensaje","Error al cerrar la conexion" + e.toString());
            
        }
    }
}
