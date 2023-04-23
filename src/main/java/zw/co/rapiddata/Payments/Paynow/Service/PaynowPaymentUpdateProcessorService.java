package zw.co.rapiddata.Payments.Paynow.Service;

import org.springframework.stereotype.Service;

@Service
public class PaynowPaymentUpdateProcessorService {

    public void processPaynowUpdate(PaynowPaymentUpdateRequest request) {
        //your logic here on what to do when payment is received
        //maybe save the payment information in database?
        //maybe send the customer an SMS notifying that payment has been received?
    }

}
