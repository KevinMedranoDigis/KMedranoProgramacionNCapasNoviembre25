package KMedrano.ProgramacionNCapasNoviembre25.Service;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.IAlumnoJPARepository;
import KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class AlumnoService {

    @Autowired
    private IAlumnoJPARepository alumnoJPARepository;

    public List<Alumno> GetAll() {

        try {

            List<KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno> alumnos = alumnoJPARepository.findAll();

            return alumnos;

        } catch (Exception ex) {
            return null;

        }

    }
}
