# soongsilHealthcareService

Android Studio와 Kotlin, Jetpack Compose 기반 헬스케어 애플리케이션 프로젝트이다.

## 현재 구현 상태

현재 프로젝트는 Android Studio와 Kotlin 기반으로 화면 이동 구조, 로컬 기록 저장, Firebase 로그인, Firestore 프로필/커뮤니티 저장, Gemini AI 코치 기능을 사용할 수 있도록 구성하였다.

## 구현 완료 기능

* 임시 로그인 화면 및 홈 화면 이동
* 홈 화면의 오늘 운동/식단 총 칼로리 요약
* Room DB 기반 운동 기록 추가, 조회, 삭제
* Room DB 기반 식단 기록 추가, 조회, 삭제, 즐겨찾기 토글
* Firebase Authentication 기반 회원가입, 로그인, 로그아웃
* Cloud Firestore 기반 프로필 저장 및 커뮤니티 게시글/좋아요
* Gemini API 기반 운동/식단 피드백
* DataStore 기반 설정 저장
* Navigation Compose 기반 화면 이동

## Firebase / Gemini 설정

`local.properties`에 다음 값을 넣으면 실제 외부 연동 기능을 사용할 수 있다.

```properties
FIREBASE_API_KEY=YOUR_FIREBASE_WEB_API_KEY
FIREBASE_PROJECT_ID=YOUR_FIREBASE_PROJECT_ID
GEMINI_API_KEY=YOUR_GEMINI_API_KEY
```

Firebase Console에서는 Authentication의 이메일/비밀번호 로그인을 켜고, Cloud Firestore 데이터베이스를 생성해야 한다. Firebase Storage는 사용하지 않는다.

## 임시 로그인

로그인 화면의 "임시 로그인" 버튼은 Firebase 없이 운동/식단 로컬 저장 흐름을 빠르게 확인하기 위한 기능이다. Firebase 기반 커뮤니티, 프로필, AI 기능은 실제 회원가입/로그인 후 사용하는 것을 기준으로 한다.

## 실행 방법

Android Studio에서 프로젝트를 열고 `app` 구성을 실행한다. 앱 실행 후 `임시 로그인` 버튼을 누르면 홈 화면으로 이동한다.

## 빌드 명령어

Windows:

```bash
gradlew.bat :app:assembleDebug
```

macOS/Linux:

```bash
./gradlew :app:assembleDebug
```

## 확장 방향

* 커뮤니티 댓글/신고 기능
* 주간 운동/식단 통계
* 알림 설정 실제 스케줄링
* Gemini 프롬프트 고도화
