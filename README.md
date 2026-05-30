# soongsilHealthcareService

Android Studio와 Kotlin, Jetpack Compose 기반 헬스케어 애플리케이션 프로젝트이다.

## 중간보고서 기준 MVP

현재 프로젝트는 Android Studio와 Kotlin 기반으로 기본 폴더 구조와 화면 이동 구조를 구성하였다. 또한 Room DB를 이용하여 개인 운동 기록과 식단 기록을 로컬에 저장하는 기능을 우선 구현하였다. AI 코치, 커뮤니티, 마이페이지 기능은 현재 placeholder 화면으로 연결되어 있으며, 이후 Gemini API, Firebase Authentication, Cloud Firestore와 연동하여 확장할 예정이다.

## 구현 완료 기능

* 임시 로그인 화면 및 홈 화면 이동
* 홈 화면의 오늘 운동/식단 총 칼로리 요약
* Room DB 기반 운동 기록 추가, 조회, 삭제
* Room DB 기반 식단 기록 추가, 조회, 삭제, 즐겨찾기 토글
* Navigation Compose 기반 화면 이동
* AI 코치, 커뮤니티, 마이페이지 placeholder 화면

## 임시 로그인 사용 이유

중간 시연용 로컬 MVP에서는 Firebase Authentication 연동을 제외하고 앱의 핵심 흐름과 로컬 저장 기능을 먼저 검증한다. 로그인 화면의 "임시 로그인" 버튼은 실제 인증 없이 홈 화면으로 이동한다.

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

## 추후 구현 예정

* Firebase Authentication 기반 실제 로그인
* Cloud Firestore 기반 커뮤니티
* Gemini API 기반 AI 코치
* Firebase 사용자 정보 기반 마이페이지
