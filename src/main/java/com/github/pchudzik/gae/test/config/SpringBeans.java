package com.github.pchudzik.gae.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * User: pawel
 * Date: 20.07.13
 * Time: 11:15
 */
@Configuration
@EnableTransactionManagement
public class SpringBeans {
	private static final String PERSISTENCE_UNIT = "transactions-optional";

	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactoryGoogleWay() {
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	@Bean @Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}
}
