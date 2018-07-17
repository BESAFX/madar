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
public class ContractProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "contractProductSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CONTRACT_PRODUCT_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "contractProductSequenceGenerator")
    private Long id;

    private Double quantity;

    private Double unitSellPrice;

    private Double unitVat;

    @ManyToOne
    @JoinColumn(name = "contract")
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "productPurchase")
    private ProductPurchase productPurchase;

    @JsonCreator
    public static ContractProduct Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ContractProduct contractProduct = mapper.readValue(jsonString, ContractProduct.class);
        return contractProduct;
    }

    public Double getTotalPriceAfterVat() {
        try {
            return (this.quantity * this.unitSellPrice) + (this.quantity * this.unitVat);
        } catch (Exception ex) {
            return 0.0;
        }
    }
}
