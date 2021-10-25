package info.cepheus.showcase_axon_exception.domain.query.personSnapshot;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PersonSnapshot {
    @Id
    private String id;

    private String personId;

    private String name;
}
