--DROP TABLE IF EXISTS Likes CASCADE;
--DROP TABLE IF EXISTS Check_watched CASCADE;
--DROP TABLE IF EXISTS Videos CASCADE;
--DROP TABLE IF EXISTS Episode CASCADE;
--DROP TABLE IF EXISTS Users CASCADE;
--DROP TABLE IF EXISTS Subscription CASCADE;
--
---- +. 구독 테이블 (Subscription)
--CREATE TABLE Subscription (
--    id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    user_id VARCHAR(255) UNIQUE,
--    start_date DATE NOT NULL,
--    end_date DATE NOT NULL,
--    subscription_type VARCHAR(50) NOT NULL
--);
--
---- 1. 사용자 테이블 (Users)
--CREATE TABLE Users (
--    user_id VARCHAR(255) PRIMARY KEY,
--    username VARCHAR(255),
--    email VARCHAR(255) UNIQUE,
--    password VARCHAR(255),
--    profile_picture_url LONGTEXT,
--    signup_date TIMESTAMP,
--    subscription_id BIGINT
--);
--
---- +. 외래 키 제약 조건 추가
--ALTER TABLE Users
--ADD CONSTRAINT fk_users_subscription
--FOREIGN KEY (subscription_id)
--REFERENCES Subscription(id)
--ON DELETE SET NULL;
--
--ALTER TABLE Subscription
--ADD CONSTRAINT fk_subscription_users
--FOREIGN KEY (user_id)
--REFERENCES Users(user_id)
--ON DELETE CASCADE;
--
---- 2. 에피소드 테이블 (Episode)
--CREATE TABLE Episode (
--    episode_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    title VARCHAR(255) NOT NULL,
--    episode_count INTEGER NOT NULL,
--    description TEXT,
--    released_date VARCHAR(255),
--    thumbnail_url LONGTEXT,
--    cast_list VARCHAR(255), -- cast가 예약어라 변경
--    like_count INTEGER,
--    watch_count INTEGER
--);
--
---- 3. 비디오 테이블 (Videos)
--CREATE TABLE Videos (
--    video_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    episode_id BIGINT,
--    video_url VARCHAR(255) NOT NULL,
--    duration INTEGER NOT NULL,
--    video_num INTEGER NOT NULL,
--    thumbnail_url VARCHAR(2083),  -- 추가
--    FOREIGN KEY (episode_id) REFERENCES Episode(episode_id) ON DELETE CASCADE
--);
--
---- 4. 시청기록 테이블 (Check_watched)
--CREATE TABLE Check_watched (
--    user_id VARCHAR(255),
--    video_id BIGINT,
--    watched_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    progress INTEGER NOT NULL,
--    PRIMARY KEY (user_id, video_id),
--    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
--    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
--);
--
---- 5. 좋아요 테이블 (Likes)
--CREATE TABLE Likes (
--    user_id VARCHAR(255),
--    video_id BIGINT,
--    created_at TIMESTAMP,
--    PRIMARY KEY (user_id, video_id),
--    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
--    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
--);


INSERT INTO Subscription (user_id, start_date, end_date, subscription_type) VALUES
('1', '2023-12-01', '2024-01-01', 'PREMIUM'),
('2', '2023-12-01', '2024-01-01', 'STANDARD'),
('3', '2023-12-01', '2024-01-01', 'BASIC');

INSERT INTO Users (user_id, username, email, password, profile_picture_url, subscription_id) VALUES
('1', 'user1', 'user1@example.com', 'password123', 'https://via.placeholder.com/100x100', 1),
('2', 'user2', 'user2@example.com', 'password123', 'https://via.placeholder.com/100x100', 2),
('3', 'user3', 'user3@example.com', 'password123', 'https://via.placeholder.com/100x100', 3),
('4', 'user4', 'user4@example.com', 'password123', 'https://via.placeholder.com/100x100', 1),
('5', 'user5', 'user5@example.com', 'password123', 'https://via.placeholder.com/100x100', 2),
('6', 'user6', 'user6@example.com', 'password123', 'https://via.placeholder.com/100x100', 3),
('7', 'user7', 'user7@example.com', 'password123', 'https://via.placeholder.com/100x100', 1),
('8', 'user8', 'user8@example.com', 'password123', 'https://via.placeholder.com/100x100', 2);

INSERT INTO Episode (title, episode_count, description, released_date, thumbnail_url, cast_list, like_count, watch_count) VALUES
('별에서 온 그대', 25, '400년 전 지구에 떨어진 외계남 도민준과 왕싸가지 한류여신 톱스타 천송이의 기적과도 같은 달콤 발랄 로맨스', '2013-12-18', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg', '전지현, 김수현, 박해진, 유인나, 신성록, 안재현', 0, 0),
('오 나의 귀신님', 3, '음탕한 처녀 귀신이 빙의 된 소심한 주방 보조 나봉선과 자뻑 스타 셰프 강선우가 펼치는 응큼발칙 빙의 로맨스', '2015-07-03', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_ohmyghost.jpg', '박보영, 조정석, 임주환, 김슬기', 0, 0),
('시그널', 2, '과거로부터 걸려온 간절한 신호로 연결된 현재와 과거의 형사들이 오래된 미제 사건들을 다시 파헤친다!', '2016-01-22', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_signal.jpg', '이제훈, 김혜수, 조진웅', 0, 0),
('빈센조', 2, '조직의 배신으로 한국으로 오게 된 이탈리아 마피아 변호사가 베테랑 독종 변호사와 함께 악당의 방식으로 악당을 쓸어버리는 이야기', '2021-02-20', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_vincenzo.jpg', '송중기, 전여빈, 옥택연, 유재명, 김여진, 곽동연, 조한철', 0, 0),
('선재 업고 튀어', 2, '죽음으로 절망했던 팬이 시간을 거슬러 최애를 구하기 위해 고군분투하는 타임슬립 드라마', '2023-06-23', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_.seonjaejpg.jpg', '변우석, 김혜윤, 송건희, 이승협', 0, 0),
('비밀의 숲', 2, '감정을 느끼지 못하는 외톨이 검사 황시목, 정의롭고 따뜻한 형사 한여진과 함께 검찰 스폰서 살인사건과 그 이면에 숨겨진 진실을 파헤치는 내부 비밀 추적극', '2017-06-10', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EB%B9%84%EB%B0%80%EC%9D%98%EC%88%B2.jpg', '조승우, 배두나, 이준혁, 유재명, 신혜선', 0, 0),
('사랑의 불시착', 4, '어느 날 돌풍과 함께 패러글라이딩 사고로 북한에 불시착한 재벌 2세 패션업계 사장 윤세리와 그녀를 숨기고 지키다 사랑하게 되는 특급 장교 리정혁의 절대 극비 로맨스', '2019-12-14', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EC%82%AC%EB%9E%91%EC%9D%98%EB%B6%88%EC%8B%9C%EC%B0%A9.jpg', '현빈, 손예진, 서지혜, 김정현', 0, 0),
('유미의 세포들2', 5, '세포들과 함께 먹고 사랑하고 성장하는 평범한 유미의 이야기를 그린 세포 자극 공감 로맨스', '2022-06-10', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yum_cell.jpg', '김고은, 박진영, 안보현, 이유비', 0, 0),
('커피프린스 1호점', 11, '소녀가장 고은찬과 재벌가 아들 최한결이 오해와 계약 알바로 얽히며 사랑과 성장 이야기를 그린 로맨틱 드라마', '2007-07-02', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg', '공유, 윤은혜, 이선균, 채정안', 0, 0),
('쓸쓸하고 찬란하신 - 도깨비', 6, '불멸의 도깨비와 기억상실 저승사자, 그리고 도깨비 신부라 주장하는 소녀가 엮이며 펼쳐지는 신비로운 운명 로맨스', '2016-12-02', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/goblin-thumbnails/goblin_th.jpg', '공유, 김고은, 이동욱, 유인나, 육성재', 0, 0),
('치얼업', 9, '찬란한 역사를 뒤로 하고 망해가는 대학 응원단에 모인 청춘들의 뜨겁고 서늘한 캠퍼스 미스터리 로코', '2022-10-03', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup-th.png', '한지현, 배인혁, 김현진, 장규리', 0, 0),
('상속자들', 4, '우리가 지금까지 단 한 번도 본적 없는 아주 섹시하고 사악한 격정 하이틴 로맨스', '2013-10-09', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/heirs.png', '이민호, 박신혜, 김우빈, 정수정, 김지원, 강민혁, 최진혁, 강하늘, 박형식', 0, 0),
('겨울연가', 18, '첫사랑의 죽음 이후 10년, 약혼 중인 유진 앞에 죽은 연인과 똑같은 외모의 남자가 나타나 혼란에 빠진다', '2002-01-14', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg', '배용준, 최지우, 박용하, 박솔미', 0, 0),
('그해 우리는', 2, '함께해서 더러웠고 다신 보지 말자!로 끝났어야 할 인연이 10년이 흘러 카메라 앞에 강제 소환 되어 펼쳐지는 청춘 다큐를 가장한 아찔한 로맨스 드라마', '2021-12-06', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/oursummer-th.jpg', '최우식, 김다미, 김성철, 노정의', 0, 0),
('미스터 션샤인', 3, '미국의 이권을 위해 조선에 주둔한 검은머리의 미 해병대장교 유진 초이와 조선의 정신적 지주인 고씨 가문의 마지막 핏줄인 애신 애기씨의, 쓸쓸하고 장엄한 모던 연애사', '2018-07-07', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/sunshine.jpg', '이병헌, 김태리, 유연석, 김민정, 변요한', 0, 0),
('무한도전', 21, '2005년부터 2018년까지 대한민국 MBC에서 방영되었던 MBC의 간판 예능 프로그램', '2005-10-29', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg', '유재석, 박명수, 정준하, 하하, 길, 노홍철, 정형돈', 0, 0),
('분노의 질주', 5, '스트리트 레이싱을 소재로 다룬 자동차 액션 영화 시리즈, 분노의 질주의 8번째 작품', '2017-04-12', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/furi.jpg', 'Vin Diesel, Dwayne Johnson, Jason Statham', 0, 0),
('알쓸신잡', 8, '분야를 넘나드는 잡학박사들의 지식 대방출 향연! 국내를 여행하면서 다양한 관점의 이야기를 펼쳐 딱히 쓸데는 없지만 알아두면 흥이 나는 신비한 수다 여행', '2017-06-02', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg', '유희열, 유시민, 황교익, 김영하, 정재승', 0, 0);

INSERT INTO Videos (episode_id, video_url, duration, video_num, thumbnail_url) VALUES
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar1.mp4', 56, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar2.mp4', 57, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar3.mp4', 55, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar4.mp4', 56, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar5.mp4', 40, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar6.mp4', 56, 6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar7.mp4', 51, 7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar8.mp4', 55, 8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar9.mp4', 59, 9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar10.mp4', 59, 10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar11.mp4', 51, 11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar12.mp4', 56, 12, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar13.mp4', 45, 13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar14.mp4', 50, 14, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar15.mp4', 56, 15, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar16.mp4', 50, 16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar17.mp4', 44, 17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar18.mp4', 58, 18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar19.mp4', 40, 19, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar20.mp4', 58, 20, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar21.mp4', 43, 21, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar22.mp4', 56, 22, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar23.mp4', 57, 23, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar24.mp4', 58, 24, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/lovestar-videos/lovestar25.mp4', 57, 25, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg'),
(2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/ohmyghost-videos/ohmyghost1.mp4', 177, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_ohmyghost.jpg'),
(2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/ohmyghost-videos/ohmyghost2.mp4', 190, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_ohmyghost.jpg'),
(2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/ohmyghost-videos/ohmyghost3.mp4', 401, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_ohmyghost.jpg'),
(3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/signal-videos/signal1.mp4', 37, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_signal.jpg'),
(3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/signal-videos/signal2.mp4', 866, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_signal.jpg'),
(4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/vincenzo-videos/vincenzo1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_vincenzo.jpg'),
(4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/vincenzo-videos/vincenzo2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_vincenzo.jpg'),
(5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/runner-videos/runner1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_.seonjaejpg.jpg'),
(5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/runner-videos/runner2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_.seonjaejpg.jpg'),
(6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/secret-videos/secret1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EB%B9%84%EB%B0%80%EC%9D%98%EC%88%B2.jpg'),
(6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/secret-videos/secret2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EB%B9%84%EB%B0%80%EC%9D%98%EC%88%B2.jpg'),
(7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/crashLanding-videos/crashLanding1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EC%82%AC%EB%9E%91%EC%9D%98%EB%B6%88%EC%8B%9C%EC%B0%A9.jpg'),
(7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/crashLanding-videos/crashLanding2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EC%82%AC%EB%9E%91%EC%9D%98%EB%B6%88%EC%8B%9C%EC%B0%A9.jpg'),
(7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/crashLanding-videos/crashLanding3.mp4', 1000, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EC%82%AC%EB%9E%91%EC%9D%98%EB%B6%88%EC%8B%9C%EC%B0%A9.jpg'),
(7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/crashLanding-videos/crashLanding4.mp4', 1000, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EC%82%AC%EB%9E%91%EC%9D%98%EB%B6%88%EC%8B%9C%EC%B0%A9.jpg'),
(8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yumi-videos/yumi1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yum_cell.jpg'),
(8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yumi-videos/yumi2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yum_cell.jpg'),
(8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yumi-videos/yumi3.mp4', 1000, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yum_cell.jpg'),
(8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yumi-videos/yumi4.mp4', 1000, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yum_cell.jpg'),
(8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yumi-videos/yumi5.mp4', 1000, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yum_cell.jpg'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+1%ED%99%94.mp4', 1510, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+1%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+2%ED%99%94.mp4', 1101, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+2%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+3%ED%99%94.mp4', 1170, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+3%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+4%ED%99%94.mp4', 1120, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+4%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+5%ED%99%94.mp4', 1213, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+5%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+6%ED%99%94.mp4', 1235, 6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+6%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+7%ED%99%94.mp4', 1398, 7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+7%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+8%ED%99%94.mp4', 1369, 8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+8%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+9%ED%99%94.mp4', 1421, 9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+9%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+10%ED%99%94.mp4', 1320, 10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+10%ED%99%94.png'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+11%ED%99%94.mp4', 1139, 11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/%EC%BB%A4%ED%94%BC%ED%94%84%EB%A6%B0%EC%8A%A4+11%ED%99%94.png'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/goblin-videos/goblin1.mp4', 727, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/goblin-thumbnails/goblin1.png'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/goblin-videos/goblin2.mp4', 672, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/goblin-thumbnails/goblin2.png'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/goblin-videos/goblin3.mp4', 673, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/goblin-thumbnails/goblin3.png'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/goblin-videos/goblin4.mp4', 681, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/goblin-thumbnails/goblin4.png'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/goblin-videos/goblin5.mp4', 773, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/goblin-thumbnails/goblin5.png'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/goblin-videos/goblin6.mp4', 702, 6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/goblin-thumbnails/goblin6.png'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup1.mp4', 1195, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup1.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup2.mp4', 1267, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup2.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup3.mp4', 1134, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup3.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup4.mp4', 1197, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup4.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup5.mp4', 938, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup5.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup6.mp4', 890, 6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup6.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup7.mp4', 1178, 7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup7.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup8.mp4', 1289, 8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup8.jpg'),
(11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/cheerup-videos/cheerup9.mp4', 1176, 9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/cheerup-thumbnails/cheerup9.jpg'),
(12, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/heirs-videos/heirs1.mp4', 766, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/heirs.png'),
(12, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/heirs-videos/heirs2.mp4', 831, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/heirs.png'),
(12, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/heirs-videos/heirs3.mp4', 853, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/heirs.png'),
(12, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/heirs-videos/heirs4.mp4', 749, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/heirs.png'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter1.mp4', 937, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter2.mp4', 917, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter3.mp4', 995, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter4.mp4', 1003, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter5.mp4', 902, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter6.mp4', 988, 6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter7.mp4', 969, 7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter8.mp4', 804, 8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter9.mp4', 1020, 9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter10.mp4', 907, 10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter11.mp4', 1459, 11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter12.mp4', 1218, 12, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter13.mp4', 1267, 13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter14.mp4', 1703, 14, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter15.mp4', 941, 15, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter16.mp4', 921, 16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter17.mp4', 1039, 17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/winter-videos/winter18.mp4', 881, 18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/winter.jpg'),
(14, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/oursummer-videos/oursummer1.mp4', 1231, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/oursummer-th.jpg'),
(14, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/oursummer-videos/oursummer2.mp4', 1201, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/oursummer-th.jpg'),
(15, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/sunshine-videos/sunshine1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/sunshine.jpg'),
(15, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/sunshine-videos/sunshine2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/sunshine.jpg'),
(15, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/sunshine-videos/sunshine3.mp4', 1000, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/sunshine.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite3.mp4', 1000, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite4.mp4', 1000, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite5.mp4', 1000, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite6.mp4', 1000, 6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite7.mp4', 1000, 7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite8.mp4', 1000, 8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite9.mp4', 1000, 9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite10.mp4', 1000, 10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite11.mp4', 1000, 11, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite12.mp4', 1000, 12, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite13.mp4', 1000, 13, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite14.mp4', 1000, 14, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite15.mp4', 1000, 15, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite16.mp4', 1000, 16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite17.mp4', 1000, 17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite18.mp4', 1000, 18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite19.mp4', 1000, 19, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite20.mp4', 1000, 20, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(16, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/infinite-videos/infinite21.mp4', 1000, 21, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/infinite.jpg'),
(17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/furi-videos/furi1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/furi.jpg'),
(17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/furi-videos/furi2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/furi.jpg'),
(17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/furi-videos/furi3.mp4', 1000, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/furi.jpg'),
(17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/furi-videos/furi4.mp4', 1000, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/furi.jpg'),
(17, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/furi-videos/furi5.mp4', 1000, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/furi.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful1.mp4', 1000, 1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful2.mp4', 1000, 2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful3.mp4', 1000, 3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful4.mp4', 1000, 4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful5.mp4', 1000, 5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful6.mp4', 1000, 6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful7.mp4', 1000, 7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg'),
(18, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/useful-videos/useful8.mp4', 1000, 8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/useful.jpg');

INSERT INTO Check_watched (user_id, video_id, progress) VALUES
('1', 1, 50),
('2', 2, 100),
('3', 3, 75),
('4', 4, 60),
('5', 5, 90),
('6', 6, 100),
('7', 7, 30),
('8', 8, 80),
('1', 11, 70),
('2', 12, 100),
('3', 13, 45),
('4', 14, 85),
('5', 15, 100),
('1', 23, 1),
('2', 23, 1),
('3', 23, 1),
('4', 23, 1),
('5', 23, 1),
('6', 23, 1);

INSERT INTO Likes (user_id, video_id) VALUES
('1', 1),
('2', 2),
('3', 3),
('4', 4),
('5', 5),
('6', 6),
('7', 7),
('8', 8),
('1', 11),
('2', 12),
('3', 13),
('4', 14),
('5', 15);


