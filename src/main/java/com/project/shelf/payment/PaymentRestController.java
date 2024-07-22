package com.project.shelf.payment;

import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.payment.PaymentRequestRecord.PaymentSaveReqDTO;
import com.project.shelf.payment.PaymentRequestRecord.PaymentUnscheduleDTO;
import com.project.shelf.payment.PaymentResponseRecord.PaymentListDTO;
import com.project.shelf.user.SessionUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PaymentRestController {

    private final PaymentService paymentService;
    private final HttpSession session;

    // 결제 내역
    @GetMapping("/app/payment-list")
    public ResponseEntity<?> paymentList() {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        PaymentListDTO resp = paymentService.paymentList(sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(resp));
    }

    // 사용자가 앱을 통해 취소
    @PostMapping("/app/unschedule")
    public ResponseEntity<?> unschedule(@Valid @RequestBody PaymentUnscheduleDTO reqDTO, Errors errors) {
        paymentService.unschedule(reqDTO.userId());
        return ResponseEntity.ok(new ApiUtil<>("이용권이 해지되었습니다."));
    }

    // 사용자가 앱을 통해 구매
    @PostMapping("/app/pay")
    public ResponseEntity<?> pay(@Valid @RequestBody PaymentSaveReqDTO reqDTO, Errors errors) {
        paymentService.save(reqDTO);
        System.out.println("결제 완료");
        return ResponseEntity.ok(new ApiUtil<>("결제가 완료되었습니다."));
    }
}
