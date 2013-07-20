package com.github.pchudzik.gae.test.domain;

import javax.persistence.*;

/**
 * User: pawel
 * Date: 20.07.13
 * Time: 14:09
 */
@Entity
@MappedSuperclass
public class BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
