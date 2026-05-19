# 역할 분담 문서

## 역할 분담 목적

본 프로젝트는 2인 협업 프로젝트로 진행하며, 기능별 역할 분리를 통해 개발 효율을 높이고 코드 충돌을 최소화하는 것을 목표로 한다.

---

# 팀 구성

| 이름  | 담당 영역                     |
| --- | ------------------------- |
| 양정원 | UI / 화면 구성 / Navigation   |
| 김준수 | 데이터 처리 / Firebase / AI 기능 |

---

# 양정원 역할

## 담당 영역

* Jetpack Compose UI 개발
* 화면 디자인 구성
* Navigation 구현
* 사용자 인터페이스 개발

---

## 담당 기능

### 로그인 화면

* LoginScreen.kt
* SignUpScreen.kt

---

### 메인 화면

* HomeScreen.kt

---

### 운동 기록 화면

* ExerciseScreen.kt
* ExerciseAddScreen.kt

---

### 식단 기록 화면

* DietScreen.kt
* DietAddScreen.kt

---

### AI 코치 화면

* AiCoachScreen.kt

---

### 커뮤니티 화면

* CommunityScreen.kt
* PostWriteScreen.kt

---

### 마이페이지 화면

* MyPageScreen.kt
* SettingScreen.kt

---

## 담당 기술

| 기술                 | 역할       |
| ------------------ | -------- |
| Jetpack Compose    | UI 구현    |
| Navigation Compose | 화면 이동    |
| Material3          | UI 디자인   |
| ViewModel          | 화면 상태 관리 |

---

# 김준수 역할

## 담당 영역

* Room DB 설계 및 구현
* Firebase Authentication 연동
* Cloud Firestore 연동
* Firebase Storage 연동
* Gemini API 연동
* Repository 계층 구현

---

## 담당 기능

### Room DB

* ExerciseEntity
* DietEntity
* ExerciseDao
* DietDao
* AppDatabase

---

### Firebase Authentication

* 회원가입 기능
* 로그인 기능
* 로그아웃 기능

---

### Cloud Firestore

* 사용자 프로필 저장
* 커뮤니티 게시글 저장

---

### Firebase Storage

* 게시글 이미지 업로드

---

### Gemini API

* AI 피드백 요청
* 프롬프트 생성
* AI 응답 처리

---

### Repository 계층

* AuthRepository
* ExerciseRepository
* DietRepository
* CommunityRepository
* AiCoachRepository

---

## 담당 기술

| 기술                      | 역할          |
| ----------------------- | ----------- |
| Room DB                 | 로컬 데이터 저장   |
| Firebase Authentication | 로그인 인증      |
| Cloud Firestore         | 클라우드 데이터 저장 |
| Firebase Storage        | 이미지 저장      |
| Gemini API              | AI 피드백 생성   |
| Retrofit2               | API 통신      |
| Coroutine               | 비동기 처리      |

---

# 공통 작업 영역

아래 영역은 두 팀원이 함께 작업한다.

---

## 공통 작업

* GitHub 관리
* 프로젝트 구조 설계
* 코드 리뷰
* 테스트 및 디버깅
* UI/기능 개선
* README 문서 관리

---

# 협업 규칙

1. 작업 전 develop 브랜치 최신화 수행
2. feature 브랜치에서 작업 진행
3. 기능 완료 후 Pull Request 생성
4. 공통 파일 수정 시 팀원과 공유
5. build.gradle.kts 수정 시 공유 필수
6. MainActivity.kt 수정 시 공유 필수

---

# 협업 목표

1. 기능 분리를 통한 개발 효율 향상
2. Git 충돌 최소화
3. 유지보수 편의성 향상
4. 안정적인 프로젝트 구조 유지
5. 역할 분리를 통한 빠른 개발 진행

---
