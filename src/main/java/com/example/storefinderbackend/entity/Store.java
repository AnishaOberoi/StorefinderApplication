package com.example.storefinderbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_details")
    private String contactDetails;

    @Column(name = "operating_hours")
    private String operatingHours;

    @Column(name = "description")
    private String description;

    @Embedded
    @Column(name= "location")
    private Location location;

    @ElementCollection
    @CollectionTable(name = "store_reviews", joinColumns = @JoinColumn(name = "store_id"))
    @Column(name = "reviews")
    private List<Reviews> reviews = new ArrayList<>();
}

