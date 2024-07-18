package com.project.shelf.payment;

import com.project.shelf.payment.PaymentResponseRecord.MonthlySaleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void findSalesByMonth_test(){
        // given

        // when
        List<MonthlySaleDTO> resp = paymentRepository.findSalesByMonth();

        // eye
        resp.forEach(r -> System.out.println("월별 매출: " + r));

        // then

    }

    @Test
    public void findByUserId_test(){
        // given
        Integer userId = 1;

        // when
        Integer resp = paymentRepository.findByUserId(userId);

        // eye
        System.out.println("구독 개월 수 : " + resp);

        // then

    }
}
