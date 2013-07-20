package com.github.pchudzik.gae.test.dao;

import com.github.pchudzik.gae.test.domain.Student;
import org.springframework.stereotype.Repository;

import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: pawel
 * Date: 20.07.13
 * Time: 11:24
 */
@Repository
public class StudentDao {
	@PersistenceContext private EntityManager em;

	@Transactional //ugly don't do this
	public Student save(Student student) {
		return em.merge(student);
	}
}
