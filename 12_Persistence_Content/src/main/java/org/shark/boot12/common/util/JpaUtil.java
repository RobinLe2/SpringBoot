package org.shark.boot12.common.util;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Component
public class JpaUtil {

  private EntityManagerFactory factory;
  
  public void initFactory() {
    factory = Persistence.createEntityManagerFactory("jpa-learning");
  }
  
  public void closeFactory() {
    if (factory != null) 
    factory.close();
  }
  
  public EntityManager getEntityManager() {
    return factory.createEntityManager();
  }
}
