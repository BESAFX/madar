package com.besafx.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractPremium implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static Logger LOG = LoggerFactory.getLogger(ContractPremium.class);

    @GenericGenerator(
            name = "contractPremiumSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "CONTRACT_PREMIUM_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "contractPremiumSequenceGenerator")
    private Long id;

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "contract")
    private Contract contract;

    @OneToMany(mappedBy = "contractPremium")
    private List<ContractPayment> contractPayments = new ArrayList<>();

    @JsonCreator
    public static ContractPremium Create(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ContractPremium contractPremium = mapper.readValue(jsonString, ContractPremium.class);
        return contractPremium;
    }

    public Double getPaid() {
        try {
            double totalPaid = this.contract.getPaid();
            double currentPaid = 0.0;
            if (totalPaid == 0) {
                return 0.0;
            }
            Iterator<ContractPremium> contractPremiumListIterator = this.contract
                    .getContractPremiums()
                    .stream()
                    .sorted(Comparator.comparing(ContractPremium::getDueDate))
                    .iterator();
            while (contractPremiumListIterator.hasNext()) {
                ContractPremium contractPremium = contractPremiumListIterator.next();
                if (totalPaid > contractPremium.getAmount()) {
                    currentPaid = contractPremium.getAmount();
                }else{
                    currentPaid = totalPaid;
                }
                totalPaid = totalPaid - currentPaid;
                if(contractPremium.getId().equals(this.getId())){
                    break;
                }
            }
            return currentPaid;
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getRemain() {
        try {
            return this.amount - this.getPaid();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public String getState() {
        try {
            double remain = this.getRemain();
            if (remain == 0) {
                return "تم السداد";
            } else if (remain > 0 && remain < this.amount) {
                return "سداد جزئي";
            } else if (this.dueDate.after(new Date())) {
                return "غير مستحق";
            } else if (remain == this.amount) {
                return "غير مسدد";
            } else {
                return "غير معروف";
            }
        } catch (Exception ex) {
            return "";
        }
    }
}
