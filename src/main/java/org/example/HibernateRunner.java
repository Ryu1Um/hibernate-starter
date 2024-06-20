package org.example;

import org.example.entity.User;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        var configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {

            session.beginTransaction();
            var user = User.builder()
                    .username("ivan@gmail.com")
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .birthDate(LocalDate.of(2000, 1, 19))
                    .age(20)
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }

    }
}
