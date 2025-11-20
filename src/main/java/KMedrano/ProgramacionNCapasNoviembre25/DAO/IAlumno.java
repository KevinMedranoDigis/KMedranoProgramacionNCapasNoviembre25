
package KMedrano.ProgramacionNCapasNoviembre25.DAO;

import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;




public interface IAlumno {
    
    public Result GetAll();
    
    public Result Add(Alumno alumno);
    //crear mi firma de metodo (Add)
    
}
