package t1.task8.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import t1.task8.dto.LimitChangeRequest;
import t1.task8.service.LimitsService;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class LimitsController {

    private final LimitsService limitsService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/increase")
    public void increasePaymentAmount(@Valid @RequestBody LimitChangeRequest limitChangeRequest) {
        limitsService.increaseLimit(limitChangeRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/decrease")
    public void decreasePaymentAmount(@Valid @RequestBody LimitChangeRequest limitChangeRequest) {
        limitsService.decreaseLimit(limitChangeRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/reset")
    public void resetAllPaymentsAmount() {
        limitsService.resetAllLimits();
    }
}
