package com.besafx.app.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Call implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "callSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CALL_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "callSequenceGenerator")
    private Long id;

    private String note;

    @ManyToOne
    @JoinColumn(name = "offer")
    @JsonIgnoreProperties(value = {"calls"}, allowSetters = true)
    private Offer offer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "person")
    @JsonIgnoreProperties(value = {"branch"}, allowSetters = true)
    private Person person;

    @JsonCreator
    public static Call Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Call call = mapper.readValue(jsonString, Call.class);
        return call;
    }
}
