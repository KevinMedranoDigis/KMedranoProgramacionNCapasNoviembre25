
package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.JPA.Direccion;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccion{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetById(int IdDireccion) {
        Result result = new Result();
        
        try {
            
            ModelMapper modelMapper = new ModelMapper();
            
            Direccion DireccionJPA = entityManager.find(Direccion.class, IdDireccion);
            
            KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion direccionML = modelMapper.map(DireccionJPA,  KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion.class);
            
            result.Object = direccionML;
            result.Correct = true;
            
            
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        
        return result;
    }
    
}
