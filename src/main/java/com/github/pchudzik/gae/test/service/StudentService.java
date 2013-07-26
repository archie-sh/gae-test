package com.github.pchudzik.gae.test.service;

import com.github.pchudzik.gae.test.dao.StudentDao;
import com.github.pchudzik.gae.test.domain.Grade;
import com.github.pchudzik.gae.test.domain.Student;
import com.github.pchudzik.gae.test.repository.StudentRepository;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Iterator;
import java.util.List;

/**
 * User: pawel
 * Date: 27.07.13
 * Time: 08:59
 */
@Service
public class StudentService {
	public enum AccessMethod {
		RAW_EM, SPRING_REPO
	}

	@PersistenceContext private EntityManager entityManager;

	@Autowired private StudentDao studentDao;
	@Autowired private StudentRepository studentRepository;

	public Student findOne(Key id, AccessMethod accessMethod) {
		switch (accessMethod) {
			case RAW_EM: return studentDao.findOne(id);
			case SPRING_REPO: return studentRepository.findOne(id);
			default: throw new IllegalArgumentException();
		}
	}

	@Transactional
	public Student saveStudent(Student student, AccessMethod accessMethod) {
		Student result = saveStudentInternal(student, accessMethod);
		Iterator<Grade> gradeIterator = student.getGrades().iterator();
		List<Grade> removedGrades = Lists.newArrayList();
		while (gradeIterator.hasNext()) {
			Grade grade = gradeIterator.next();
			if(grade.isRemoved()) {
				gradeIterator.remove();
				grade.setStudent(null);
				removedGrades.add(grade);
			}
		}
		return result;
	}

	public void delete(Key key, AccessMethod accessMethod) {
		if(accessMethod == AccessMethod.RAW_EM) {
			studentDao.delete(key);
		} else if (accessMethod == AccessMethod.SPRING_REPO) {
			studentRepository.delete(key);
		}
	}

	public List<Student> findAll(AccessMethod accessMethod) {
		switch (accessMethod) {
			case RAW_EM: return studentDao.findAll();
			case SPRING_REPO: return studentRepository.findAll();
			default: throw new IllegalArgumentException();
		}
	}

	private Student saveStudentInternal(Student student, AccessMethod accessMethod) {
		switch (accessMethod) {
			case RAW_EM: return studentDao.save(student);
			case SPRING_REPO: return studentRepository.save(student);
			default: throw new IllegalArgumentException();
		}
	}
}
