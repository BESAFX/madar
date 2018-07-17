package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "transactionTypeSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TRANSACTION_TYPE_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "transactionTypeSequenceGenerator")
    private Long id;

    private String code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "transactionType")
    private TransactionType transactionType;

    @JsonCreator
    public static TransactionType Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TransactionType transactionType = mapper.readValue(jsonString, TransactionType.class);
        return transactionType;
    }
}
