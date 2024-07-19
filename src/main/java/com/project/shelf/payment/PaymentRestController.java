package com.project.shelf.payment;

import com.project.shelf.payment.PaymentRequestRecord.PaymentSaveReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PaymentRestController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody PaymentSaveReqDTO reqDTO) {
        paymentService.save(reqDTO);
        return ResponseEntity.ok(Map.of("message", "결제가 완료되었습니다."));
    }
}
