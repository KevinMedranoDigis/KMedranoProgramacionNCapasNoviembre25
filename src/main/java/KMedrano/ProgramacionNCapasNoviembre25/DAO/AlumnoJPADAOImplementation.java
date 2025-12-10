package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.Configuration.ModelMapperConfig;
import KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.JPA.Direccion;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlumnoJPADAOImplementation implements IAlumnoJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;
            
    @Override
    public Result GetAll() {
        //JPQL - para consulta de datos 
        TypedQuery<Alumno> queryAlumno = entityManager.createQuery("FROM Alumno", Alumno.class);
        List<Alumno> alumnos = queryAlumno.getResultList();

        Result result = new Result();

        //ModelMapper model = new ModelMapper();

        result.Objects = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            
          //  KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno alumnoMLDos = new KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno(alumno);
            
            KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno alumnoML = modelMapper.map(alumno, KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno.class);

            result.Objects.add(alumnoML);
        }
        /*
            Lista ALumnoJPA - List AlumnoML
            Mapper
         */

        return result;
    }

}
