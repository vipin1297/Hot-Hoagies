package com.spiralforge.hothoagies.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@SequenceGenerator(name = "orderDetailId", initialValue = 121916, allocationSize = 1)
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderDetailId")
	private Long orderDetailId;
	private LocalDate orderDate;
	private LocalTime orderTime;
	private String orderStatus;
	private String paymentMode;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
}
