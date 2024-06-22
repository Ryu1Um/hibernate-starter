package org.example;

import io.hypersistence.utils.hibernate.naming.CamelCaseToSnakeCaseNamingStrategy;
import org.example.converters.BirthdayConverter;
import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        //TRANSIENT
        var user = User.builder()
                .username("ivan@gmail.com")
                .lastName("Ivanov")
                .firstName("Ivan")
                .build();

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (var session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }

            try (var session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
//                session2.delete(user);
                user.setFirstName("Sveta");
//                session2.refresh(user);
                var freshUser = session2.get(User.class, user.getUsername());
                user.setLastName(freshUser.getLastName());
                user.setLastName(freshUser.getLastName());

                session2.merge(user);
                session2.getTransaction().commit();
            }
        }

    }
}
