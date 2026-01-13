package KMedrano.ProgramacionNCapasNoviembre25.Service;

import KMedrano.ProgramacionNCapasNoviembre25.DAO.IAlumnoJPARepository;
import KMedrano.ProgramacionNCapasNoviembre25.JPA.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService implements UserDetailsService {
    
    private IAlumnoJPARepository iAlumnoJPARepository;
    
    public UserDetailsJPAService(IAlumnoJPARepository iAlumnoJPARepository) {
        this.iAlumnoJPARepository = iAlumnoJPARepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumno alumno = iAlumnoJPARepository.findByEmail(username);
        
        return User.withUsername(alumno.getEmail())
                .password(alumno.getPassword())
                .roles(alumno.Semestre.getNombre())
                //  .accountExpired(true)
                //     .disabled(true)
                .build();
    }
    
}
