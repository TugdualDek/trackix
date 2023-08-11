package com.juniorisep.trackix.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mail_track")
public class MailTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private boolean isFinished;
    @OneToMany(mappedBy = "mailTrack", cascade = CascadeType.ALL)
    @JsonManagedReference // Serialize this property normally
    private List<DataMail> dataMails;
    private String link;
    private int count;

}
