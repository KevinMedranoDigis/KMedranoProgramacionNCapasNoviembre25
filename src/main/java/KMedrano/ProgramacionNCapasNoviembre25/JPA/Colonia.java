
package KMedrano.ProgramacionNCapasNoviembre25.JPA;

import KMedrano.ProgramacionNCapasNoviembre25.ML.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "colonia")
public class Colonia {
    @Id
    @Column(name = "idcolonia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdColonia;
    @Column(name = "nombre")
    private String Nombre;
    @Column(name = "codigopostal")
    private String CodigoPostal;
    
    //public Municipio Municipio;

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }
    
    
}
