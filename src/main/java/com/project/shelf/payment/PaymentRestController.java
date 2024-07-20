package com.project.shelf.payment;

import com.project.shelf.payment.PaymentRequestRecord.PaymentSaveReqDTO;
import com.project.shelf.payment.PaymentRequestRecord.PaymentUnscheduleDTO;
import com.project.shelf.user.SessionUser;
import jakarta.servlet.http.HttpSession;
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
    private final HttpSession session;

    // 사용자가 앱을 통해 취소
    @PostMapping("/unschedule")
    public ResponseEntity<?> unschedule() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        paymentService.unschedule(sessionUser.getId());
        return ResponseEntity.ok(Map.of("message", "이용권이 해지되었습니다."));
    }

    // 사용자가 앱을 통해 구매
    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody PaymentSaveReqDTO reqDTO) {
        paymentService.save(reqDTO);
        return ResponseEntity.ok(Map.of("message", "결제가 완료되었습니다."));
    }
}
