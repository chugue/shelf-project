
package com.project.shelf.payment;


import com.project.shelf._core.erros.exception.Exception400;
import com.project.shelf.payment.PaymentRequestRecord.PaymentSaveReqDTO;
import com.project.shelf.sub_types.SubTypes;
import com.project.shelf.sub_types.SubTypesRepository;
import com.project.shelf.user.User;
import com.project.shelf.user.UserRepository;
import com.project.shelf.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final SubTypesRepository subTypesRepository;

    // 비인증 결제(최초)
    @Transactional
    public void save(PaymentSaveReqDTO paymentDTO) {
        User user = userRepository.findByEmail(paymentDTO.buyerEmail())
                .orElseThrow(() -> new Exception400("존재하지 않는 회원입니다."));

        SubTypes subTypes = subTypesRepository.findById(1) // 지금은 이용권 1개월만 사용하므로 고정값을 넣음
                .orElseThrow(() -> new Exception400("존재하지 않는 이용권입니다."));

        Payment payment = Payment.builder()
                .user(user)
                .subTypes(subTypes)
                .orderDate(paymentDTO.orderDate())
                .amount(paymentDTO.amount())
                .impUid(paymentDTO.impUid())
                .payMethod(paymentDTO.payMethod())
                .merchantUid(paymentDTO.merchantUid())
                .name(paymentDTO.name())
                .cardName(paymentDTO.cardName())
                .customerUid(paymentDTO.customerUid())
                .subStatus(Payment.SubscriptionPayment.완료)
                .build();

        paymentRepository.save(payment);
        System.out.println("DB에 저장 성공");
    }

}
