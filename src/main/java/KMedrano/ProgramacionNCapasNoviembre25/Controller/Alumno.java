
package KMedrano.ProgramacionNCapasNoviembre25.Controller;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.AlumnoDAOIMplementation;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("alumno")
public class Alumno {
    
    @Autowired
    private AlumnoDAOIMplementation alumnoDaoImplementation;
    
    
    
    @GetMapping
    public String GetAll(Model model){
        
     Result result =  alumnoDaoImplementation.GetAll();
     
     model.addAttribute("Alumnos", result.Objects);
        
        return "Index";
    }
    
    
    
}
