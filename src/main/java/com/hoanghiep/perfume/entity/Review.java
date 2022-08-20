package com.hoanghiep.perfume.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String message;
    private Integer rating;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Perfume perfume;
    
    public Review() {
        this.date = LocalDate.now();
    }
}
