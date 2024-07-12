--4. 책 --
INSERT INTO book_tb(author_id, title, path, book_intro, index, page_count, contents, category, publisher, created_at, updated_at)
values (1, '대화의 힘',  '/image/book/대화의 힘.jpg', '원하는 모든 것을 얻어내는 최고의 기술', null, 300, '습관과 대화 같은 보편적 주제를 새롭고 흥미로운 사례로 풀어내 읽는 재미를 선사하고 거기에 명확한 솔루션을 제시', '자기계발', '선규사', now(), null), --이 책은 ~저자와 ~ 리스트에 담김
       (2, '벌거벗은 한국사-영웅편', '/image/book/벌거벗은 한국사-영웅편.jpg', '방송에서 다룬 다양한 이야기 중 주요 한국사 영웅들의 이야기를 모아 『벌거벗은 한국사: 영웅편』에 담았다. ', '1부 시대의 난제를 극복한 영웅 2부 대한민국을 지켜낸 독립 영웅', 320, '한국사의 대표적읜 인물들은 다룬 책', '역사', '지민사', now(), null),
       (3, '아이가 없는 집',  '/image/book/아이가 없는 집.jpg', '살인 용의자가 된 주인공이 탐정과 함께 사건의 진실을 밝히는 책 입니다.', '1 2 3 4', 350, '살인 용의자가 된 주인공이 탐정과 함께 사건의 진실을 밝히는 책 입니다.', '소설', '성훈사', now(), null),
       (4, '아버지의 해방일지', '/image/book/아버지의 해방일지.jpg', '미스터리 같은 한 남자가 해쳐온 역사의 격량, 그 안에서 발견하는 끝끝내 강인한 우리의 인생', null, 280, ' 탁월한 언어적 세공으로 “한국소설의 새로운 화법을 제시”(문학평론가 정홍수)하기를 거듭해온 정지아는 한 시대를 풍미한 『빨치산의 딸』(1990) 이래로 다시 초심으로 돌아가 아버지 이야기를 다룬다. 소설은 ‘전직 빨치산’ 아버지의 죽음 이후 3일간의 시간만을 현재적 배경으로 다루지만, 장례식장에서 얽히고설킨 이야기를 따라가다보면 해방 이후 70년 현대사의 질곡이 생생하게 드러난다. ', '소설', '주혁사', now(), null),
       (5, '홍학의 자리', '/image/book/홍학의 자리.jpg', '홍학의 자리는 한 남자가 사체를 호수에 유기하는 장면으로 이야기의 문을 연다.', null, 270, '반전 스릴러의 내용이 담겨져 있다.', '소설', '선규사', now(), null);

--5. 위 시리스트--
INSERT INTO wishlist_tb(user_id, book_id, created_at, updated_at)
values (1, 1, now(), null),
       (2, 2, now(), null),
       (3, 3, now(), null),
       (4, 4, now(), null),
       (5, 5, now(), null);

--6. 책 읽기--
INSERT INTO book_history_tb(user_id, book_id, last_read_page, created_at, updated_at)
values (1, 1, 300, '2024-06-28', '2024-07-09'),
       (2, 2, 320, '2024-06-29', '2024-07-10'),
       (3, 3, 350, '2024-06-30', '2024-07-11'),
       (4, 4, 280, '2024-07-01', '2024-07-12'),
       (5, 5, 270, '2024-07-02', '2024-07-13'),
       (4, 3, 290, '2024-07-03', '2024-07-14'),
       (2, 3, 290, '2024-07-04', '2024-07-15'),
       (3, 1, 290, '2024-07-05', '2024-07-16');


