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
    user_id VARCHAR(255) PRIMARY KEY, -- String 타입으로 변경
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
    episode_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    episode_count INTEGER NOT NULL,
    description TEXT,
    released_date DATE,
    thumbnail_url LONGTEXT,
    cast_list VARCHAR(255) -- cast가 예약어라 변경
);

-- 3. 비디오 테이블 (Videos)
CREATE TABLE Videos (
    video_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    episode_id INTEGER,
    video_url VARCHAR(255) NOT NULL,
    duration INTEGER NOT NULL,
    video_num INTEGER NOT NULL,
    thumbnail_url LONGTEXT,  -- 추가
    FOREIGN KEY (episode_id) REFERENCES Episode(episode_id) ON DELETE CASCADE
);

-- 4. 시청기록 테이블 (Check_watched)
CREATE TABLE Check_watched (
    user_id VARCHAR(255),
    video_id INTEGER,
    watched_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    progress INTEGER NOT NULL,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
);

-- 5. 좋아요 테이블 (Likes)
CREATE TABLE Likes (
    user_id VARCHAR(255),
    video_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
);


INSERT INTO Users (user_id, username, email, password, profile_picture_url) VALUES
('1', 'user1', 'user1@example.com', 'password123', 'https://example.com/profile1.jpg'),
('2', 'user2', 'user2@example.com', 'password123', 'https://example.com/profile2.jpg'),
('3', 'user3', 'user3@example.com', 'password123', 'https://example.com/profile3.jpg'),
('4', 'user4', 'user4@example.com', 'password123', 'https://example.com/profile4.jpg'),
('5', 'user5', 'user5@example.com', 'password123', 'https://example.com/profile5.jpg'),
('6', 'user6', 'user6@example.com', 'password123', 'https://example.com/profile6.jpg'),
('7', 'user7', 'user7@example.com', 'password123', 'https://example.com/profile7.jpg'),
('8', 'user8', 'user8@example.com', 'password123', 'https://example.com/profile8.jpg'),
('9', 'user9', 'user9@example.com', 'password123', 'https://example.com/profile9.jpg'),
('10', 'user10', 'user10@example.com', 'password123', 'https://example.com/profile10.jpg');

INSERT INTO Episode (title, episode_count, description, released_date, thumbnail_url, cast_list) VALUES
('드라마 1', 10, 'Description for Episode 1', '2023-01-01', 'https://via.placeholder.com/280x400', 'Actor A, Actor B'),
('드라마 2', 12, 'Description for Episode 2', '2023-02-01', 'https://via.placeholder.com/280x400', 'Actor C, Actor D'),
('드라마 3', 8, 'Description for Episode 3', '2023-03-01', 'https://via.placeholder.com/280x400', 'Actor E, Actor F'),
('드라마 4', 15, 'Description for Episode 4', '2023-04-01', 'https://via.placeholder.com/280x400', 'Actor G, Actor H'),
('드라마 5', 10, 'Description for Episode 5', '2023-05-01', 'https://via.placeholder.com/280x400', 'Actor I, Actor J'),
('드라마 6', 12, 'Description for Episode 6', '2023-06-01', 'https://via.placeholder.com/280x400', 'Actor K, Actor L'),
('드라마 7', 9, 'Description for Episode 7', '2023-07-01', 'https://via.placeholder.com/280x400', 'Actor M, Actor N'),
('드라마 8', 11, 'Description for Episode 8', '2023-08-01', 'https://via.placeholder.com/280x400', 'Actor O, Actor P'),
('드라마 9', 7, 'Description for Episode 9', '2023-09-01', 'https://via.placeholder.com/280x400', 'Actor Q, Actor R'),
('드라마 10', 13, 'Description for Episode 10', '2023-10-01', 'https://via.placeholder.com/280x400', 'Actor S, Actor T');

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


