package com.platform.entity;

import java.sql.Types;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "EMAILTEMPLATE")
@ClassMetaProperty(code = "ETL")
public class EmailTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "TITLE")
	private String title;
	
	@JsonIgnore
	@Lob
	@JdbcTypeCode(Types.VARBINARY)
	@Column(name = "CONTENT")
	private byte[] content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
