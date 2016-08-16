package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "entry_id")
    private long id;

    @Column(name = "content")
    //TODO настроить максимальную длину в 1024 символа
    private String content;

    @Column(name = "creation_date")
    private Date creationDate;
}
