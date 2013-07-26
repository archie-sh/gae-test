package com.github.pchudzik.gae.test.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * User: pawel
 * Date: 25.07.13
 * Time: 19:00
 */
@Entity
public class Grade extends BaseEntity {
	private String courseName;
	private int grade;

	@ManyToOne(cascade = CascadeType.ALL)
	private Student student;

	@Transient
	private boolean removed;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}
