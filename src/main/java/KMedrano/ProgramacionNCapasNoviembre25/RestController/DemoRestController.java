package KMedrano.ProgramacionNCapasNoviembre25.RestController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DemoRestController {
    
    
    @GetMapping("hola/{persona}")
    public String HolaMundo(@PathVariable String persona){
    
        return"Hola "+persona;
    }
}
