package ma.emsi.patientsmvc3.security.UserRepository;

import ma.emsi.patientsmvc3.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
}
