package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
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
         result.Correct  =   jdbcTemplate.execute("{CALL AlumnoDireccionGetAll(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.Objects = new ArrayList<>();

                while (resultSet.next()) {
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(resultSet.getInt("IdAlumno"));
                    alumno.setNombre(resultSet.getString("Nombre"));
                    alumno.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    
                    
                    result.Objects.add(alumno);
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
