package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAlumnoJPARepository extends JpaRepository<Alumno, Integer>{
    
}
