
package com.project.shelf.payment;


import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf._core.erros.exception.Exception401;
import com.project.shelf._core.erros.exception.Exception404;
import com.project.shelf.payment.PaymentRequestRecord.PaymentSaveReqDTO;
import com.project.shelf.payment.PaymentRequestRecord.WebHookDTO;
import com.project.shelf.payment.PaymentResponseRecord.PaymentDetailDTO;
import com.project.shelf.payment.PaymentResponseRecord.PaymentListDTO;
import com.project.shelf.sub_types.SubTypes;
import com.project.shelf.sub_types.SubTypesRepository;
import com.project.shelf.user.User;
import com.project.shelf.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import static com.project.shelf._core.util.Formatter.number;


@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final SubTypesRepository subTypesRepository;
    private final PortOneService portOneService;


    // 결제 내역
    public PaymentListDTO paymentList(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception400("존재하지 않는 회원입니다."));

        List<Payment> paymentList = paymentRepository.findByUserId(userId);
        List<PaymentListDTO.PaymentDTO> paymentDTOList = paymentList.stream().map(payment -> PaymentListDTO.PaymentDTO.builder()
                .paymentId(payment.getId())
                .subTypePeriod(String.valueOf(payment.getSubTypes().getSubPeriod()))
                .paymentStatus(String.valueOf(payment.getSubStatus()))
                .paymentAt(Instant.ofEpochSecond(Long.parseLong(payment.getOrderDate()))
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .amount(number(payment.getAmount()))
                .build()).toList();;

        return new PaymentListDTO(paymentList.size(), paymentDTOList);
    }

    // 예약 취소
    @Transactional
    public void unschedule(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception401("존재하지 않는 회원입니다."));

        // 예약 취소
        portOneService.unschedule(user.getCustomerUid());
        System.out.println("예약 취소 성공");
    }

    // 비인증 결제(최초 x)
    @Transactional
    public void saveScheduledPayment(WebHookDTO paymentDTO) {
        // impUid로 결제 내역 조회
        ResponseEntity<PaymentDetailDTO> resp = portOneService.getPaymentDetail(paymentDTO.impUid());

        User user = userRepository.findByEmail(resp.getBody().response().buyerEmail())
                .orElseThrow(() -> new Exception401("존재하지 않는 회원입니다."));

        SubTypes subTypes = subTypesRepository.findById(1) // 지금은 이용권 1개월만 사용하므로 고정값을 넣음
                .orElseThrow(() -> new Exception404("존재하지 않는 이용권입니다."));

        // DB에 저장
        Payment payment = Payment.builder()
                .user(user)
                .subTypes(subTypes)
                .orderDate(resp.getBody().response().paidAt())
                .amount(resp.getBody().response().amount())
                .impUid(paymentDTO.impUid())
                .payMethod(resp.getBody().response().payMethod())
                .merchantUid(resp.getBody().response().merchantUid())
                .name(resp.getBody().response().name())
                .cardName(resp.getBody().response().cardName())
                .cardNumber(resp.getBody().response().cardNumber())
                .customerUid(resp.getBody().response().customerUid())
                .subStatus(Payment.SubscriptionPayment.완료)
                .build();

        paymentRepository.save(payment);
        System.out.println("예약 결제 내역 DB에 저장 성공");

//        if (subTypes.getSubPeriod().equals(SubTypes.SubscriptionPeriod.일년)){
//            Integer paymentCount = paymentRepository.findByUserId(user.getId());
//            if(paymentCount == 12){
//                // ...
//            }else{
//                portOneService.schedule(payment, user.getEmail(), subTypes);
//                System.out.println("예약 성공");
//                // ...
//            }
//        }

        portOneService.schedule(user, subTypes);
        System.out.println("예약 성공");

    }

    // 비인증 결제(최초)
    @Transactional
    public void save(PaymentSaveReqDTO paymentDTO) {
        User user = userRepository.findByEmail(paymentDTO.buyerEmail())
                .orElseThrow(() -> new Exception401("존재하지 않는 회원입니다."));

        SubTypes subTypes = subTypesRepository.findById(1) // 지금은 이용권 1개월만 사용하므로 고정값을 넣음
                .orElseThrow(() -> new Exception404("존재하지 않는 이용권입니다."));

        Payment payment = Payment.builder()
                .user(user)
                .subTypes(subTypes)
                .orderDate(paymentDTO.paidAt())
                .amount(paymentDTO.paidAmount())
                .impUid(paymentDTO.impUid())
                .payMethod(paymentDTO.payMethod())
                .merchantUid(paymentDTO.merchantUid())
                .name(paymentDTO.name())
                .cardName(paymentDTO.cardName())
                .cardNumber(paymentDTO.cardNumber())
                .customerUid(paymentDTO.customerUid())
                .subStatus(Payment.SubscriptionPayment.완료)
                .build();

        user.setCustomerUid(paymentDTO.customerUid());
        user.setStatus(true);

        paymentRepository.save(payment);
        System.out.println("DB에 저장 성공");

        portOneService.schedule(user, subTypes);
        System.out.println("예약 성공");

    }

}
