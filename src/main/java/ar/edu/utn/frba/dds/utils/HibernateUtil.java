package ar.edu.utn.frba.dds.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory sessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

}
