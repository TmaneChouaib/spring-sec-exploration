package ma.emsi.patientsmvc3.security.service;

import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc3.security.entities.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        if(appUser==null) throw new UsernameNotFoundException(String.format("User %S not found", username))
        return null;
    }
}
