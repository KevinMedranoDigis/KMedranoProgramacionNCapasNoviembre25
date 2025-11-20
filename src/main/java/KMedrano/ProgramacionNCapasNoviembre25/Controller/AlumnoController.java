
package KMedrano.ProgramacionNCapasNoviembre25.Controller;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.AlumnoDAOIMplementation;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // sirve para mapear interacciones del usuario 
@RequestMapping("alumno")
public class AlumnoController {
    
    @Autowired // Inyección de dependencias (field injection)
    private AlumnoDAOIMplementation alumnoDAOImplementation;


    @GetMapping // responder a interacciones de usuario
    public String GetAll(Model model){
        
          Result result =  alumnoDAOImplementation.GetAll();
          //model -> me permite cargar información desde el backend en la parte del front
          model.addAttribute("Alumnos", result.Objects);
        
          return "AlumnoIndex"; // -> Busca una vista que se llame Index
    }
    
    @GetMapping("form")
    public String Form(Model model){
        
        model.addAttribute("Alumno", new Alumno());
        
        return "AlumnoForm";
    }
    
    @PostMapping("add")
    public String Add(@ModelAttribute("Alumno") Alumno alumno){
    
        
        //Llamado a mi DAO
     
        
        return "AlumnoIndex";
    }
    
    
}
