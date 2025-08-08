/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASES;

/**
 *
 * @author Armando
 */
public class Estudiante {
    private int id;
    private String nombre;
    private String apellidos;
    private int idSexo;       // FK hacia la tabla sexo
    private String nombreSexo;  // Para mostrar el texto convenientemente

    // Constructor vacío
    public Estudiante() {}

    // Constructor con parámetros (sin id, para insertar)
    public Estudiante(String nombre, String apellidos, int idSexo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.idSexo = idSexo;
    }

    // Constructor con id, para editar o consultar
    public Estudiante(int id, String nombre, String apellidos, int idSexo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.idSexo = idSexo;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public int getIdSexo() { return idSexo; }
    public void setIdSexo(int idSexo) { this.idSexo = idSexo; }

    public String getNombreSexo() { return nombreSexo; }
    public void setNombreSexo(String nombreSexo) { this.nombreSexo = nombreSexo; }
}
