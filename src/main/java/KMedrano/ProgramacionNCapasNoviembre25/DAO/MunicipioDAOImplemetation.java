
package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplemetation implements IMunicipio{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetMunicipioByEstado(int IdEstado) {
        Result result = new Result();
        
        
        return result;
    }
    
    
}
