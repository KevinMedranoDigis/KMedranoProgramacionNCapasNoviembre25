package KMedrano.ProgramacionNCapasNoviembre25.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DIRECCION")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int IdDireccion;
    
    @Column(name = "calle")
    private String Calle;
    
    @ManyToOne
    @JoinColumn(name = "idalumno")
    public Alumno Alumno;
}
