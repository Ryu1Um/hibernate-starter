package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        var user = User.builder()
                .username("ivan@gmail.com")
                .firstName("Ivan")
                .lastName("Ivanov")
                .birthDate(LocalDate.of(2000, 1, 19))
                .age(20)
                .build();

        var sql = """
                insert
                    into
                        %s
                        (%s)
                    values
                        (%s)
                """;
        var tableName = Optional.ofNullable(user
                        .getClass()
                        .getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        var declaredFields = user.getClass().getDeclaredFields();

        var columnNames = Arrays.stream(declaredFields)
                .map(field -> Optional
                        .ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName())).collect(Collectors.joining(", "));

        var columnValues = Arrays.stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        var prepatedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            prepatedStatement.setObject(1, declaredField.get(user));
        }
    }
}
