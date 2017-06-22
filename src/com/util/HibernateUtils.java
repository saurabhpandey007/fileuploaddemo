package com.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
	
	private static  SessionFactory sessionFactory;
	 
    public static SessionFactory getSessionFactory()
    {
    	if(sessionFactory == null)
    	{
    		
    		
    		/* load from different directory
    					SessionFactory sessionFactory = new Configuration().configure(
    							"/com/mkyong/persistence/hibernate.cfg.xml")
    							.buildSessionFactory();*/
    		
    		// loads configuration and mappings from resource folder 
    		Configuration configuration = new Configuration().configure();
    		
    		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
    	
    		 // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);     
    	}
    	
    	return sessionFactory;
    }
    public static void closeSession() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}
