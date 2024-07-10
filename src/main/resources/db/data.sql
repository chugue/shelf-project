--관리자--
Insert Into admin_tb(email, password, created_at, updated_at)
values ('shelf@naver.com', '1234', now(), null);

--사용자--
Insert Into user_tb (email, password, nick_name, phone, address, created_at, updated_at)
values ('psk@naver.com', '1234', 'psk', '010-2897-2345', '부산광역시 금정구', now(), null),
     ('sjm@naver.com', '1234', 'sjm', '010-3892-1445', '부산광역시 금정구', now(), null),
     ('kjh@naver.com', '1234', 'kjh', '010-1782-2345', '서울특별시 강남구', now(), null),
     ('ksh@naver.com', '1234', 'ksh', '010-2348-5422', '서울특별시 노원구', now(), null),
     ('jyj@naver.com', '1234', 'jyj', '010-8782-3472', '인천광역시 남동구', now(), null);



