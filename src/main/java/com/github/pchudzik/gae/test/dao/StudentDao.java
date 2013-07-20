package com.github.pchudzik.gae.test.dao;

import com.github.pchudzik.gae.test.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: pawel
 * Date: 20.07.13
 * Time: 11:24
 */
@Repository
public class StudentDao {
	@Autowired private PlatformTransactionManager txManager;
	@PersistenceContext private EntityManager entityManager;

	private TransactionTemplate transactionTemplate;

	@PostConstruct
	protected void postConstruct() {
		transactionTemplate = new TransactionTemplate(txManager);
	}

	@Transactional //ugly don't do this
	public Student save(final Student student) {
		return transactionTemplate.execute(new TransactionCallback<Student>() {
			@Override
			public Student doInTransaction(TransactionStatus status) {
				return entityManager.merge(student);
			}
		});
	};

	public List<Student> findAll() {
		return (List<Student>)entityManager.createQuery("select s from Student s").getResultList();
	}

	public Student findOne(Long id) {
		return (Student) entityManager.createQuery("select s from Student s where id = " + id).getSingleResult();
	}
}
