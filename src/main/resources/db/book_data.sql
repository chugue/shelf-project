
INSERT INTO book_tb (author_id, title, path, page_count, book_intro, content_intro, category, publisher, epub_file, created_at, updated_at)
VALUES
    (1, '대화의 힘', '/image/book/대화의_힘.jpg', '300', '대화의 중요성과 기술을 다룬 책입니다.', '대화의 힘을 통해 원하는 것을 얻는 방법을 설명합니다.', '자기계발', '선규사', '/image/epub/대화의_힘.epub', NOW(), NOW()),
    (2, '벌거벗은 한국사-영웅편', '/image/book/벌거벗은_한국사(영웅편).jpg', '320', '한국사의 영웅들을 다룬 책입니다.',  '한국사의 대표적인 영웅들의 이야기를 담았습니다.', '역사', '지민사', '/image/epub/벌거벗은_한국사(영웅편).epub', NOW(), NOW()),
    (3, '아이가 없는 집', '/image/book/아버지의_해방일지.jpg', '350', '살인 용의자의 이야기를 다룬 소설입니다.',  '주인공이 탐정과 함께 사건의 진실을 밝히는 내용을 담았습니다.', '소설', '성훈사', '/image/epub/아이가_없는_집.epub', NOW(), NOW()),
    (4, '아버지의 해방일지', '/image/book/아이가_없는_집.jpg', '280', '한 남자의 인생 이야기를 다룬 책입니다.',  '역사의 격량을 헤쳐온 한 남자의 이야기를 담았습니다.', '소설', '주혁사', '/image/epub/아버지의_해방일지.epub', NOW(), NOW()),
    (5, '홍학의 자리', '/image/book/홍학의_자리.jpg', '270', '반전 스릴러를 다룬 소설입니다.',  '사체를 유기하는 장면으로 시작되는 반전 스릴러입니다.', '소설', '선규사', '/image/epub/홍학의_자리.epub', NOW(), NOW()),
--     IT
    (6, '만들면서 배우는 플러터 앱 프로그래밍', '/image/book/만들면서_배우는_플러터_앱_프로그래밍.jpg', '388', '최신 버전의 플러터 & Dart 핵심 문법 익힌 후 6가지 앱과 가지 웹을 제작해보고, RiverPod를 사용하여 상태 관리 방법 등 고급 기술도 자세하게 설명하였다!', '이 책의 핵심 구성은 플러터 & 다트 핵심 문법을 익힌 후 스토어 앱, 레시피 앱, 프로필 앱', 'IT', '앤써북', '/image/epub/대화의_힘.epub', NOW(), NOW()),
    (6, '클라우드 서비스 개발자를 위한 AWS로 구현하는 CI/CD 배포 입문', '/image/book/클라우드_서비스_개발자를_위한_AWS로_구현하는_CICD_배포_입문.jpg', '300', '이 책은 신입 개발자부터 실제 서비스 구축 경험이 없는 모든 개발자를 위한 클라우드 서비스 개발 실무 밀착형 입문서!',  '이 책은 깃허브에 이미 구현된 코드와 예시와 함께 자세히 설명되어 있는 개념들로 훨씬 이해하기 쉽고 재미있게 따라할 수 있도록 구성하였다.', 'IT', '앤써북', '/image/epub/대화의_힘.epub', NOW(), NOW()),
    (6, 'IoT 사물인터넷을 위한 라즈베리파이 4 정석', '/image/book/IoT_사물인터넷_ 위한_라즈베리파이_4_정석.jpg', '416', '왕초보도 라즈베리파이를 이용해 다양한 IoT를 직접 개발할 수 있도록 전기전자 기초 원리부터 센서제어 및 나만의 가상비서 만들기 등 초수/중수/고수 예제 실습과 실전 프로젝트까지 단계별로 구성하였다',  '이 책은 부품 및 전자회로가 어떻게 동작하는지와 IoT 동작을 위해 어떻게 코딩해야 하는지를 도식화와 한 줄 한 줄 등으로 진짜 친절하게 설명하였다.', 'IT', '앤써북', '/image/epub/대화의_힘.epub', NOW(), NOW()),
    (7, '나의 첫 노션(Notion) 마스터북', '/image/book/나의_첫_노션(Notion)_마스터북.jpg', '340', '노션AI를 개인 비서로 활용, 취업·이직을 위한 포트폴리오 제작, 데이터베이스 정복, 비대면 협업·프로젝트 관리까지 하루 10분, 4주 만에 노션 마스터하기!',  '이 책은 매년 2,000명이 넘는 온·오프라인 수강생들이 강의 수강 후 제출하는 후기를 바탕으로 저자가 하루 10분, 4주 만에 빠르게 완성할 수 있도록 구성하였다.', 'IT', '비즈니스북', '/image/epub/대화의_힘.epub', NOW(), NOW()),
    (8, '요즘 우아한 개발', '/image/book/요즘_우아한_개발.jpg', '360', '★ 급성장하는 요즘 IT 서비스 어떻게 개발할까요? ★ ‘우아한형제들’의 ‘배달의민족’ 개발 이야기에서 확인하세요!',  '이 책은 블로그의 글을 엄선해 우형이 성장하며 겪고 헤쳐간 온보딩, 개발, 문화, 이슈 관리 이야기를 담았습니다.', 'IT', '골든래빗(주)', '/image/epub/대화의_힘.epub', NOW(), NOW());
--     역사
--     (9, '컬처, 문화로 쓴 세계사', 'image/book/컬처_문화로쓴세계사.jpg', '428', '**학문의 시원 바그다드,최초의 순례자 현장법사,모든 영웅의 아버지 호메로스세계사를 바꾼 4천 년 문화를 집대성하다**', '하버드대 마틴 푸크너의 인류 문화 오디세이. 모든 영웅의 원형을 만든 호메로스 서사시에서 한강과 마거릿 애트우드가 함께할 2114년 미래의 도서관까지, 인류 문화의 15가지 이야기를 통해 인간이 어떻게 세계사의 결정적 장면들을 만들었는지 보여준다. 인간은 자기 존재의 의미를 표현하기 위해 먼 곳의 문화에 눈길을 돌렸고, 그것은 문명이 거대한 발걸음을 내딛는 동력이 되었다. 편협하고 극단적인 민족주의가 세계 곳곳에 도래하는 지금, 인류의 과거와 현재를 재정의하고 우리가 선택할 수 있는 최선의 미래를 제시하는 책.', '역사', '어크로스', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (10, '글이 만든 세계', '/image/book/글이_만든_세계.jpg', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     종교
--     (9, '전부를 걸어라', '/image/book/전부를_걸어라.jpg', '280','“진짜를 발견했는데 어떻게 가만히 있겠는가?”천국을 위해서 이 땅의 것을 포기하는 사람은절대로 어리석은 사람이 아니다자신의 전부를 거는 사람이 하나님나라에 들어간다제자광성교회 박한수 목사의 올인 메시지', '“오늘 나는 무엇을 드려서 천국을 사는가?” 우리는 반드시 두 가지를 수행해야 한다. 먼저는 구원 얻을 믿음을 획득하는 것이고, 그다음 그 믿음을 지켜서 빼앗기지 않도록 믿음으로 살아가는 것이다. 저자는 믿음의 결국, 우리 믿음에 목적은 우리 영혼이 구원을 받는 것이라는 성경의 말씀대로 먼저는 교회에 나와 복음을 듣고 거듭나야 하는 성도들을 위해 원색적인 복음을 전하고 있다. 또한 저자는 악하고 음란한 세대와 말세지말의 현실을 살아가는 참된 신자가 끝까지 믿음의 선한 싸움을 할 수 있도록 세상을 이기는 믿음을 북돋는다. 그래서 우리 신앙의 자유를 위해 포괄적차별금지법 등 악법의 제정에 반대하여 목소리를 내는 것이다. 결국 영혼의 구원함을 얻는 믿음도, 마지막까지 믿음을 지켜내는 일도 전부를 드려야 가능하다는 것이 이 책의 요체이다. 우리의 믿음을 빼앗아 영원한 생명을 도둑질하고 죽이고, 결국은 한 영혼이라도 더 지옥으로 끌어가 멸망시키고자 하는 마귀에 대적할 믿음으로 무장되기 원하는 분들에게 이 책을 강력히 추천한다.', '종교', '규장', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (10, '의사, 주석증', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', '소북소북', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),
--     (n, 'title', 'image/book/', 'page_count','book_intro', 'content_intro', 'category', 'publisher', '/image/epub/대화의_힘.epub', NOW(), NOW()),


--5. 위 시리스트--
INSERT INTO wishlist_tb(user_id, book_id, created_at, updated_at)
values (1, 1, now(), NOW()),
       (1, 2, now(), NOW()),
       (1, 3, now(), NOW()),
       (1, 4, now(), NOW()),
       (1, 5, now(), NOW()),
       (2, 1, now(), NOW()),
       (2, 2, now(), NOW()),
       (2, 3, now(), NOW()),
       (3, 4, now(), NOW()),
       (3, 5, now(), NOW());


--6. 책 읽기--
INSERT INTO book_history_tb(user_id, book_id, last_read_page, created_at, updated_at)
values (1, 1, 300, '2024-06-28', '2024-07-09'),
       (2, 2, 320, '2024-06-29', '2024-07-10'),
       (3, 3, 350, '2024-06-30', '2024-07-11'),
       (4, 4, 280, '2024-07-01', '2024-07-12'),
       (5, 5, 270, '2024-07-02', '2024-07-13'),
       (4, 3, 290, '2024-07-03', '2024-07-14'),
       (2, 3, 290, '2024-07-04', '2024-07-15'),
       (3, 1, 290, '2024-07-05', '2024-07-16'),
       (1, 2, 300, now(), now()),
       (1, 3, 300, now(), now()),
       (1, 4, 270, now(), now()),
       (1, 5, 250, now(), now()),
       (2, 1, 250, now(), now()),
       (3, 1, 250, now(), now()),
       (4, 1, 250, now(), now()),
       (2, 4, 250, now(), now()),
       (3, 4, 250, now(), now());





