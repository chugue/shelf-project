package com.project.shelf.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shelf.payment.PaymentResponseRecord.PaymentDetailDTO;
import com.project.shelf.payment.PaymentResponseRecord.PortOneScheduledDTO;
import com.project.shelf.payment.PaymentResponseRecord.PortOneTokenDTO;
import com.project.shelf.payment.PaymentResponseRecord.PortOneUnscheduledDTO;
import com.project.shelf.sub_types.SubTypes;
import com.project.shelf.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PortOneService {

    private final RestTemplate rt = new RestTemplate();

    @Value("${iamport.imp_key}")
    private String impKey;
    @Value("${iamport.imp_secret}")
    private String impSecret;


    // 예약 취소
    @Transactional
    public void unschedule(String customerUid) {
        // access 토큰 발급
        String accessToken = getAccessToken();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken); // 인증 및 권한 부여

        // 바디 설정
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        body.put("customer_uid", customerUid);

        // 헤더 + 바디
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        // API 요청
        ResponseEntity<PortOneUnscheduledDTO> response = rt.exchange(
                "https://api.iamport.kr/subscribe/payments/unschedule",
                HttpMethod.POST,
                request,
                PortOneUnscheduledDTO.class
        );

        System.out.println("unschedule : " + response);

    }


    // 결제내역 단건 조회
    @Transactional
    public ResponseEntity<PaymentDetailDTO> getPaymentDetail(String impUid) {
        // access 토큰 발급
        String accessToken = getAccessToken();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken); // 인증 및 권한 부여

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers); // GET이라서 바디가 필요 없다.

        // API 요청
        ResponseEntity<PaymentDetailDTO> response = rt.exchange(
                "https://api.iamport.kr/payments/" + impUid,
                HttpMethod.GET,
                request,
                PaymentDetailDTO.class);

        System.out.println("response : " + response.getBody().toString());

        return response;
    }

    // 예약
    @Transactional
    public void schedule(User user, SubTypes subTypes) {
        // access 토큰 발급
        String accessToken = getAccessToken();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken); // 인증 및 권한 부여

        // 요청 데이터 설정
        Calendar calendar = Calendar.getInstance();
        String merchantUid = UUID.randomUUID().toString();
        calendar.add(Calendar.SECOND, 2); // 테스트라서 한 달 뒤로 설정 안 함
//            calendar.add(Calendar.MONTH, 1); // 한 달 뒤 결제

        Date tenSecondLater = calendar.getTime();
        long scheduledAt = tenSecondLater.getTime() / 1000L;

        // 바디 설정
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode body = mapper.createObjectNode();
        ArrayNode schedules = mapper.createArrayNode();
        ObjectNode schedule = mapper.createObjectNode();

        schedule.put("merchant_uid", merchantUid);
        schedule.put("schedule_at", scheduledAt);
        schedule.put("currency", "KRW");
        schedule.put("amount", 100); // 테스트라서... 원래는 subTypes.getAmount()
        schedule.put("name", subTypes.getSubName());
        schedule.put("buyer_email", user.getEmail());

        schedules.add(schedule);

        body.put("customer_uid", user.getCustomerUid());
        body.set("schedules", schedules);

        // 헤더 + 바디
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        // API 요청
        ResponseEntity<PortOneScheduledDTO> response = rt.exchange(
                "https://api.iamport.kr/subscribe/payments/schedule",
                HttpMethod.POST,
                request,
                PortOneScheduledDTO.class
        );

        System.out.println(response);
    }


    // access 토큰 발급
    @Transactional
    public String getAccessToken() {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 바디 설정
        ObjectMapper mapper = new ObjectMapper(); // JSON과 Java 객체간의 변환을 처리하는 역할
        ObjectNode body = mapper.createObjectNode(); // ObjectNode : JSON 객체를 표현하는 Jackson의 클래스
        body.put("imp_key", impKey);
        body.put("imp_secret", impSecret);

        // 헤더 + 바디
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers); // HttpEntity : 요청 본문과 헤더를 함께 보낼 수 있는 객체


        // API 요청
        ResponseEntity<PortOneTokenDTO> response = rt.exchange( // ResponseEntity는 HTTP 응답을 나타내며 HTTP 상태코드, 헤더, 바디를 담을 수 있다.
                "https://api.iamport.kr/users/getToken",
                HttpMethod.POST,
                request,
                PortOneTokenDTO.class // 응답 타입 설정
        );


        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) { // HTTP 응답 상태 코드가 200번대 + 응답 본문이 비어있지 않다면
            System.out.println("accessToken: " + response.getBody().response().accessToken());
            return response.getBody().response().accessToken(); // accessToken return
        }

        throw new RuntimeException("Token 가져오는 것을 실패했습니다.");
    }

}







