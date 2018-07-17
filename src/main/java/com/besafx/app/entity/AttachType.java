package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachType implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "attachTypeSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ATTACH_TYPE_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "attachTypeSequenceGenerator")
    private Long id;

    private String name;

    @JsonCreator
    public static AttachType Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AttachType billBuyType = mapper.readValue(jsonString, AttachType.class);
        return billBuyType;
    }
}
