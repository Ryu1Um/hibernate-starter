package org.example;

import org.example.converters.BirthdayConverter;
import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        var configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(BirthdayConverter.class, true);
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();
            var user = User.builder()
                    .username("ivan@gmail.com")
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .info("""
                            {"name": "Ivan",
                            "id": 25}                       
                            """)
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
                    .role(Role.ADMIN)
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }

    }
}
