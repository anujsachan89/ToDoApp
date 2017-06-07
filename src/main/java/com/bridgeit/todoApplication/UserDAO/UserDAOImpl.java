package com.bridgeit.todoApplication.UserDAO;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.model.UserPicture;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO{
	static final Logger log = Logger.getLogger(UserDAOImpl.class);


	public UserDAOImpl() {

	}


	@Autowired
	SessionFactory sessionFactory;

	Session session = null;


	public void addEntity(User user) throws Exception {
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		log.warn("Inserting to db, this dosent persist the item");
	}

	public User getEntityById(int id) throws Exception {

		session = sessionFactory.getCurrentSession();
		Criteria ctr = session.createCriteria(User.class);
		ctr.add(Restrictions.eq("id", id));
		User user = (User) ctr.uniqueResult();
		log.warn("Getting entity by i.d");
		return user;
	}

	public List<User> getUserList() throws Exception {
		session = sessionFactory.getCurrentSession();
		Criteria ctr = session.createCriteria(User.class);

		@SuppressWarnings("unchecked")
		List<User> list = ctr.list();
		log.warn("Getting user list");
		return list;
	}


	public void deleteEntity(int id) throws Exception {
		session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from User where id = :id");
		query.setParameter("id", id);
		int rowCount = query.executeUpdate();
		log.warn("Deleting user by i.d");
		System.out.println(rowCount + " Data Deleted");
	}


	public User authUser(String email, String password) {
		session = sessionFactory.getCurrentSession();

		Criteria ctr = session.createCriteria(User.class);
		User user = (User) ctr.add(Restrictions.conjunction().add(Restrictions.eq("email", email))
				.add(Restrictions.eq("password", password))).uniqueResult();
		log.warn("Authorising user when eamil and password is correct");
		return user;
	}


	public User getEntityByEmailId(String email) {

		session = sessionFactory.getCurrentSession();
		Criteria ctr = session.createCriteria(User.class);
		ctr.add(Restrictions.eq("email", email));
		User user = (User) ctr.uniqueResult();
		log.warn("Getting user by email i.d");
		return user;
	}

	@Override
	public void savePicture(UserPicture picture) {
			session = sessionFactory.getCurrentSession();
	        session.saveOrUpdate(picture);
	}
	
	public UserPicture getPicture(int userId)
	{	
		session = sessionFactory.getCurrentSession();
		UserPicture up = (UserPicture) session.get(UserPicture.class, userId);
		return up;
	}
}	