
INSERT INTO book_tb (author_id, title, path, page_count, book_intro, content_intro, category, publisher, epub_file, created_at, updated_at)
VALUES
    (1, '대화의 힘', '/image/book/대화의_힘.jpg', '300', '대화의 중요성과 기술을 다룬 책입니다.', '대화의 힘을 통해 원하는 것을 얻는 방법을 설명합니다.', '자기계발', '선규사', '/image/epub/대화의 힘.epub', NOW(), NOW()),
    (2, '벌거벗은 한국사-영웅편', '/image/book/벌거벗은_한국사(영웅편).jpg', '320', '한국사의 영웅들을 다룬 책입니다.',  '한국사의 대표적인 영웅들의 이야기를 담았습니다.', '역사', '지민사', '/image/epub/벌거벗은 한국사-영웅편.epub', NOW(), NOW()),
    (3, '아이가 없는 집', '/image/book/아버지의_해방일지.jpg', '350', '살인 용의자의 이야기를 다룬 소설입니다.',  '주인공이 탐정과 함께 사건의 진실을 밝히는 내용을 담았습니다.', '소설', '성훈사', '/image/epub/아이가 없는 집.epub', NOW(), NOW()),
    (4, '아버지의 해방일지', '/image/book/아이가_없는_집.jpg', '280', '한 남자의 인생 이야기를 다룬 책입니다.',  '역사의 격량을 헤쳐온 한 남자의 이야기를 담았습니다.', '소설', '주혁사', '/image/epub/아버지의 해방일지.epub', NOW(), NOW()),
    (5, '홍학의 자리', '/image/book/홍학의_자리.jpg', '270', '반전 스릴러를 다룬 소설입니다.',  '사체를 유기하는 장면으로 시작되는 반전 스릴러입니다.', '소설', '선규사', '/image/epub/홍학의 자리.epub', NOW(), NOW());


--5. 위 시리스트--
INSERT INTO wishlist_tb(user_id, book_id, created_at, updated_at)
values (1, 1, now(), NOW()),
       (2, 2, now(), NOW()),
       (3, 3, now(), NOW()),
       (4, 4, now(), NOW()),
       (5, 5, now(), NOW());

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



