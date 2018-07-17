package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "bankTransactionSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "BANK_TRANSACTION_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "bankTransactionSequenceGenerator")
    private Long id;

    private Long code;

    private Double amount;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "transactionType")
    private TransactionType transactionType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @JsonCreator
    public static BankTransaction Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BankTransaction bankTransaction = mapper.readValue(jsonString, BankTransaction.class);
        return bankTransaction;
    }
}
