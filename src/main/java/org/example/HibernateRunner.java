package org.example;

import io.hypersistence.utils.hibernate.naming.CamelCaseToSnakeCaseNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.example.converters.BirthdayConverter;
import org.example.entity.Birthday;
import org.example.entity.PersonalInfo;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        //TRANSIENT
        var user = User.builder()
                .personalInfo(PersonalInfo.builder()
                        .lastName("Petrov")
                        .firstName("Petr")
                        .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                        .build())
                .build();

        log.info("User entity is in transient state, object: {}", user);

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            var session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User is in persistent state: {}. session {}", user, session1);

                session1.getTransaction().commit();
            }
            log.warn("User is detached state: {}, session is closed {}", user, session1);

            try (var session = sessionFactory.openSession()) {
                var key = PersonalInfo.builder()
                        .lastName("Petrov")
                        .firstName("Petr")
                        .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                        .build();

                var user2 = session.get(User.class, key);
                log.info("USER2 {}", user2);
            }
        } catch (Exception e) {
            log.error("Exception occurred", e);
        }
    }
}
