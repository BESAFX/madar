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
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "sellerSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "SELLER_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "sellerSequenceGenerator")
    private Long id;

    private Integer code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "contact")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;

    @OneToMany(mappedBy = "seller")
    private List<Contract> contracts = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<ProductPurchase> productPurchases = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<BankTransaction> bankTransactions = new ArrayList<>();

    @Transient
    private Double balance;

    @Transient
    private Double totalDeposits;

    @Transient
    private Double totalWithdraws;

    @JsonCreator
    public static Seller Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Seller seller = mapper.readValue(jsonString, Seller.class);
        return seller;
    }

    public String getShortName() {
        try {
            return this.contact.getNickname().concat(" ").concat(this.contact.getName());
        } catch (Exception ex) {
            return "";
        }
    }

    public Date getLastContractDate() {
        try {
            return this.contracts.stream().map(Contract::getWrittenDate).max(Date::compareTo).get();
        } catch (Exception ex) {
            return null;
        }
    }
}
