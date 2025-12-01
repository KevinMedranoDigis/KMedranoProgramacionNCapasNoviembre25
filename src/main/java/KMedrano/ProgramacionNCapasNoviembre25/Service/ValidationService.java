package KMedrano.ProgramacionNCapasNoviembre25.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import java.lang.Object;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class ValidationService {
    
    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;
    
    public BindingResult validateObjects(Object target){
        DataBinder dataBinder = new DataBinder(target);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        
        return dataBinder.getBindingResult();
        
    }
    
    
}
