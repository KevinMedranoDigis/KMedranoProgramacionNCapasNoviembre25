package KMedrano.ProgramacionNCapasNoviembre25.Controller;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.AlumnoDAOIMplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.SemestreDAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // sirve para mapear interacciones del usuario 
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired // Inyección de dependencias (field injection)
    private AlumnoDAOIMplementation alumnoDAOImplementation;

    @Autowired
    private SemestreDAOImplementation semestreDAOImplementation;

    @GetMapping // responder a interacciones de usuario
    public String GetAll(Model model) {

        Result result = alumnoDAOImplementation.GetAll();
        //model -> me permite cargar información desde el backend en la parte del front
        model.addAttribute("Alumnos", result.Objects);

        return "AlumnoIndex"; // -> Busca una vista que se llame Index
    }

    @GetMapping("form")
    public String Form(Model model) {

        Result result = semestreDAOImplementation.GetAll();
        model.addAttribute("Semestres", result.Objects);
        model.addAttribute("Alumno", new Alumno());

        return "AlumnoForm";
    }

    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("Alumno") Alumno alumno, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("Alumno", alumno);

            return "AlumnoForm";
        } else {
            Result result = alumnoDAOImplementation.Add(alumno);
        }

        return "AlumnoIndex";
    }
    

    @GetMapping("detail/{IdAlumno}")
    public String Detail(@PathVariable("IdAlumno") int IdAlumno) {

        //consulta de un usuario con sus direcciones
        return "AlumnoDetail";
    }

}
