package info.cepheus.showcase_axon_exception.domain.query.personInformation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonInformationRepository extends JpaRepository<PersonInformation, String> {
}
