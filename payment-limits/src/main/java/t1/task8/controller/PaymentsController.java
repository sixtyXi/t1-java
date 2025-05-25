package t1.task8.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t1.task8.dto.PaymentRequest;
import t1.task8.service.PaymentsService;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final PaymentsService paymentsService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/increase")
    public void increasePaymentAmount(@Valid @RequestBody PaymentRequest paymentRequest) {
        paymentsService.increaseAmount(paymentRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/decrease")
    public void decreasePaymentAmount(@Valid @RequestBody PaymentRequest paymentRequest) {
        paymentsService.decreaseAmount(paymentRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/reset")
    public void resetAllPaymentsAmount() {
        paymentsService.resetAllPayments();
    }
}
