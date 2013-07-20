package com.github.pchudzik.gae.test.domain;

import com.google.appengine.repackaged.com.google.common.base.Objects;

import javax.persistence.Entity;

/**
 * User: pawel
 * Date: 20.07.13
 * Time: 10:38
 */
@Entity
public class Student extends BaseEntity {
	private String firstName;
	private String lastName;
	private String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("password", password)
				.toString();
	}
}
