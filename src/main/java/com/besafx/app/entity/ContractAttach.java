package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
public class ContractAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "contractAttachSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CONTRACT_ATTACH_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "contractAttachSequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attach")
    private Attach attach;

    @ManyToOne
    @JoinColumn(name = "contract")
    private Contract contract;

    @JsonCreator
    public static ContractAttach Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ContractAttach attachment = mapper.readValue(jsonString, ContractAttach.class);
        return attachment;
    }
}
