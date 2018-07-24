package com.besafx.app.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompanyOptions {

    private String yamamahUserName;

    private String yamamahPassword;

    private Double vatFactor;

    private String logo;

    private String background;

    private String reportTitle;

    private String reportSubTitle;

    private String reportFooter;
}
