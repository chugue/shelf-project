--관리자--
Insert Into admin_tb(email, password, created_at, updated_at)
values ('shelf@naver.com', '1234', now(), null);

--사용자--
INSERT INTO user_tb (email, password, nick_name, phone, address, created_at, updated_at, status)
VALUES ('psk@naver.com', '1234', 'psk', '010-2897-2345', '부산광역시 금정구', NOW(), NOW(), true),
       ('sjm@naver.com', '1234', 'sjm', '010-3892-1445', '부산광역시 금정구', NOW(), NOW(), true),
       ('kjh@naver.com', '1234', 'kjh', '010-1782-2345', '서울특별시 강남구', NOW(), NOW(), true),
       ('ksh@naver.com', '1234', 'ksh', '010-2348-5422', '서울특별시 노원구', NOW(), NOW(), true),
       ('jyj@naver.com', '1234', 'jyj', '010-8782-3472', '인천광역시 남동구', NOW(), NOW(), true),
       ('ysh@naver.com', '1234', 'ysh', '010-2452-1472', '인천광역시 남동구', NOW(), NOW(), false);




