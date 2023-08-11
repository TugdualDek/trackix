package com.juniorisep.trackix.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "data_link")
public class DataLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLink", referencedColumnName = "id")
    @JsonBackReference // Avoid serialization loop
    private LinkTrack linkTrack;
    private String userAgent;
    private String ip;
    @Column(nullable = false)
    private Date Date;

}
