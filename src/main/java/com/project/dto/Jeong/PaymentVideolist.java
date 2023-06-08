package com.project.dto.Jeong;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class PaymentVideolist {
    
    BigInteger paymentlistno;

    BigInteger profileno;

    BigInteger videocode;

    String title;

    Date regdate;

    BigInteger imgno;
    
}
