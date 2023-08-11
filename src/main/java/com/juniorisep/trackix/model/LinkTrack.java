package com.juniorisep.trackix.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "link_track")
public class LinkTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String link;
    @Column(nullable = false)
    private String linkRedirect;
    private int count;
    @OneToMany(mappedBy = "linkTrack", cascade = CascadeType.ALL)
    @JsonManagedReference // Serialize this property normally
    private List<DataLink> dataLinks;
    @Column(nullable = false)
    private boolean isFinished;
    @Column(nullable = false)
    private Date startDate;

}
