package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ProductPurchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "productPurchaseSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PRODUCT_PURCHASE_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "productPurchaseSequenceGenerator")
    private Long id;

    private Integer code;

    private Double quantity;

    private Double unitPurchasePrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String note;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "bankTransaction")
    private BankTransaction bankTransaction;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @OneToMany(mappedBy = "productPurchase")
    private List<ContractProduct> contractProducts = new ArrayList<>();

    @JsonCreator
    public static ProductPurchase Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductPurchase productPurchase = mapper.readValue(jsonString, ProductPurchase.class);
        return productPurchase;
    }

    public Double getRemain() {
        try {
            return this.quantity - this.contractProducts
                    .stream()
                    .mapToDouble(ContractProduct::getQuantity)
                    .sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }
}
