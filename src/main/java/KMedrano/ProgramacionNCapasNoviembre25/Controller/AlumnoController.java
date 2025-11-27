package KMedrano.ProgramacionNCapasNoviembre25.Controller;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.AlumnoDAOIMplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.EstadoDAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.MunicipioDAOImplemetation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.PaisDAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.SemestreDAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Colonia;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Estado;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // sirve para mapear interacciones del usuario 
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired // Inyección de dependencias (field injection)
    private AlumnoDAOIMplementation alumnoDAOImplementation;

    @Autowired
    private SemestreDAOImplementation semestreDAOImplementation;
    
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation; 
    
    @Autowired
    private MunicipioDAOImplemetation municipioDAOImplemetation;

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
        model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);

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
    public String Detail(@PathVariable("IdAlumno") int IdAlumno, Model model) {

        //consulta de un usuario con sus direcciones
        Result result = alumnoDAOImplementation.GetByIdDirecciones(IdAlumno);
        model.addAttribute("Alumno", result.Objects);
        
        return "AlumnoDetail";
    }
    
    @GetMapping("getEstadosByPais/{idPais}")
    @ResponseBody // retorna un dato estructurado (JA)
    public Result EstadosByPais(@PathVariable("idPais") int idPais){
        
        Result resultEstados = estadoDAOImplementation.GetEstadoByPais(idPais);

        return resultEstados;
    }
    
    @GetMapping("getMunicipioByEstado/{IdEstado}")
    @ResponseBody
    public Result GetMunicipioByEstado(@PathVariable int IdEstado){
        
        Result result  = municipioDAOImplemetation.GetMunicipioByEstado(IdEstado);
        
        return result;
    }
    
    @GetMapping("/formEditable") //Solo renderiza
    public String Form(@RequestParam("IdAlumno") int IdAlumno, @RequestParam(required = false) Integer IdDireccion, Model model){
    
        
        if(IdDireccion == null){ // editar usuario
            Result result = alumnoDAOImplementation.GetById(IdAlumno);
            
            Result resultSemestres = semestreDAOImplementation.GetAll();
            model.addAttribute("Semestres", resultSemestres.Objects);
            model.addAttribute("Alumno", result.Object);
            return "AlumnoForm";
        }
        else if(IdDireccion == 0){ //Aregar direccion
                //Formulario de direccion sin datos
                    
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(1);
                alumno.Direcciones = new ArrayList<>();
                alumno.Direcciones.add(new Direccion());
                alumno.Direcciones.get(0).setIdDireccion(1);
                 model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
                model.addAttribute("Alumno", alumno);
                
        
                return "AlumnoForm";
                }else{// Editar Direccion
                    //Retornar formulario direccion con datos
                   
                    //Simulacion de DAOImplementation
                    Alumno alumno = new Alumno();
                    alumno.Direcciones = new ArrayList<>();
                    alumno.Direcciones.add(new Direccion());
                    alumno.Direcciones.get(0).setIdDireccion(1);
                    alumno.Direcciones.get(0).setCalle("Francisco");
                    alumno.Direcciones.get(0).setNumeroExterior("34");
                    alumno.Direcciones.get(0).setNumeroInterior("34");
                    alumno.Direcciones.get(0).Colonia = new Colonia();
                    
                    model.addAttribute("Alumno", alumno);
                    model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
                    return "AlumnoForm";
        
        }
        
    }

    
    
}
