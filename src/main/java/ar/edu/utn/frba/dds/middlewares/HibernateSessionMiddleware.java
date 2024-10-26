package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.utils.HibernateUtil;
import io.javalin.http.Context;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateSessionMiddleware {

    public static void handleSession(Context ctx) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.sessionFactory().openSession();
            transaction = session.beginTransaction();
            // Store the session in the context so it can be accessed in routes
            ctx.attribute("entity-manager", session);
            session.getEntityManagerFactory().createEntityManager();

            // no tiene q commitear aca
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
}
