package org.example.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Data
@Entity
@Builder
@Table(name = "users", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String username;
    private String firstName;
    private String lastName;
    //    @Convert(converter = BirthdayConverter.class) alternative to configuration.addAttributeConverter
    @Column(name = "birth_date")
    private Birthday birthDate;

    @Type(JsonBinaryType.class)
    private String info;

    @Enumerated(EnumType.STRING)
    private Role role;
}
