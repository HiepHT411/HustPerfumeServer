package com.hoanghiep.perfume.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "perfumes")
public class Perfume {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfume_id_seq")
    @SequenceGenerator(name = "perfume_id_seq", sequenceName = "perfume_id_seq", initialValue = 1001, allocationSize = 1)
    private Long id;
    private String title;
    private String perfumer;
    private Integer year;
    private String country;
    private String gender;
    private String fragranceTopNotes;
    private String fragranceMiddleNotes;
    private String fragranceBaseNotes;
    private String description;
    private Integer price;
    private String volume;
    private String type;
    private Double rating;
    private String filename; 	// image link

    //one to many relationship with Review
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perfume")
    @JsonIgnore
    private List<Review> reviews;

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }

	@Override
	public String toString() {
		return "Perfume [id=" + id + ", title=" + title + ", perfumer=" + perfumer + ", year=" + year + ", country="
				+ country + ", gender=" + gender + ", fragranceTopNotes=" + fragranceTopNotes
				+ ", fragranceMiddleNotes=" + fragranceMiddleNotes + ", fragranceBaseNotes=" + fragranceBaseNotes
				+ ", description=" + description + ", price=" + price + ", volume=" + volume + ", type=" + type
				+ ", rating=" + rating + ", filename=" + filename + "]";
	}
    
    
}
