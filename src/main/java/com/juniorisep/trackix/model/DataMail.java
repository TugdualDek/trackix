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
@Table(name = "data_mail")
public class DataMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMail", referencedColumnName = "id")
    @JsonBackReference // Avoid serialization loop
    private MailTrack mailTrack;
    private String userAgent;
    private String ip;
    @Column(nullable = false)
    private Date Date;

}
