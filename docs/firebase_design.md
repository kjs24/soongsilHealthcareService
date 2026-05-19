# Firebase 및 Firestore 설계 문서

## Firebase 사용 목적

본 프로젝트는 Firebase를 사용하여 사용자 인증, 사용자 정보 저장, 커뮤니티 데이터 저장 및 이미지 업로드 기능을 구현한다.

---

# 사용 기술

* Firebase Authentication
* Cloud Firestore
* Firebase Storage

---

# 1. Firebase Authentication

## 설명

Firebase Authentication은 사용자의 회원가입 및 로그인 기능을 처리하기 위해 사용한다.

## 인증 방식

* 이메일 / 비밀번호 로그인

## 저장 정보

| 필드    | 설명                 |
| ----- | ------------------ |
| uid   | Firebase 사용자 고유 ID |
| email | 사용자 이메일            |

## 주요 기능

* 회원가입
* 로그인
* 로그아웃
* 로그인 상태 유지

---

# 2. Cloud Firestore

## 설명

Cloud Firestore는 사용자 프로필 정보 및 커뮤니티 게시글 데이터를 저장하기 위해 사용한다.

---

# users 컬렉션

## 설명

사용자의 프로필 정보를 저장한다.

## 구조

```text
users
 └── userId
      ├── nickname
      ├── height
      ├── weight
      ├── goalWeight
      └── createdAt
```

## 필드 설명

| 필드명        | 타입        | 설명      |
| ---------- | --------- | ------- |
| nickname   | String    | 사용자 닉네임 |
| height     | Double    | 사용자 키   |
| weight     | Double    | 사용자 몸무게 |
| goalWeight | Double    | 목표 체중   |
| createdAt  | Timestamp | 가입 시간   |

---

# communityPosts 컬렉션

## 설명

사용자의 운동 공유 게시글을 저장한다.

## 구조

```text
communityPosts
 └── postId
      ├── userId
      ├── nickname
      ├── content
      ├── exerciseSummary
      ├── calorie
      ├── imageUrl
      ├── likeCount
      └── createdAt
```

## 필드 설명

| 필드명             | 타입        | 설명        |
| --------------- | --------- | --------- |
| userId          | String    | 작성자 uid   |
| nickname        | String    | 작성자 닉네임   |
| content         | String    | 게시글 내용    |
| exerciseSummary | String    | 운동 요약     |
| calorie         | Int       | 총 소모 칼로리  |
| imageUrl        | String    | 이미지 URL   |
| likeCount       | Int       | 좋아요 수     |
| createdAt       | Timestamp | 게시글 작성 시간 |

---

# Firestore 사용 목적

| 데이터      | 저장 위치              |
| -------- | ------------------ |
| 사용자 프로필  | users 컬렉션          |
| 커뮤니티 게시글 | communityPosts 컬렉션 |

---

# 3. Firebase Storage

## 설명

Firebase Storage는 커뮤니티 게시글에 업로드되는 이미지 파일을 저장하기 위해 사용한다.

## 저장 데이터

* 운동 인증 사진
* 게시글 첨부 이미지

## 저장 구조 예시

```text id="q1z0bz"
community/
 └── userId/
      └── imageFile.jpg
```

---

# Firebase 보안 규칙

## Authentication 규칙

* 로그인한 사용자만 Firestore 접근 가능
* 로그인한 사용자만 게시글 작성 가능

## Firestore 규칙 예시

```text
allow read: if request.auth != null;
allow write: if request.auth != null;
```

---

# Firebase 역할 정리

| 기능          | Firebase 서비스            |
| ----------- | ----------------------- |
| 로그인 / 회원가입  | Firebase Authentication |
| 사용자 프로필 저장  | Cloud Firestore         |
| 커뮤니티 게시글 저장 | Cloud Firestore         |
| 이미지 업로드     | Firebase Storage        |

---

# 예상 데이터 흐름

```text id="f5h4zn"
회원가입
→ Firebase Authentication 사용자 생성
→ Firestore users 컬렉션에 사용자 정보 저장

게시글 작성
→ Firebase Storage 이미지 업로드
→ Firestore communityPosts 컬렉션에 게시글 저장
```

---

# 장점

1. 실시간 데이터 동기화 가능
2. 사용자 인증 기능 제공
3. 클라우드 기반 데이터 저장 가능
4. 이미지 업로드 기능 제공
5. Android와 높은 호환성 제공

---
