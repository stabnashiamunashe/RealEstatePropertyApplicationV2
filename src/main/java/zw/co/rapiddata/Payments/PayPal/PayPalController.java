package zw.co.rapiddata.Payments.PayPal;

import org.springframework.web.servlet.view.RedirectView;
import zw.co.rapiddata.Payments.PayPal.PaymentInformation.PaymentInformation;
import zw.co.rapiddata.Payments.PayPal.PaymentInformation.PaymentInformationService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    private final PayPalService paypalService;

    private final PaymentInformationService paymentInformationService;

    public PayPalController(PayPalService paypalService, PaymentInformationService paymentInformationService) {
        this.paypalService = paypalService;
        this.paymentInformationService = paymentInformationService;
    }

    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(@RequestBody PaymentRequest paymentRequest) throws PayPalRESTException {
        try {
            Payment payment = paypalService.createPayment(paymentRequest.getPrice(), paymentRequest.getCurrency(),
                    paymentRequest.getMethod(), paymentRequest.getIntent(),
                    paymentRequest.getDescription(),
                    "http://localhost:8080/api/paypal/cancel/payment",
                    "http://localhost:8080/api/paypal/complete/payment");
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(link.getHref());
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find approval_url");
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/complete/payment")
    public RedirectView completePayment(@RequestParam("paymentId") String paymentId,
                                             @RequestParam("PayerID") String payerId) throws PayPalRESTException {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")) {
                    PaymentInformation paymentInformation = new PaymentInformation();
                    paymentInformation.setIntent(payment.getIntent());
                    paymentInformation.setPaymentMethod(payment.getPayer().getPaymentMethod());
                    paymentInformation.setEmail(payment.getPayer().getPayerInfo().getEmail());
                    paymentInformation.setFirstName(payment.getPayer().getPayerInfo().getFirstName());
                    paymentInformation.setLastName(payment.getPayer().getPayerInfo().getLastName());
                    paymentInformation.setDescription(payment.getTransactions().get(0).getDescription());
                    paymentInformation.setPrice(Double.valueOf(payment.getTransactions().get(0).getAmount().getTotal()));
                    paymentInformation.setCurrency(payment.getTransactions().get(0).getAmount().getCurrency());
                    paymentInformationService.SavePaymentInformation(paymentInformation);
                return new RedirectView("https://auspicex.com/agentAccount/dashboard");
//                return ResponseEntity.status(HttpStatus.OK).body("Payment completed successfully");
            }
            return new RedirectView("https://auspicex.com/error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not complete payment");
           } catch (PayPalRESTException e) {
            return new RedirectView("https://auspicex.com/error");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/cancel/payment")
    public ResponseEntity<?> cancelPayment() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment cancelled");
    }
}

