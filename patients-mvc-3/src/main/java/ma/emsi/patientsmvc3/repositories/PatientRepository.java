
package ma.emsi.patientsmvc3.repositories;

import ma.emsi.patientsmvc3.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository <Patient,Long>{
    Page<Patient> findByNomContains(String kw , Pageable pegeable);

}
