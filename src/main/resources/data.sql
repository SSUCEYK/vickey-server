DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Episode;
DROP TABLE IF EXISTS Videos;
DROP TABLE IF EXISTS Check_watched;
DROP TABLE IF EXISTS Likes;

-- +. 구독 테이블 (Subscription)
CREATE TABLE Subscription (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(255) UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    subscription_type VARCHAR(50) NOT NULL
);

-- 1. 사용자 테이블 (Users)
CREATE TABLE Users (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_picture_url LONGTEXT,
    signup_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subscription_id BIGINT
);

-- +. 외래 키 제약 조건 추가
ALTER TABLE Users
ADD CONSTRAINT fk_users_subscription
FOREIGN KEY (subscription_id)
REFERENCES Subscription(id)
ON DELETE SET NULL;

ALTER TABLE Subscription
ADD CONSTRAINT fk_subscription_users
FOREIGN KEY (user_id)
REFERENCES Users(user_id)
ON DELETE CASCADE;

-- 2. 에피소드 테이블 (Episode)
CREATE TABLE Episode (
    episode_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    episode_count INTEGER NOT NULL,
    description TEXT,
    released_date VARCHAR(255),
    thumbnail_url LONGTEXT,
    cast_list VARCHAR(255), -- cast가 예약어라 변경
    like_count INTEGER,
    watch_count INTEGER
);

-- 3. 비디오 테이블 (Videos)
CREATE TABLE Videos (
    video_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    episode_id BIGINT,
    video_url VARCHAR(255) NOT NULL,
    duration INTEGER NOT NULL,
    video_num INTEGER NOT NULL,
    thumbnail_url VARCHAR(2083),  -- 추가
    FOREIGN KEY (episode_id) REFERENCES Episode(episode_id) ON DELETE CASCADE
);

-- 4. 시청기록 테이블 (Check_watched)
CREATE TABLE Check_watched (
    user_id VARCHAR(255),
    video_id BIGINT,
    watched_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    progress INTEGER NOT NULL,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
);

-- 5. 좋아요 테이블 (Likes)
CREATE TABLE Likes (
    user_id VARCHAR(255),
    video_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
);


INSERT INTO Users (user_id, username, email, password, profile_picture_url) VALUES
('1', 'user1', 'user1@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('2', 'user2', 'user2@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('3', 'user3', 'user3@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('4', 'user4', 'user4@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('5', 'user5', 'user5@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('6', 'user6', 'user6@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('7', 'user7', 'user7@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('8', 'user8', 'user8@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('9', 'user9', 'user9@example.com', 'password123', 'https://via.placeholder.com/100x100'),
('10', 'user10', 'user10@example.com', 'password123', 'https://via.placeholder.com/100x100');

INSERT INTO Episode (title, episode_count, description, released_date, thumbnail_url, cast_list, like_count, watch_count) VALUES
('드라마 1', 10, 'Description for Episode 1', '2023-01-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_goblin.jpg', 'Actor A, Actor B', 0, 0),
('드라마 2', 12, 'Description for Episode 2', '2023-02-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_lovefromstar.jpg', 'Actor C, Actor D', 0, 0),
('드라마 3', 8, 'Description for Episode 3', '2023-03-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_ohmyghost.jpg', 'Actor E, Actor F', 0, 0),
('드라마 4', 15, 'Description for Episode 4', '2023-04-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_ourbelovedsummer.jpg', 'Actor G, Actor H', 0, 0),
('드라마 5', 10, 'Description for Episode 5', '2023-05-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_signal.jpg', 'Actor I, Actor J', 0, 0),
('드라마 6', 12, 'Description for Episode 6', '2023-06-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_vincenzo.jpg', 'Actor K, Actor L', 0, 0),
('드라마 7', 9, 'Description for Episode 7', '2023-07-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_.seonjaejpg.jpg', 'Actor M, Actor N', 0, 0),
('드라마 8', 11, 'Description for Episode 8', '2023-08-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EB%B9%84%EB%B0%80%EC%9D%98%EC%88%B2.jpg', 'Actor O, Actor P', 0, 0),
('드라마 9', 7, 'Description for Episode 9', '2023-09-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail_%EC%82%AC%EB%9E%91%EC%9D%98%EB%B6%88%EC%8B%9C%EC%B0%A9.jpg', 'Actor Q, Actor R', 0, 0),
('드라마 10', 13, 'Description for Episode 10', '2023-10-01', 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/yum_cell.jpg', 'Actor S, Actor T', 0, 0);

--INSERT INTO episode (episode_id, episode_count, title, cast_list, released_date, description, thumbnail_url, video_urls)
--VALUES (7333, 5, 'The Last Stand', 'William Taylor, Emily White', '2023', 'Everything comes to a dramatic conclusion.', 'https://picsum.photos/900/1850',
--        '["https://d3frntip486vjo.cloudfront.net/Vickey_test_1.MP4", "https://d3frntip486vjo.cloudfront.net/Vickey_test_2.MP4", "https://d3frntip486vjo.cloudfront.net/Vickey_test_3.MP4", "https://d3frntip486vjo.cloudfront.net/Vickey_test_4.MP4", "https://d3frntip486vjo.cloudfront.net/Vickey_test_5.MP4"]');

INSERT INTO Videos (episode_id, video_url, duration, video_num, thumbnail_url) VALUES
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1200, 1, 'https://via.placeholder.com/140x200'),
(1, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1400, 2, 'https://via.placeholder.com/140x200'),
(2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1600, 1, 'https://via.placeholder.com/140x200'),
(2, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1800, 2, 'https://via.placeholder.com/140x200'),
(3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1200, 1, 'https://via.placeholder.com/140x200'),
(3, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1400, 2, 'https://via.placeholder.com/140x200'),
(4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1600, 1, 'https://via.placeholder.com/140x200'),
(4, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1800, 2, 'https://via.placeholder.com/140x200'),
(5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1200, 1, 'https://via.placeholder.com/140x200'),
(5, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1400, 2, 'https://via.placeholder.com/140x200'),
(6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1600, 1, 'https://via.placeholder.com/140x200'),
(6, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1800, 2, 'https://via.placeholder.com/140x200'),
(7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1200, 1, 'https://via.placeholder.com/140x200'),
(7, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1400, 2, 'https://via.placeholder.com/140x200'),
(8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1600, 1, 'https://via.placeholder.com/140x200'),
(8, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1800, 2, 'https://via.placeholder.com/140x200'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1200, 1, 'https://via.placeholder.com/140x200'),
(9, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1400, 2, 'https://via.placeholder.com/140x200'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1600, 1, 'https://via.placeholder.com/140x200'),
(10, 'https://ceyk-bucket.s3.ap-northeast-2.amazonaws.com/1733016602387-video3.mp4', 1800, 2, 'https://via.placeholder.com/140x200');

INSERT INTO Check_watched (user_id, video_id, progress) VALUES
('1', 1, 50),
('2', 2, 100),
('3', 3, 75),
('4', 4, 60),
('5', 5, 90),
('6', 6, 100),
('7', 7, 30),
('8', 8, 80),
('9', 9, 95),
('10', 10, 100),
('1', 11, 70),
('2', 12, 100),
('3', 13, 45),
('4', 14, 85),
('5', 15, 100);

INSERT INTO Likes (user_id, video_id) VALUES
('1', 1),
('2', 2),
('3', 3),
('4', 4),
('5', 5),
('6', 6),
('7', 7),
('8', 8),
('9', 9),
('10', 10),
('1', 11),
('2', 12),
('3', 13),
('4', 14),
('5', 15);

INSERT INTO Subscription (user_id, start_date, end_date, subscription_type) VALUES
('1', '2023-12-01', '2024-01-01', 'PREMIUM'),
('2', '2023-12-01', '2024-01-01', 'STANDARD'),
('3', '2023-12-01', '2024-01-01', 'BASIC');


