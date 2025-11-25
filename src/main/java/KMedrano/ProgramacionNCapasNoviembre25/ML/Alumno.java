
package KMedrano.ProgramacionNCapasNoviembre25.ML;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;


public class Alumno {
    private int IdAlumno;
    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Size(min = 2 , max = 20, message = "El tamaño minimo es de 2 y maximo 20")
    private String Nombre;
    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Size(min = 2 , max = 20, message = "El tamaño minimo es de 2 y maximo 20")
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    @Pattern(regexp = "", message = "Ingresa un correo valido")
    private String Email;
    private String Password;
    public Semestre Semestre;
    public List<Direccion> Direcciones;
    

    public int getIdAlumno() {
        return IdAlumno;
    }

    public void setIdAlumno(int IdAlumno) {
        this.IdAlumno = IdAlumno;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Semestre getSemestre() {
        return Semestre;
    }

    public void setSemestre(Semestre Semestre) {
        this.Semestre = Semestre;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
    
    
    
}
