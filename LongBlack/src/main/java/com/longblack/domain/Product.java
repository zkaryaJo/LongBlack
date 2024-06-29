package com.longblack.domain;

import java.util.List;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Audited
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "COFFEE_PRODUCT")
public class Product {
	
	 	@Id @Column(name="product_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 	@ManyToOne
	 	@JsonBackReference
	 	@JoinColumn(name = "store_id")
	 	private Store store;

	 	private String category;
	    private String name;
	    private String description;
	    private double price;
	    private int stock;

	    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	    @JsonManagedReference
	    private List<OrderItem> orderItems;

	    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	    @JsonManagedReference
	    private List<Review> reviews;

}
