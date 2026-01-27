
package KMedrano.ProgramacionNCapasNoviembre25.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
         HttpSession session = request.getSession(); 
        
        if(exception instanceof DisabledException){
            session.setAttribute("isDisable", true);
            session.setAttribute("ErrorMessage", "El usuario esta deshabilitado, acudir al admin para reactivarlo");
        }
        
        
        
        
        response.sendRedirect("/IsDisable");
    }
    
}
