# 프로젝트 폴더 구조

## 프로젝트 구조 개요

본 프로젝트는 기능 단위(feature)와 공통 기능(core)을 분리하여 관리한다.

화면 기능은 feature 폴더에서 관리하고, 공통 데이터 처리 및 API 연동은 core 폴더에서 관리한다.

---

# 전체 폴더 구조

```text
soongsilHealthcareService/
│
├── app/
│
├── docs/
│
├── navigation/
│
├── core/
│   ├── model/
│   ├── data/
│   │   ├── local/
│   │   ├── remote/
│   │   └── repository/
│   │
│   ├── ui/
│   │   ├── component/
│   │   └── theme/
│   │
│   └── util/
│
├── feature/
│   ├── auth/
│   ├── home/
│   ├── exercise/
│   ├── diet/
│   ├── aicoach/
│   ├── community/
│   └── mypage/
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

# feature 폴더 역할

feature 폴더는 화면 단위 기능을 관리한다.

각 기능별 화면(Screen), ViewModel 및 UI 관련 코드를 포함한다.

---

## auth

### 역할

로그인 및 회원가입 기능을 담당한다.

### 포함 예정 파일

```text id="rqcru3"
LoginScreen.kt
SignUpScreen.kt
AuthViewModel.kt
```

---

## home

### 역할

메인 홈 화면 기능을 담당한다.

### 포함 예정 파일

```text id="thx86l"
HomeScreen.kt
HomeViewModel.kt
```

---

## exercise

### 역할

운동 기록 기능을 담당한다.

### 포함 예정 파일

```text id="nczb50"
ExerciseScreen.kt
ExerciseAddScreen.kt
ExerciseViewModel.kt
```

---

## diet

### 역할

식단 기록 기능을 담당한다.

### 포함 예정 파일

```text id="zrz1so"
DietScreen.kt
DietAddScreen.kt
DietViewModel.kt
```

---

## aicoach

### 역할

AI 코치 기능을 담당한다.

### 포함 예정 파일

```text id="r91ln9"
AiCoachScreen.kt
AiCoachViewModel.kt
```

---

## community

### 역할

커뮤니티 기능을 담당한다.

### 포함 예정 파일

```text id="1fl0l4"
CommunityScreen.kt
PostWriteScreen.kt
CommunityViewModel.kt
```

---

## mypage

### 역할

마이페이지 및 설정 기능을 담당한다.

### 포함 예정 파일

```text id="6cdyx2"
MyPageScreen.kt
SettingScreen.kt
MyPageViewModel.kt
```

---

# core 폴더 역할

core 폴더는 공통 데이터 처리 및 공통 기능을 관리한다.

---

## model

### 역할

공통 데이터 모델 클래스를 관리한다.

### 포함 예정 파일

```text id="jlwmvc"
UserProfile.kt
ExerciseRecord.kt
DietRecord.kt
CommunityPost.kt
```

---

## data/local

### 역할

Room DB 관련 코드를 관리한다.

### 포함 예정 파일

```text id="0aj3pn"
AppDatabase.kt
ExerciseDao.kt
DietDao.kt
ExerciseEntity.kt
DietEntity.kt
```

---

## data/remote

### 역할

Firebase 및 Gemini API 연동 코드를 관리한다.

### 포함 예정 파일

```text id="xbgw6x"
AuthService.kt
FirestoreService.kt
GeminiService.kt
```

---

## data/repository

### 역할

Repository 계층을 관리한다.

### 포함 예정 파일

```text id="3ftm9w"
AuthRepository.kt
ExerciseRepository.kt
DietRepository.kt
AiCoachRepository.kt
CommunityRepository.kt
```

---

## ui/component

### 역할

공통 UI 컴포넌트를 관리한다.

### 포함 예정 파일

```text id="gq7e4c"
CommonButton.kt
CommonTopBar.kt
LoadingDialog.kt
```

---

## ui/theme

### 역할

앱의 테마 및 색상 설정을 관리한다.

### 포함 예정 파일

```text id="n6c1n7"
Color.kt
Theme.kt
Type.kt
```

---

## util

### 역할

공통 유틸리티 기능을 관리한다.

### 포함 예정 파일

```text id="ykg6o0"
DateUtil.kt
CalorieCalculator.kt
```

---

# navigation 폴더 역할

앱의 화면 이동(Navigation)을 관리한다.

---

## 포함 예정 파일

```text id="7ljv66"
AppNavGraph.kt
Routes.kt
```

---

# docs 폴더 역할

프로젝트 설계 문서 및 개발 문서를 관리한다.

---

## 포함 문서

```text id="8y5g5r"
feature_plan.md
db_design.md
firebase_design.md
ai_prompt.md
folder_structure.md
branch_rule.md
role.md
```

---

# 구조 설계 목적

1. 기능별 코드 분리
2. 유지보수 편의성 향상
3. 팀 협업 시 충돌 감소
4. MVVM 구조 적용
5. 데이터 처리와 UI 분리

---

# 적용 아키텍처

본 프로젝트는 MVVM(Model-View-ViewModel) 구조를 기반으로 개발한다.

| 계층         | 역할                 |
| ---------- | ------------------ |
| View       | Compose UI 화면      |
| ViewModel  | UI 상태 관리           |
| Repository | 데이터 처리             |
| DataSource | Room DB 및 Firebase |
| Model      | 데이터 구조             |

---
