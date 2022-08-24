package com.pension.pms.pensionmanagement.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPension {
    private String aadhaarNumber;
    private String name;
    private Date dob;
    private String pan;
    private long salaryEarned;
    private long allowances;
    private String pensionType;
    private String bankType;
    private double pensionAmount;
    private double serviceCharge;
}
