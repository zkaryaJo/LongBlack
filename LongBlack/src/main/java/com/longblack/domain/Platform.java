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
@Table(name = "PLATFORM")
public class Platform {
	
    @Id @Column(name="platform_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
//    private String address;
//    private String phone;
    
    @OneToMany(mappedBy = "platform", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @JsonBackReference
    private List<StorePlatform> storePlatforms;

}