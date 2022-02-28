package com.hoanghiep.perfume.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class OrderItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq", initialValue = 11, allocationSize = 1)
    private Long id;
    private Long amount;	//price x quantity
    private Long quantity;

    //quan he 1vs1, refer voi object perfume
    @OneToOne
    private Perfume perfume;
}
