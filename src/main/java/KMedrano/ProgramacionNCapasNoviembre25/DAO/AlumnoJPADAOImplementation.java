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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Result Add(Alumno alumno) {
        Result result = new Result();

        try {

            entityManager.persist(alumno);
            alumno.Direcciones.get(0).Alumno = new Alumno();
            alumno.Direcciones.get(0).Alumno.setIdAlumno(alumno.getIdAlumno());
            entityManager.persist(alumno.Direcciones.get(0));

            //guardar la direccion
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    //Transactional
    //override
    public Result Update(KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno alumnoML) {

        //verificar si existe en la base de datos
        Alumno alumnoBD = entityManager.find(Alumno.class, alumnoML.getIdAlumno());

        if (alumnoBD != null) {

            ModelMapper modelMapper = new ModelMapper();

            Alumno alumnoJPA = modelMapper.map(alumnoML, Alumno.class);

            alumnoJPA.Direcciones = alumnoBD.Direcciones;

            System.out.println(alumnoJPA);
            //merge

        }
        //mapear el ML Alumno a un JPA Alumno
        //  hacer merge

        return new Result();
    }
}
