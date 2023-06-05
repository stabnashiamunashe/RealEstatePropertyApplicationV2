package zw.co.rapiddata.Payments.Paynow.Controller;

import zw.co.rapiddata.Payments.Paynow.Service.PaynowPaymentUpdateProcessorService;
import zw.co.rapiddata.Payments.Paynow.Service.PaynowPaymentUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaynowUpdateReceiverController {

    private final PaynowPaymentUpdateProcessorService updateProcessor;

    public PaynowUpdateReceiverController(PaynowPaymentUpdateProcessorService updateProcessor) {
        this.updateProcessor = updateProcessor;
    }

    //The REST endpoint which you should set as the result url
    //Change the value of RequestMapping if you prefer a different name
    //Add your implementation with business logic in com.yourdomain.services.PaynowPaymentUpdateProcessorServiceImpl
    @RequestMapping(
            value = "/paymentupdatereceiver",
            method = RequestMethod.POST,
            produces = {MediaType.ALL_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public void processPaynowUpdate(@RequestBody PaynowPaymentUpdateRequest requestFromPaynow) {
        log.info("Payment update received from Paynow: " + requestFromPaynow);
        updateProcessor.processPaynowUpdate(requestFromPaynow);
    }


}