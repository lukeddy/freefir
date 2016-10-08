package com.snicesoft.freefir.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = -3631578764777787952L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "paymentableGenerator", strategy = "com.snicesoft.freefir.utils.IdGenerator")
	@Column(name = "id")
	protected Long id;
	
	public BaseEntity() {
		super();
	}

	public BaseEntity(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}