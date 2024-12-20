# Vickey Project

*한국어: [한국어 버전으로 이동](#한국어-버전) / English: [Go to English version](#english-version)*

</br>

### 한국어 버전


>**Vickey**는 중국에서 급성장하고 있는 <u>숏폼 드라마</u> 시장을 겨냥해 개발된 혁신적인 플랫폼입니다. </br> 바쁜 사회인, 콘텐츠 러버, 젊은 시청자를 타겟으로 맞춤화된 세로형 비디오 시청 경험과 구독 기반 결제 모델을 제공합니다.

</br>  

#### **저장소 개요**

- **[vickey_app](https://github.com/SSUCEYK/vickey_app)**: 일반 사용자용 안드로이드 애플리케이션 프론트엔드.
- **[vickey-admin-project](https://github.com/SSUCEYK/vickey-admin-project)**: 콘텐츠 관리용 관리자 웹 프론트엔드.
- **[vickey-server](https://github.com/SSUCEYK/vickey-server)**: 사용자 및 관리자 인터페이스를 지원하는 백엔드 API 서버.
</br> 

#### **주요 기능**

- **사용자 인증**: 네이버 소셜 로그인과 Firebase 이메일 로그인 지원.
- **구독 시스템**: 카드 결제 및 카카오페이와 같은 외부 간편 결제 지원.
- **홈 화면**: 개인화된 추천 콘텐츠 및 인기 콘텐츠 제공.
- **콘텐츠 세부 정보**: 동적 에피소드 탐색과 자연스러운 사용자 인터페이스 제공.
- **비디오 재생**: AWS S3와 CloudFront를 통한 원활한 비디오 스트리밍.
- **검색**: 제목, 장르, 연출자 등의 콘텐츠 세부 정보 기반의 실시간 검색 지원.
- **마이 리스트**: 좋아요 표시한 콘텐츠 및 시청 기록 관리.
</br> 

#### **설치 및 실행 방법**

* ##### **vickey_app**
1. 저장소 클론:
   
   ```bash
    git clone https://github.com/SSUCEYK/vickey_app.git
   ```

2. Android Studio에서 프로젝트 열기.
3. Firebase 인증 구성, 네이버/AWS 키 구성, api-base-url 설정.
4. 안드로이드 디바이스 또는 에뮬레이터에서 빌드 및 실행.

* ##### **vickey-admin-project**
1. 저장소 클론:
   
   ```bash
    git clone https://github.com/SSUCEYK/vickey-admin-project.git
   ```

2. 의존성 설치 및 개발 서버 실행:
   
   ```bash
    npm install
    npm run serve
   ```

* ##### **vickey-server**
1. 저장소 클론:
   
   ```bash
    git clone https://github.com/SSUCEYK/vickey-server.git
   ```

2. 프로젝트 빌드:
   
   ```bash
    mvn clean install
   ```

3. 애플리케이션 실행:
   
   ```bash
    java -jar target/vickey-server-0.0.1-SNAPSHOT.jar
   ```

</br> 

#### **시스템 아키텍처**

1. **클라이언트**:
   - 일반 사용자는 안드로이드 앱을 통해 콘텐츠를 소비.
   - 관리자는 웹 인터페이스를 통해 콘텐츠를 관리.

2. **서버**:
   - 콘텐츠 제공, 인증 처리, 데이터 관리.
   - AWS CloudFront를 통해 비디오 스트리밍 URL 제공.

3. **스토리지**:
   - AWS S3에 비디오 파일 및 썸네일 저장.
   - 사용자 및 콘텐츠 메타데이터를 RDS Mysql DB에 저장.

4. **결제**:
   - 외부 결제 게이트웨이를 통한 구독 처리.
   - 서버 스케줄링을 통한 정기 결제 자동화.
</br> 

#### **개발 정보**

1.  **개발 기간**: 2024.09.-12.
2. **기술 스택**: 
   - **Android Studio**: 안드로이드 앱 개발
   - **Vue.js**: 관리자 웹 개발
   - **Firebase**: 인증 및 실시간 데이터베이스
   - **Spring Boot**: 백엔드 API 서버
   - **AWS S3**: 비디오 파일 저장
   - **AWS CloudFront**: 콘텐츠 전송 네트워크
   - **AWS RDS**: MySQL 기반 데이터베이스

</br> 

---

### English Version



>**Vickey** is an innovative platform designed for the rapidly growing <u>short-form drama</u> market in China. It targets busy professionals, content enthusiasts, and younger audiences, providing a tailored vertical video viewing experience and subscription-based payment model.

</br> 

#### **Repository Overview**

- **[vickey_app](https://github.com/SSUCEYK/vickey_app)**: Frontend Android application for general users.
- **[vickey-admin-project](https://github.com/SSUCEYK/vickey-admin-project)**: Web frontend for content management by administrators.
- **[vickey-server](https://github.com/SSUCEYK/vickey-server)**: Backend API server supporting user and admin interfaces.
</br> 

#### **Key Features**

- **User Authentication**: Supports Naver social login and Firebase email login.
- **Subscription System**: Supports card payments and external payment options like KakaoPay.
- **Homepage**: Provides personalized recommendations and trending content.
- **Content Details**: Offers dynamic episode navigation with a user-friendly interface.
- **Video Playback**: Smooth video streaming via AWS S3 and CloudFront.
- **Search**: Real-time search based on title, genre, or director details.
- **My List**: Manage liked content and viewing history.
</br> 

#### **Installation and Setup**

* ##### **vickey_app**
1. Clone the repository:
   
   ```bash
    git clone https://github.com/SSUCEYK/vickey_app.git
   ```

2. Open the project in Android Studio.
3. Configure Firebase authentication, Naver/AWS keys, and api-base-url.
4. Build and run on an Android device or emulator.

* ##### **vickey-admin-project**
1. Clone the repository:
   
   ```bash
    git clone https://github.com/SSUCEYK/vickey-admin-project.git
   ```

2. Install dependencies and start the development server:
   
   ```bash
    npm install
    npm run serve
   ```

* ##### **vickey-server**
1. Clone the repository:
   
   ```bash
    git clone https://github.com/SSUCEYK/vickey-server.git
   ```

2. Build the project:
   
   ```bash
    mvn clean install
   ```

3. Run the application:
   
   ```bash
    java -jar target/vickey-server-0.0.1-SNAPSHOT.jar
   ```
</br> 

#### **System Architecture**

1. **Client**:
   - General users consume content through the Android app.
   - Administrators manage content via the web interface.

2. **Server**:
   - Provides content, handles authentication, and manages data.
   - Delivers video streaming URLs through AWS CloudFront.

3. **Storage**:
   - Stores video files and thumbnails in AWS S3.
   - Manages user and content metadata in RDS Mysql DB.

4. **Payment**:
   - Processes subscriptions through external payment gateways.
   - Automates recurring payments via server scheduling.
</br> 

#### **Development Information**

1.  **Period**: 2024.09.-12.
2. **Tech Stack**: 
   - **Android Studio**: Android app development
   - **Vue.js**: Admin web development
   - **Firebase**: Authentication and real-time database
   - **Spring Boot**: Backend API server
   - **AWS S3**: Video file storage
   - **AWS CloudFront**: Content delivery network
   - **AWS RDS**: MySQL-based database

