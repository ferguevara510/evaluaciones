package tc.web.evaluaciones.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tc.web.evaluaciones.database.ConnectionDB;

public class Pregunta {
    private int id;
    private String descripcion;
    private String tipo;

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static boolean registrarPregunta(String descripcion, String tipo, String folioExamen){
        boolean validacion = false;
        Connection conexion = ConnectionDB.createConnection();
        try {
        PreparedStatement pst;
        pst = conexion.prepareStatement("insert into pregunta(descripcion, tipo, folioExamen) values (?,?,?)");
            pst.setString(1, descripcion);
            pst.setString(2, tipo);
            pst.setString(3, folioExamen);
            pst.execute();
            pst.close();
            validacion = true;
        } catch(SQLException e){
            System.out.print(e);
        } finally{
            try{
                if(!conexion.isClosed()){
                    conexion.close();
                }
            } catch(SQLException ex){
                System.out.print(ex);
            }
        }
        return validacion;
    }

    public static List<Pregunta> buscarPreguntasDeExamen(String folioExamen){
        List<Pregunta> preguntas = new ArrayList<>();
        Pregunta pregunta = null;
        Connection connection = ConnectionDB.createConnection();
        try {
            PreparedStatement query = connection.prepareStatement("select * from pregunta where folioExamen = ?");
            query.setString(1, folioExamen);
            
            ResultSet result = query.executeQuery();
            while(result.next()){
                pregunta = new Pregunta();
                pregunta.setId(result.getInt("id"));
                pregunta.setDescripcion(result.getString("descripcion"));
                pregunta.setTipo(result.getString("tipo"));
                preguntas.add(pregunta);
            }
            result.close();
        } catch (SQLException ex) {
            
        }
        return preguntas;
    }

    public static Pregunta buscarPreguntaPorID(int id){
        Pregunta pregunta = null;
        Connection connection = ConnectionDB.createConnection();
        try {
            PreparedStatement query = connection.prepareStatement("select * from pregunta where id = ?");
            query.setInt(1, id);
            
            ResultSet result = query.executeQuery();
            if(result.next()){
                pregunta = new Pregunta();
                pregunta.setId(result.getInt("id"));
                pregunta.setDescripcion(result.getString("descripcion"));
                pregunta.setTipo(result.getString("tipo"));
            }
            result.close();
        } catch (SQLException ex) {
            
        }
        return pregunta;
    }

    public static boolean borrarPregunta(String id){
        boolean validacion = false;
        Connection connection = ConnectionDB.createConnection();
        try {
            PreparedStatement query = connection.prepareStatement("delete from pregunta where id = ?");
            query.setString(1, id);
            
            ResultSet result = query.executeQuery();
            result.close();
            validacion = true;
        } catch (SQLException ex) {
            
        }
        return validacion;
    }

    public static Pregunta preguntaActual(String folioExamen, Integer pagina){
        Pregunta pregunta = null;
        Connection connection = ConnectionDB.createConnection();
        try {
            PreparedStatement query = connection.prepareStatement("select * from pregunta where folioExamen = ? limit 1 offset ?");
            query.setString(1, folioExamen);
            query.setInt(2, pagina);
            
            ResultSet result = query.executeQuery();
            if(result.next()){
                pregunta = new Pregunta();
                pregunta.setId(result.getInt("id"));
                pregunta.setDescripcion(result.getString("descripcion"));
                pregunta.setTipo(result.getString("tipo"));
            }
            result.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------------------------------------------");
        }
        return pregunta;
    }

    public static boolean registrarRespuesta(String matricula, Integer folioExamen, Integer idPregunta, Integer idRespuesta){
        boolean validacion = false;
        Connection conexion = ConnectionDB.createConnection();
        try {
        PreparedStatement pst;
        pst = conexion.prepareStatement("insert into respuesta_pregunta(matricula, idPregunta, folioExamen, idRespuesta) values (?,?,?,?)");
            pst.setString(1, matricula);
            pst.setInt(2, idPregunta);
            pst.setInt(3, folioExamen);
            pst.setInt(4, idRespuesta);
            pst.execute();
            pst.close();
            validacion = true;
        } catch(SQLException e){
            System.out.print(e);
        } finally{
            try{
                if(!conexion.isClosed()){
                    conexion.close();
                }
            } catch(SQLException ex){
                System.out.print(ex);
            }
        }
        return validacion;
    }

}
