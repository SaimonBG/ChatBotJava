package javabot.javateacherbot.service;

/**
 * Created by
 * Bogun Semen
 * Класс для записи данных в таблицу БД
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import javabot.javateacherbot.model.Answer;


public class HibernateService {


    private static SessionFactory sessionFactory;

    private static Session session;

    private static Transaction transaction;


    // TODO: В данном методе идет запись данных в БД, используя Hibernate, где легко и быстро идет запись данных в таблицу,
    //  без использования SQL скриптов, Hibernate делает все это за нас.
    public static void main(String[] args) {

        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        Answer answer1 = new Answer();
        answer1.setTheme("Theme text");
        answer1.setAnswer("Answer text");

        session.save(answer1);
        transaction.commit();
        session.close();
        sessionFactory.close();

    }
}
