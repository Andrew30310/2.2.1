package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void addUser(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> getUsersList() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findUser(String model, int series){
      List<Car> carList= sessionFactory.getCurrentSession()
              .createQuery("SELECT id FROM Car WHERE model = '" + model + "' AND series = " + series)
              .getResultList();
      List<User> userList = sessionFactory.getCurrentSession()
              .createQuery("SELECT r FROM User r where cars_id = " + carList.get(0), User.class)
              .getResultList();
      return userList.get(0);
   }
}
