
package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Pais;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPais{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll() {
        
        Result result = new Result();
        
        try{
            jdbcTemplate.execute("{CALL GetAllPais(?)}", (CallableStatementCallback<Boolean>) callableStament ->{
                
                callableStament.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStament.execute();
                
                ResultSet resultSet =  (ResultSet) callableStament.getObject(1);
                
                result.Objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));
                    
                    result.Objects.add(pais);
                    
                }
                
                
                return true;
            });


        }
        catch(Exception ex){
            result.Correct = false;
           result.ErrorMessage = ex.getLocalizedMessage();
           result.ex = ex;
        }
        
        
        
        return result;
        
    }
    
}
