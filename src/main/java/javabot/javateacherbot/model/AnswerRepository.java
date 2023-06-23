package javabot.javateacherbot.model;

/**
 * Created by
 * Bogun Semen
 * Интерфейс репозиторий, с помощью него мы получаем связь с БД, для совершения операций с данными
 */

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
