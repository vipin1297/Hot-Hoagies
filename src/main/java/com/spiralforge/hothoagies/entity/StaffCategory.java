package com.spiralforge.hothoagies.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class StaffCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long staffCategoryId;
	
	@OneToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
}
