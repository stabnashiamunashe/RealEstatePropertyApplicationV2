package zw.co.rapiddata.Payments.PaymentInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInformationService {

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    public String SavePaymentInformation(PaymentInformation paymentInformation){
        paymentInformationRepository.save(paymentInformation);
        return "Payment Saved";
    }
}
