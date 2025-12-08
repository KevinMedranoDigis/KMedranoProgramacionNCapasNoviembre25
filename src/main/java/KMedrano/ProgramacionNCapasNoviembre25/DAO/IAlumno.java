
package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import java.util.List;




public interface IAlumno {
    
    public Result GetAll();
    
    public Result Add(Alumno alumno);
    //crear mi firma de metodo (Add)
    
    public Result GetByIdDirecciones(int IdAlumno);
    
    public Result GetById(int IdAlumno);
    
    public Result AddAll(List<Alumno> alumnos);
    
    public Result GetAllDinamico(Alumno alumno);
        
}
