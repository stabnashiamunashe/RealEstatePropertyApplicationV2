package zw.co.rapiddata.Payments.Paynow.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaynowPaymentUpdateRequest {

    private String reference;
    private Double amount;
    private String paynowreference;
    private String pollurl;
    private String status;
    private String hash;
}
