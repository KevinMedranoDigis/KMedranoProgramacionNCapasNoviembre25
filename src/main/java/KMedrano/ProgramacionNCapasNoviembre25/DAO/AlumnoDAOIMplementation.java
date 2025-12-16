package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Colonia;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Semestre;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Result GetByIdDirecciones(int IdAlumno) {
        Result result = new Result();

        jdbcTemplate.execute("{CALL AlumnoDireccionGetById(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

            callableStatement.setInt(1, IdAlumno);
            callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

            if (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setNombre(resultSet.getString("Nombre"));

                int idDireccion = resultSet.getInt("IdDireccion"); // null -> 0
               
                if (idDireccion != 0) {
                
                    alumno.Direcciones = new ArrayList<>();
                    
                    do {
                        Direccion direccion = new KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        alumno.Direcciones.add(direccion);
                    } while (resultSet.next());

                }

                result.Correct = true;
                result.Object = alumno;
            }

            return true;
        });

        return result;
    }

    @Override
    public Result GetById(int IdAlumno) {
        Result result = new Result();

        try {

            result.Correct = jdbcTemplate.execute("{CALL AlumnoGetById(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdAlumno);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                while (resultSet.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(resultSet.getInt("IdAlumno"));
                    alumno.setNombre(resultSet.getString("Nombre"));
                    alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    alumno.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    alumno.setEmail(resultSet.getString("Email"));
                    alumno.setPassword(resultSet.getString("Password"));
                    alumno.Semestre = new Semestre();

                    alumno.Semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                    alumno.Semestre.setNombre(resultSet.getString("NombreSemestre"));

                    result.Object = alumno;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Alumno> alumnos) {

        Result result = new Result();

        try {

            jdbcTemplate.batchUpdate("{CALL AddAlumno(?,?,?,?,?,?)}", alumnos, alumnos.size(), (CallableStatement, alumno) -> {

                CallableStatement.setString(1, alumno.getNombre());
                CallableStatement.setString(2, alumno.getApellidoPaterno());
                CallableStatement.setString(3, alumno.getApellidoMaterno());

                //no usar execute           
            });
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
        }

        return result;
    }

    @Override
    public Result GetAllDinamico(Alumno alumno) {

        Result result = new Result();

        try {

            jdbcTemplate.execute("{CALL BusquedaAlumnoDireccionGetAll (?,?,?) }", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, alumno.getNombre());
                callableStatement.setInt(2, alumno.Semestre.getIdSemestre());
                callableStatement.registerOutParameter(3, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(3);

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
                        Alumno alumnoBusqueda = ((Alumno) result.Objects.get(result.Objects.size() - 1));
                        alumnoBusqueda.Direcciones.add(direccion);

                    } else {

                        Alumno alumnoBusqueda = new Alumno();
                        alumnoBusqueda.setIdAlumno(IdAlumnoPorIngresar);
                        alumnoBusqueda.setNombre(resultSet.getString("Nombre"));
                        int IdDireccion = resultSet.getInt("IdDireccion");
                        if (IdDireccion != 0) {
                            alumnoBusqueda.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(IdDireccion);
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            alumnoBusqueda.Direcciones.add(direccion);
                        }

                        result.Objects.add(alumnoBusqueda);
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

}
