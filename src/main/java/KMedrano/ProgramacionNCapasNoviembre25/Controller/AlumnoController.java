package KMedrano.ProgramacionNCapasNoviembre25.Controller;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.AlumnoDAOIMplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.AlumnoJPADAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.DireccionJPADAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.EstadoDAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.MunicipioDAOImplemetation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.PaisDAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.DAO.SemestreDAOImplementation;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Alumno;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Colonia;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Direccion;
import KMedrano.ProgramacionNCapasNoviembre25.ML.ErrorCarga;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Result;
import KMedrano.ProgramacionNCapasNoviembre25.ML.Semestre;
import KMedrano.ProgramacionNCapasNoviembre25.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller // sirve para mapear interacciones del usuario 
@RequestMapping("alumno")
public class AlumnoController {
    
    @Autowired // Inyección de dependencias (field injection)
    private AlumnoDAOIMplementation alumnoDAOImplementation;
    
    @Autowired
    private AlumnoJPADAOImplementation alumnoJPADAOImplementation;
    
    @Autowired
    private SemestreDAOImplementation semestreDAOImplementation;
    
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    
    @Autowired
    private MunicipioDAOImplemetation municipioDAOImplemetation;
    
    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;
    
    @Autowired
    private ValidationService validatorService;
    
    @GetMapping // responder a interacciones de usuario
    public String GetAll(Model model) {
        
        Result result = alumnoJPADAOImplementation.GetAll();

        // Result result = alumnoDAOImplementation.GetAll();
        Result resultSemestres = semestreDAOImplementation.GetAll();
        //model -> me permite cargar información desde el backend en la parte del front
        model.addAttribute("Alumnos", result.Objects);
        model.addAttribute("semestres", resultSemestres.Objects);
        model.addAttribute("alumnoBusqueda", new Alumno());
        
        return "AlumnoIndex"; // -> Busca una vista que se llame Index
    }

    //Agrega todo (AlumnoDirecion)
    @GetMapping("form/{IdAlumno}")
    public String Form(@PathVariable int IdAlumno, Model model) {
        
        Result result = semestreDAOImplementation.GetAll();
        model.addAttribute("Semestres", result.Objects);
        Alumno alumno = new Alumno();
        alumno.Direcciones = new ArrayList<>();
        alumno.Direcciones.add(new Direccion());
        model.addAttribute("Alumno", alumno);
        model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
        
        return "AlumnoForm";
    }
    
    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("Alumno") Alumno alumno, BindingResult bindingResult, Model model, @RequestParam("imagenFile") MultipartFile imagen) {
        
        if (imagen != null) {
            
        }
        
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
        Result resultSemestres = semestreDAOImplementation.GetAll();
        model.addAttribute("Alumno", result.Object);
        model.addAttribute("Semestres", resultSemestres.Objects);
        model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
        model.addAttribute("Direccion", new Direccion());
        
        return "AlumnoDetail";
    }
    
    @GetMapping("getEstadosByPais/{idPais}")
    @ResponseBody // retorna un dato estructurado (JA)
    public Result EstadosByPais(@PathVariable("idPais") int idPais) {
        
        Result resultEstados = estadoDAOImplementation.GetEstadoByPais(idPais);
        
        return resultEstados;
    }
    
    @GetMapping("getMunicipioByEstado/{IdEstado}")
    @ResponseBody
    public Result GetMunicipioByEstado(@PathVariable int IdEstado) {
        
        Result result = municipioDAOImplemetation.GetMunicipioByEstado(IdEstado);
        
        return result;
    }

    //Agrega o Actualiza  Usuario o Direccion
    @GetMapping("/formEditable") //Solo renderiza
    public String Form(@RequestParam("IdAlumno") int IdAlumno, @RequestParam(required = false) Integer IdDireccion, Model model) {
        
        if (IdDireccion == null) { // editar usuario
            Result result = alumnoDAOImplementation.GetById(IdAlumno);
            
            Result resultSemestres = semestreDAOImplementation.GetAll();
            model.addAttribute("Semestres", resultSemestres.Objects);
            Alumno alumno = (Alumno) result.Object;
            alumno.Direcciones = new ArrayList<>();
            alumno.Direcciones.add(new Direccion());
            alumno.Direcciones.get(0).setIdDireccion(-1);
            model.addAttribute("Alumno", alumno);
            return "AlumnoForm";
        } else if (IdDireccion == 0) { //Aregar direccion
            //Formulario de direccion sin datos

            Alumno alumno = new Alumno();
            alumno.setIdAlumno(1);
            alumno.Direcciones = new ArrayList<>();
            alumno.Direcciones.add(new Direccion());
            alumno.Direcciones.get(0).setIdDireccion(1);
            model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
            model.addAttribute("Alumno", alumno);
            
            return "AlumnoForm";
        } else {// Editar Direccion
            //Retornar formulario direccion con datos

            //Simulacion de DAOImplementation
            Alumno alumno = new Alumno();
            alumno.setIdAlumno(1);
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
    
    @PostMapping("formEditable")
    public String Form(@ModelAttribute Alumno alumno, @RequestParam("imagenFile") MultipartFile imagen) throws IOException {

        //evaluar si viene una imagen
        //decodificar a base64
        //Base64 a String 
        // alumno.setImagen(base64.toString)

        
        if (alumno.getIdAlumno() == 0) {
            
            ModelMapper modelMapper = new ModelMapper();
            
            KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno alumnoJPA = modelMapper.map(alumno, KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno.class);
            
            Result resultadd = alumnoJPADAOImplementation.Add(alumnoJPA);
            
            System.out.println("Estoy agregando alumno");
        } else {
            if (alumno.Direcciones.get(0).getIdDireccion() == -1) {
                
                Result result = alumnoJPADAOImplementation.Update(alumno);
                
                System.out.println("Estoy actualizando alumno");
            } else if (alumno.Direcciones.get(0).getIdDireccion() == 0) {

                //alumnoDAOImplementation.AddDireccion()
            } else {

                //alumnoDAOImplementation.updateDireccion
            }
        }
        
        return "redirect:/AlumnoIndex";
    }
    
    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }
    
    @PostMapping("CargaMasiva")
    public String CargaMasiva(@ModelAttribute MultipartFile archivo, Model model, HttpSession session) throws IOException {
        String extencion = archivo.getOriginalFilename().split("\\.")[1];
        
        String path = System.getProperty("user.dir");
        String pathArchivo = "src/main/resources/archivos";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String rutaabsoluta = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();
        
        archivo.transferTo(new File(rutaabsoluta));
        List<Alumno> alumnos = new ArrayList<>();
        
        if (extencion.equals("txt")) {
            //lectura de un archivo txt
            alumnos = LecturaArchivo(new File(rutaabsoluta));
        } else {
            alumnos = LecturaArchivoExcel(new File(rutaabsoluta));
        }
        
        List<ErrorCarga> errores = ValidarDatosTxt(alumnos);
        
        if (errores.isEmpty()) {
            model.addAttribute("listaErrores", errores);
            session.setAttribute("archivoCargaMasiva", rutaabsoluta); //path guardado en sesion
        } else {

            //   model.addAttribute("listaRrrores", true);
            model.addAttribute("listaErrores", errores);
            //retornar la lista de errores a la vista
        }
        
        return "CargaMasiva";
    }
    
    public List<Alumno> LecturaArchivo(File archivo) {
        
        List<Alumno> alumnos = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));) {
            
            bufferedReader.readLine();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] datos = line.split("\\|");
                
                Alumno alumno = new Alumno();
                alumno.setNombre(datos[0]);
                alumno.setApellidoPaterno(datos[1]);
                alumno.setApellidoMaterno(datos[2]);
                
                alumnos.add(alumno);
            }
            
        } catch (Exception ex) {
            return null;
        }
        
        return alumnos;
    }
    
    public List<Alumno> LecturaArchivoExcel(File archivo) {
        
        List<Alumno> alumnos = new ArrayList<>();
        
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            for (Row row : sheet) {
                Alumno alumno = new Alumno();
                alumno.setNombre(row.getCell(0).toString());
                alumno.setApellidoPaterno(row.getCell(1).toString());
                alumno.setApellidoMaterno(row.getCell(2).toString());
                alumno.Semestre = new Semestre();
                
                int idRol = Integer.parseInt(row.getCell(5).toString());
                alumno.Semestre.setIdSemestre(idRol);
                
                alumno.Semestre.setIdSemestre(idRol);
                alumnos.add(alumno);
            }
        } catch (Exception ex) {
            alumnos = null;
        }
        
        return alumnos;
        
    }

    //Validacion 
    public List<ErrorCarga> ValidarDatosTxt(List<Alumno> alumnos) {
        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int LineaError = 0;
        
        for (Alumno alumno : alumnos) {
            
            List<ObjectError> errors = new ArrayList<>();
            
            LineaError++;
            BindingResult bindingResultAlumno = validatorService.validateObjects(alumno);
            if (bindingResultAlumno.hasErrors()) {
                errors.addAll(bindingResultAlumno.getAllErrors());
            }
            
            if (alumno.Semestre != null) {
                BindingResult bindingSemestre = validatorService.validateObjects(alumno.Semestre);
                if (bindingSemestre.hasErrors()) {
                    errors.addAll(bindingSemestre.getAllErrors());
                }
            }
            
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = LineaError;
                errorCarga.Campo = fieldError.getField();
                errorCarga.Descripcion = fieldError.getDefaultMessage();
                erroresCarga.add(errorCarga);
                
            }
        }
        return erroresCarga;
    }
    
    @GetMapping("/CargaMasiva/procesar")
    public String ProcesarArchivo(HttpSession sesion) {
        String path = sesion.getAttribute("archivoCargaMasiva").toString();
        System.out.println(path);

        // alumnos =  LecturaArchivoExcel(new File(path));
        sesion.removeAttribute("archivoCargaMasiva");
        //Proceso de guardado
        //leer el 
        File archivo = new File(path);
        String extension = archivo.getName();

        //validacion de extension (xlsx o txt)
        return "AlumnoIndex";
    }
    
    @PostMapping("/GetAllDinamico")
    public String GetAllDinamico(@ModelAttribute Alumno alumno, Model model) {
        
        model.addAttribute("alumnoBusqueda", new Alumno());
        model.addAttribute("semestres", semestreDAOImplementation.GetAll().Objects);
//        model.addAttribute("Alumnos", alumnoDAOImplementation.GetAllDinamico(alumno).Objects);

        ModelMapper modelMapper = new ModelMapper();
        KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno alumnoJPA = modelMapper.map(alumno, KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno.class);
        
        model.addAttribute("Alumnos", alumnoJPADAOImplementation.GetAllDinamico(alumnoJPA).Objects);
        return "AlumnoIndex";
    }
    
    @GetMapping("/GetDireccionById")
    @ResponseBody
    public Result GetByIdDireccion(@RequestParam int IdDireccion) {
        Result result = new Result();
        
        result = direccionJPADAOImplementation.GetById(IdDireccion);
        
        return result;
    }
    
}
