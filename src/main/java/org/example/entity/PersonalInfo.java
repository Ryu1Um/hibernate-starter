package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8673824335886693473L;

    private String firstName;
    private String lastName;
    //    @Convert(converter = BirthdayConverter.class) alternative to configuration.addAttributeConverter
    private Birthday birthDate;
}
