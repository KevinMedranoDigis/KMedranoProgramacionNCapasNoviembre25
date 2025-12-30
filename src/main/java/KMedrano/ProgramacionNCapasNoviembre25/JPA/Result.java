
package KMedrano.ProgramacionNCapasNoviembre25.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;


public class Result {
    public boolean Correct;
    public String ErrorMessage;
    public Exception ex;
    public Object Object;
    public List<Object> Objects;
    @JsonIgnore
    public int StatusCode;
}
