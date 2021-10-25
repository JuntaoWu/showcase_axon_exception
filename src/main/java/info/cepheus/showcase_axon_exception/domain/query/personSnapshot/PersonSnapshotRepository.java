package info.cepheus.showcase_axon_exception.domain.query.personSnapshot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonSnapshotRepository extends JpaRepository<PersonSnapshot, String> {
}
