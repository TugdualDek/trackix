package com.juniorisep.trackix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="template")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String subject;
    private String text;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String html;
    private Date modifiedDate;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String templateDesign;

}
