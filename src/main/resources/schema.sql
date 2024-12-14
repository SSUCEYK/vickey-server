-- 기존 테이블 삭제
DROP TABLE IF EXISTS Likes CASCADE;
DROP TABLE IF EXISTS Check_watched CASCADE;
DROP TABLE IF EXISTS Videos CASCADE;
DROP TABLE IF EXISTS Episode CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Subscription CASCADE;

-- 1. Subscription 테이블 생성
CREATE TABLE Subscription (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(255) UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    subscription_type VARCHAR(50) NOT NULL
);

-- 2. Users 테이블 생성
CREATE TABLE Users (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    profile_picture_url LONGTEXT,
    signup_date TIMESTAMP,
    subscription_id BIGINT,
    FOREIGN KEY (subscription_id) REFERENCES Subscription(id) ON DELETE SET NULL
);

-- 3. Episode 테이블 생성
CREATE TABLE Episode (
    episode_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    episode_count INTEGER NOT NULL,
    description TEXT,
    released_date VARCHAR(255),
    thumbnail_url LONGTEXT,
    cast_list VARCHAR(255),
    like_count INTEGER,
    watch_count INTEGER
);

-- 4. Videos 테이블 생성
CREATE TABLE Videos (
    video_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    episode_id BIGINT,
    video_url VARCHAR(255) NOT NULL,
    duration INTEGER NOT NULL,
    video_num INTEGER NOT NULL,
    thumbnail_url VARCHAR(2083),
    FOREIGN KEY (episode_id) REFERENCES Episode(episode_id) ON DELETE CASCADE
);

-- 5. Check_watched 테이블 생성
CREATE TABLE Check_watched (
    user_id VARCHAR(255),
    video_id BIGINT,
    watched_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    progress INTEGER NOT NULL,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
);

-- 6. Likes 테이블 생성
CREATE TABLE Likes (
    user_id VARCHAR(255),
    video_id BIGINT,
    created_at TIMESTAMP,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
);
