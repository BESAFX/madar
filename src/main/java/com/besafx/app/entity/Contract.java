package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @GenericGenerator(
            name = "contractSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CONTRACT_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "contractSequenceGenerator")
    private Long id;

    private Long code;

    private Double paperFees;

    private Double commissionFees;

    private Double lawFees;

    private Double discount;

    private Double premiumAmount;

    private Double advancedAmount;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "sponsor1")
    private Customer sponsor1;

    @ManyToOne
    @JoinColumn(name = "sponsor2")
    private Customer sponsor2;

    @Temporal(TemporalType.TIMESTAMP)
    private Date writtenDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date premiumStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    //جدولة الأقساط بالتقويم الميلادي أو الهجري
    @Transient
    private String premiumCalendar;

    //نوع جدولة الأقساط
    @Transient
    private String premiumPeriod;

    //عدد الأقساط
    @Transient
    private Integer premiumCount;

    @OneToMany(mappedBy = "contract")
    private List<ContractProduct> contractProducts = new ArrayList<>();

    @OneToMany(mappedBy = "contract")
    private List<ContractPremium> contractPremiums = new ArrayList<>();

    @OneToMany(mappedBy = "contract")
    private List<ContractPayment> contractPayments = new ArrayList<>();

    @OneToMany(mappedBy = "contract")
    private List<ContractAttach> contractAttaches = new ArrayList<>();

    @JsonCreator
    public static Contract Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Contract contract = mapper.readValue(jsonString, Contract.class);
        return contract;
    }

    public Double getTotalPrice() {
        try {
            return this.contractProducts
                    .stream()
                    .mapToDouble(contractProduct -> contractProduct.getQuantity() * contractProduct.getUnitSellPrice())
                    .sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getTotalVat() {
        try {
            return this.contractProducts
                    .stream()
                    .mapToDouble(contractProduct -> contractProduct.getQuantity() * contractProduct.getUnitVat())
                    .sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getTotalPriceAfterDiscountAndVat() {
        try {
            return (this.getTotalPrice() + this.getTotalVat()) - this.discount;
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getTotalPremium() {
        try {
            return this.contractPremiums
                    .stream()
                    .mapToDouble(ContractPremium::getAmount)
                    .sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getRemainPremium() {
        try {
            return this.getTotalPriceAfterDiscountAndVat() - this.getTotalPremium();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getPaid() {
        try {
            return this.contractPayments
                    .stream()
                    .mapToDouble(ContractPayment::getAmount)
                    .sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getRemain() {
        try {
            return getTotalPriceAfterDiscountAndVat() - getPaid();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Date getLastPaymentDate() {
        try {
            return this.contractPayments.stream().map(ContractPayment::getDate).max(Date::compareTo).get();
        } catch (Exception ex) {
            return null;
        }
    }

    //حساب ربح العقد
    public Double getCapitalCash() {
        try {
            return this.contractProducts
                    .stream()
                    .mapToDouble(contractProduct -> contractProduct.getQuantity() * contractProduct.getProductPurchase().getUnitPurchasePrice())
                    .sum();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    //حساب الربح
    public Double getProfit() {
        try {
            return this.getTotalPrice() - this.getCapitalCash();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    //حساب نسبة الربح
    public Double getProfitPercentage() {
        try {
            return (this.getProfit() / this.getCapitalCash()) * 100;
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public String getContractProductsNames() {
        try {
            return String.join(", ", this.contractProducts
                    .stream()
                    .map(contractProduct -> contractProduct.getProductPurchase().getProduct().getName())
                    .toArray(String[]::new));
        } catch (Exception ex) {
            return "";
        }
    }

}
