package ma.emsi.patientsmvc3.security.UserRepository;

import ma.emsi.patientsmvc3.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
