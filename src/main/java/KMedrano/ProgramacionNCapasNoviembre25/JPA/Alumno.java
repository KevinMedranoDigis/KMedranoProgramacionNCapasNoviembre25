package KMedrano.ProgramacionNCapasNoviembre25.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "ALUMNO")
public class Alumno {

    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalumno")
    private int IdAlumno;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @ManyToOne
    @JoinColumn(name = "idsemestre")
    public Semestre Semestre; // FK
            
    @OneToMany(mappedBy = "Alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones = new ArrayList<>();
    
}
