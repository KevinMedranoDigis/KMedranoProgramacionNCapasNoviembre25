package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlumnoJPADAOImplementation implements IAlumnoJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAll() {
        //JPQL - para consulta de datos 
        TypedQuery<Alumno> queryAlumno = entityManager.createQuery("FROM Alumno", Alumno.class);
        List<Alumno> alumno = queryAlumno.getResultList();
        
        /*
            Lista ALumnoJPA - List AlumnoML
            Mapper
        */
        
        return null;
    }

}
