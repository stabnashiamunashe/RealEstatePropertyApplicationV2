package zw.co.rapiddata.Payments.PayPal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

}
