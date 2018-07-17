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
public class ContractPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "contractPaymentSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CONTRACT_PAYMENT_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "contractPaymentSequenceGenerator")
    private Long id;

    private Integer code;

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "contract")
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "contractPremium")
    private ContractPremium contractPremium;

    @ManyToOne
    @JoinColumn(name = "bankTransaction")
    private BankTransaction bankTransaction;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @JsonCreator
    public static ContractPayment Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ContractPayment contractPayment = mapper.readValue(jsonString, ContractPayment.class);
        return contractPayment;
    }

    //حساب رأس المال
    public Double getCapitalCash() {
        try {
            return this.amount - this.getProfit();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    //حساب الربح
    public Double getProfit() {
        try {
            return (this.amount * this.contract.getProfitPercentage()) / 100;
        } catch (Exception ex) {
            return 0.0;
        }
    }
}
