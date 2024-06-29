package com.longblack.domain;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "STORE_PLATFORM")
public class StorePlatform {
	
    @Id @Column(name="store_platform_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String useYn;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference
    private Store store;
    
    @ManyToOne
    @JoinColumn(name = "platform_id")
    @JsonBackReference
    private Platform platform;
    
}