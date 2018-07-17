package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "customerSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CUSTOMER_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "customerSequenceGenerator")
    private Long id;

    private Integer code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "contact")
    private Contact contact;

    @OneToMany(mappedBy = "customer")
    private List<Contract> contracts = new ArrayList<>();

    @JsonCreator
    public static Customer Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = mapper.readValue(jsonString, Customer.class);
        return customer;
    }

    public Double getContractsTotalPrice() {
        try {
            return this.contracts.stream().mapToDouble(Contract::getTotalPrice).sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getContractsPaid() {
        try {
            return this.contracts.stream().mapToDouble(Contract::getPaid).sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getContractsRemain() {
        try {
            return this.contracts.stream().mapToDouble(Contract::getRemain).sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }
}
