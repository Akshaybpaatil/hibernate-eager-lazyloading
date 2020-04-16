package com.practice.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.practice.hibernate.demo.entity.Course;
import com.practice.hibernate.demo.entity.Instructor;
import com.practice.hibernate.demo.entity.InstructorDetail;
import com.practice.hibernate.demo.entity.Student;

public class FetchJoinInstructorCourse {

	public static void main(String[] args) {

		// create a session factory
		SessionFactory factory = new Configuration()
				                 .configure("hibernate.cfg.xml")
				                 .addAnnotatedClass(InstructorDetail.class)
				                 .addAnnotatedClass(Instructor.class)
				                 .addAnnotatedClass(Course.class)
				                 .buildSessionFactory();
		
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			//start the transaction
			session.beginTransaction();
			
			int id = 1;
			
			Query<Instructor> query =
					session.createQuery("select i from Instructor i "
							           + "JOIN FETCH i.courses "
							           + "where i.id=:theInstructorId",
							           Instructor.class);
			
			//set parameter on query
			query.setParameter("theInstructorId", id);
			
			//execute query and get Instructor
			Instructor tempInstructor = query.getSingleResult();

			
			System.out.println("Instructor"+tempInstructor);
			
			//commit transaction
			session.getTransaction().commit();
			
			session.close();
			
			System.out.println("Session is Closed\n");
			
			System.out.println("Courses:"+tempInstructor.getCourses());
			
			System.out.println("Done!");
			
		}
		finally {
			
			session.close();
			
			factory.close();
		}
	}

}
