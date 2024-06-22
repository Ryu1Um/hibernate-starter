package org.example;

import io.hypersistence.utils.hibernate.naming.CamelCaseToSnakeCaseNamingStrategy;
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
//        configuration.setPhysicalNamingStrategy(new CamelCaseToSnakeCaseNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(BirthdayConverter.class, true);
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();
            var user = User.builder()
                    .username("ivan12@gmail.com")
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .info("""
                            {"name": "Ivan",
                            "id": 25}                       
                            """)
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
                    .role(Role.ADMIN)
                    .build();
            var user1 = session.get(User.class, "ivan@gmail.com");
            var user2 = session.get(User.class, "ivan@gmail.com");

            user2.setLastName("Petrov2");
            session.flush();

            session.getTransaction().commit();
        }

    }
}
