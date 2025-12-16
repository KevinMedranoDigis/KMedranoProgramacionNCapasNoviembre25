package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;


public interface IAlumnoJPA {
    Result GetAll();
    Result Add(Alumno alumno);
    Result GetAllDinamico(Alumno alumno);
}
