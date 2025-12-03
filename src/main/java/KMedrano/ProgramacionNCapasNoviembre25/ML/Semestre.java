
package KMedrano.ProgramacionNCapasNoviembre25.ML;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class Semestre {
    @Max(value=10, message = "El valor debe estar dentro del rango de 1-10")
    @Min(value=1, message = "El valor debe estar dentro del rango de 1-10")
    private int IdSemestre;
    private String Nombre;

    public int getIdSemestre() {
        return IdSemestre;
    }

    public void setIdSemestre(int IdSemestre) {
        this.IdSemestre = IdSemestre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    
    
}
