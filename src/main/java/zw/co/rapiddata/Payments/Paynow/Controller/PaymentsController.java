package zw.co.rapiddata.Payments.Paynow.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.rapiddata.Payments.Paynow.Service.PaynowCart;
import zw.co.paynow.constants.MobileMoneyMethod;
import zw.co.paynow.core.Payment;
import zw.co.paynow.core.Paynow;
import zw.co.paynow.responses.MobileInitResponse;
//import zw.co.paynow.responses.StatusResponse;
import zw.co.paynow.responses.StatusResponse;
import zw.co.paynow.responses.WebInitResponse;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/paynow")
public class PaymentsController {

    private final Paynow paynow;

//    private final String pollUrl ;

    public PaymentsController(Paynow paynow
//            , String pollUrl
    ) {
        this.paynow = paynow;
//        this.pollUrl = pollUrl;
    }

    @GetMapping("/mobile")
    public ResponseEntity<?> mobilePayment(@RequestParam String mobileNumber, @RequestParam String invoiceNumber, @RequestBody List<PaynowCart> cart, Principal principal, @RequestParam MobileMoneyMethod mobileMoneyMethod) {
        //Create a new payment passing your own unique reference for that payment (e.g invoice id)
        //You can choose to pass in the email address of the customer whereby Paynow will attempt to auto login the customer using that email at the Paynow website
        Payment payment = paynow.createPayment(invoiceNumber, principal.getName());

        //When the payment is created, add the cart items your customer should pay for
        for (PaynowCart paynowCart : cart){
            payment.add(paynowCart.itemName(), paynowCart.price());
        }

        //Initiate the transaction so that the payment can be accepted
        //Response form Paynow will contain various information you could use. See javadocs for more
        MobileInitResponse response = paynow.sendMobile(payment, mobileNumber, mobileMoneyMethod);

        //Check if the request was successful
        if (response.success()) {

            //Instructions on how to make the mobile money payment
            String instructions = response.instructions();
            return ResponseEntity.status(HttpStatus.OK).body(instructions);

            // Get the poll url of the transaction so you can poll the transaction status later if required
//            String pollUrl = response.pollUrl();

        } else {
            // Something went wrong
            log.error(response.errors());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process request!");
        }
    }

    @GetMapping("/example/web")
    public ResponseEntity<?> webPayment(@RequestParam String invoiceNumber, @RequestBody List<PaynowCart> cart, @RequestParam String purpose, Principal principal) {

        //Create a new payment passing your own unique reference for that payment (e.g invoice id)
        //You can choose to pass in the email address of the customer whereby Paynow will attempt to auto login the customer using that email at the Paynow website
        Payment payment = paynow.createPayment(invoiceNumber, principal.getName());

        //When the payment is created, add the cart items your customer should pay for
        for (PaynowCart paynowCart : cart){
            payment.add(paynowCart.itemName(), paynowCart.price());
        }

        //Optionally add a description for the cart, otherwise a description will be generated automatically
        //Generated description will be shown to the user at Paynow website when making a payment
        payment.setCartDescription(purpose);

        //Initiate the transaction so that the payment can be accepted
        //Response form Paynow will contain various information you could use. See javadocs for more
        WebInitResponse response = paynow.send(payment);

        //Check if the request was successful
        if (response.success()) {

            //Get the url to redirect the user to so they can make payment
            String redirectURL = response.redirectURL();
            // Get the poll url of the transaction so you can poll the transaction status later if required
            return ResponseEntity.status(HttpStatus.OK).body(redirectURL);
//            String pollUrl = response.pollUrl();

        } else {
            // Something went wrong
            log.error(response.errors());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process request!");
        }

    }


    public boolean IsTransactionStatusPaid(String pollUrl) {

        //Checking if the payment has been paid by getting the detailed status
        StatusResponse status = paynow.pollTransaction(pollUrl);

        //Use the paid method to check if the transaction was paid
        if (status.paid()) {
            return true;
        } else {
            return false;
        }

    }




}
