package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {

    private String firstName;
    private String lastName;
    //    @Convert(converter = BirthdayConverter.class) alternative to configuration.addAttributeConverter
    private Birthday birthDate;
}
