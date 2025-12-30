
package KMedrano.ProgramacionNCapasNoviembre25.RestController;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.AlumnoJPADAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiAlumno")
@Tag(name = "Alumno", description = "Controlador relacionado a las operaciones de la entidad alumno")
public class AlumnoRestController {

    @Autowired
    private AlumnoJPADAOImplementation alumnoJPADAOImplementation;
    
    @PostMapping("/add")
    @Operation(summary = "Agragegar alumno", description = "Metodo para insertar un alumno a la base de datos")
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "El alumno fue ingresado"),
        @ApiResponse(responseCode = "500", description = "El Alumno no pudo ser insertado")})
    public ResponseEntity Add(@RequestBody Alumno alumno) {

        ModelMapper modelMapper = new ModelMapper();
        KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno alumnoJPA = modelMapper.map(alumno, KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno.class);
        Result result = alumnoJPADAOImplementation.Add(alumnoJPA);

        if(result.Correct){
            return ResponseEntity.status(200).body("Alumno agregado correctamente"); 
        }else{
            return ResponseEntity.status(500).body("Error al agregar el alumno"); 
        }
        
        
    }
    
}
