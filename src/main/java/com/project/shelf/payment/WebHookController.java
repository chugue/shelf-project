package com.project.shelf.payment;

import com.project.shelf.payment.PaymentRequestRecord.WebHookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WebHookController {

    private final PaymentService paymentService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody WebHookDTO respDTO) {
        String[] parts = respDTO.impUid().split("_");

        if("imps".equals(parts[0])) { // 최초 결제가 아니라면 (최초 결제는 imp, 최초결제가 아니면 imps)
            if ("failed".equals(respDTO.status())) {
                System.out.println("[webhook] 예약 결제 실패: " + respDTO);
            } else if ("paid".equals(respDTO.status())) {
                System.out.println("[webhook] 예약 결제 성공");
                paymentService.saveScheduledPayment(respDTO);
            }
        }

        return ResponseEntity.ok("Webhook received");
    }
}