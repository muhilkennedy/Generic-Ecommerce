package com.user.entity;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "CUSTOMER")
@ClassMetaProperty(code = "CUS")
@Indexed(index = "customer_index")
public class Customer extends User {

	private static final long serialVersionUID = 1L;

}
