package com.bridgeit.todoApplication.UserDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todoApplication.model.ToDoTask;

 
@Repository
@Transactional
public class ToDoDaoImpl implements ToDoDao {

	@Autowired
	SessionFactory sessionFactory;

	public void addToDoTask(ToDoTask todo) throws HibernateException 
	{

		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(todo);
	}

	public void deleteTaskByTODoId(int taskId) throws Exception 
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from ToDoTask where id = :id");
		query.setParameter("id", taskId);
		int rowCount = query.executeUpdate();
		System.out.println(rowCount + " Data Deleted");
	}

	@Override
	public List<ToDoTask> getToDoListByUserId(long id) throws Exception 
	{
		Session session = sessionFactory.openSession();
		Criteria ctr = session.createCriteria(ToDoTask.class);
		List<ToDoTask> list = ctr.add(Restrictions.eq("user.id", id)).list();
		session.close();
		
		if( list != null)
		{
			for (ToDoTask toDoTask : list) {
				if( toDoTask.getUser() != null){
					toDoTask.setUser(null);
				}
			}
		}
		
		return list;
	}
	public List<ToDoTask> getArchivedTOdoTask(long userId) throws Exception 
	{

		Session session = sessionFactory.openSession();
		Criteria ctr = session.createCriteria(ToDoTask.class);
		List<ToDoTask> list = ctr.add(Restrictions.eq("user.id", userId)).add(Restrictions.eq("archive", true)).list();
		session.close();

		if( list != null)
		{
			for (ToDoTask toDoTask : list) 
			{
				if( toDoTask.getUser() != null)
				{
					toDoTask.setUser(null);
				}
			}
		}

		return list;
	}
}
