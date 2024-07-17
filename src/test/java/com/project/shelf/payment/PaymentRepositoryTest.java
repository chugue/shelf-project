package com.project.shelf.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

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
