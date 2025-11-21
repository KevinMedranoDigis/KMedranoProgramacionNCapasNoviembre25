package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Semestre;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SemestreDAOImplementation implements ISemestre {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            result.Correct = jdbcTemplate.execute("{CALL GetAllSemestre(?) }", (CallableStatementCallback<Boolean>) callableStatement -> {
                    callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                    callableStatement.execute();
                    
                    ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                    result.Objects = new ArrayList<>();
                    
                    while(resultSet.next()){
                        Semestre semestre = new Semestre();
                        semestre.setIdSemestre(resultSet.getInt("IdSemestre"));
                        semestre.setNombre(resultSet.getString("Nombre"));
                        
                        result.Objects.add(semestre);
                        
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
