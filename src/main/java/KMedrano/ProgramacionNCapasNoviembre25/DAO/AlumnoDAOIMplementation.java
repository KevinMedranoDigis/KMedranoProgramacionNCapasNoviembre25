package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Colonia;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // Lógica de base de datos
public class AlumnoDAOIMplementation implements IAlumno {

    @Autowired //Permite la inyeccion de dependencias
    private JdbcTemplate jdbcTemplate; // context

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {
            // -> función anonima, función autoinvocada, arrow function, lambda function
            result.Correct = jdbcTemplate.execute("{CALL AlumnoDireccionGetAll(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.Objects = new ArrayList<>();
                while (resultSet.next()) {
                    int IdAlumnoPorIngresar = resultSet.getInt("IdAlumno");

                    if (!result.Objects.isEmpty() && ((Alumno) result.Objects.get(result.Objects.size() - 1)).getIdAlumno() == IdAlumnoPorIngresar) {

                        Direccion direccion = new KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        Alumno alumno = ((Alumno) result.Objects.get(result.Objects.size() - 1));
                        alumno.Direcciones.add(direccion);

                    } else {

                        Alumno alumno = new Alumno();
                        alumno.setIdAlumno(IdAlumnoPorIngresar);
                        alumno.setNombre(resultSet.getString("Nombre"));
                        int IdDireccion = resultSet.getInt("IdDireccion");
                        if (IdDireccion != 0) {
                            alumno.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(IdDireccion);
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            alumno.Direcciones.add(direccion);
                        }

                        result.Objects.add(alumno);
                    }
                }
                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    public Result Add(Alumno alumno) {
        Result result = new Result();

        try {

            result.Correct = jdbcTemplate.execute("{CALL AlumnoDireccionAdd(?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setString(1, alumno.getNombre());
                callableStatement.setString(2, alumno.getApellidoPaterno());
                callableStatement.setString(3, alumno.getApellidoMaterno());
                callableStatement.setString(4, alumno.getEmail());
                callableStatement.setString(5, alumno.getPassword());
                callableStatement.setString(6, alumno.Direcciones.get(0).getCalle());
                callableStatement.setString(7, alumno.Direcciones.get(0).getNumeroInterior());
                callableStatement.setString(8, alumno.Direcciones.get(0).getNumeroExterior());
                callableStatement.setInt(9, alumno.Direcciones.get(0).Colonia.getIdColonia());
                
                callableStatement.executeUpdate();
                
                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
