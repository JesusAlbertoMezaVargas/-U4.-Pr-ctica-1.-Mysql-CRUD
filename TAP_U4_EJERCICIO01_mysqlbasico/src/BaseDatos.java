import com.mysql.cj.conf.PropertyKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDatos {
  Connection conexion;
  Statement transaccion;
  ResultSet cursor;
  
  String cadenaConexion = "jdbc:mysql://localhost:3306/persona?zeroDateTimeBehavior=CONVERT_TO_NULL";
  String usuario = "root";
  String pass = "root";
 public BaseDatos(){
try{
    Class. forName ("com.mysql.cj.jdbc.Driver");
    conexion = DriverManager.getConnection (cadenaConexion, usuario, pass);
    transaccion=conexion.createStatement();
}catch(SQLException e){
        }catch(ClassNotFoundException e){
}
 }
 
 public boolean insertar(Persona p) {
        String SQL_Insertar = "INSERT INTO `Persona` (`ID`, `NOMBRE`, `DOMICILIO`, `TELEFONO`) VALUES (NULL, '%NOM%', '%DOM%', '%TEL%');";

        SQL_Insertar = SQL_Insertar.replace("%NOM%", p.nombre);
        SQL_Insertar = SQL_Insertar.replace("%DOM%", p.domicilio);
        SQL_Insertar = SQL_Insertar.replace("%TEL%", p.telefono);

        try {
            transaccion.execute(SQL_Insertar);
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

 
 public ArrayList<Persona> mostrarTodos() {
        ArrayList<Persona> datos = new ArrayList<>();
        String SQL_consulta = "SELECT * FROM `Persona`";

        try {
            //RESULTSET = variable que maneja las tuplas resultantes
            cursor = transaccion.executeQuery(SQL_consulta);

            if (cursor.next()) {
                do {
                    Persona p = new Persona(
                            cursor.getInt(1), // ID
                            cursor.getString(2), // Nombre
                            cursor.getString(3), // Domicilio
                            cursor.getString(4) // Telefono
                    );
                    datos.add(p);
                } while (cursor.next());
            }

        } catch (SQLException ex) {
            
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }


    public Persona obtenerPorID(String IDaBuscar) {
        String SQL_consulta = "SELECT * FROM `Persona` WHERE `ID`=" + IDaBuscar;

        try {
            //RESULTSET = variable que maneja las tuplas resultantes
            cursor = transaccion.executeQuery(SQL_consulta);

            if (cursor.next()) {
                Persona p = new Persona(
                        cursor.getInt(1), // ID
                        cursor.getString(2), // Nombre
                        cursor.getString(3), // Domicilio
                        cursor.getString(4) // Telefono
                );
                return p;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Persona(-1, " ", "", "");
    }
 public boolean eliminar(String IDaEliminar) {
        String SQL_eliminar = "DELETE FROM `Persona` WHERE `ID`=" + IDaEliminar;

        try {
            transaccion.execute(SQL_eliminar);
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

  public boolean  actualizar(Persona p){
   String SQL_actualizar = "UPDATE `persona`SET `NOMBRE`=`%NOM%`,`DOMICILIO`=`%DOM%`,"
                         +"`TELEFONO`=`%TEL%` WHERE `ID`="+p.id;
    SQL_actualizar = SQL_actualizar.replace("%NOM%", p.nombre);
    SQL_actualizar = SQL_actualizar.replace("%DOM%", p.domicilio);
    SQL_actualizar = SQL_actualizar.replace("%TEL%", p.telefono);
    try{
        transaccion.executeUpdate(SQL_actualizar);
    }catch(SQLException ex){
        return false;
    }
    return true;
 }
}




