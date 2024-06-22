package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.converters.BirthdayConverter;
import org.example.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        var configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToSnakeCaseNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(BirthdayConverter.class, true);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
