import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.StudentInfoService;

import javax.persistence.EntityManager;

public class AppMain {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        try(SessionFactory sessionFactory = configuration.  buildSessionFactory()){
            EntityManager entityManager = sessionFactory.createEntityManager();
            StudentInfoService studentInfoService = new StudentInfoService();
            studentInfoService.studentInfo(entityManager, 1L);
            entityManager.close();
        }
    }
}
