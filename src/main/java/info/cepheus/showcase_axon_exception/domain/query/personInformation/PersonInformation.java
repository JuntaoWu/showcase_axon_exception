package info.cepheus.showcase_axon_exception.domain.query.personInformation;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "ux_person_information_name", columnNames = {"name"})})
public class PersonInformation {
    @Id
    private String personId;

    private String name;

}
