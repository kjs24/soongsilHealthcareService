# Git 브랜치 규칙

## 브랜치 관리 목적

본 프로젝트는 GitHub 기반 협업을 진행하기 위해 브랜치 규칙을 정의한다.

브랜치 분리를 통해 기능별 개발을 독립적으로 진행하고, 코드 충돌을 최소화하는 것을 목표로 한다.

---

# 브랜치 구조

| 브랜치       | 역할        |
| --------- | --------- |
| main      | 최종 안정 버전  |
| develop   | 개발 통합 브랜치 |
| feature/* | 기능 개발 브랜치 |

---

# 브랜치 설명

## main 브랜치

### 역할

최종 안정 버전을 관리한다.

### 규칙

* 직접 push 금지
* 테스트 완료 후 merge
* 배포 가능한 코드만 유지

---

## develop 브랜치

### 역할

개발 중인 기능을 통합하는 브랜치이다.

### 규칙

* feature 브랜치 merge 대상
* 최신 개발 코드 유지
* 기능 테스트 진행

---

## feature 브랜치

### 역할

기능별 개발을 진행한다.

### 규칙

* 기능 단위로 브랜치 생성
* 개발 완료 후 develop 브랜치로 merge
* 작업 완료 후 삭제 가능

---

# 브랜치 이름 규칙

## 형식

```text id="4z5rws"
feature/기능이름
```

---

## 예시

```text id="6eb9ij"
feature/auth
feature/home
feature/exercise
feature/diet
feature/aicoach
feature/community
feature/mypage
```

---

# Git 작업 흐름

## 1. develop 브랜치 이동

```bash id="2h5s7n"
git checkout develop
```

---

## 2. 최신 코드 가져오기

```bash id="jpmr6w"
git pull origin develop
```

---

## 3. feature 브랜치 생성

```bash id="czuybn"
git checkout -b feature/exercise
```

---

## 4. 기능 개발 진행

```text id="c13wpa"
기능 구현 및 테스트 수행
```

---

## 5. 변경 사항 저장

```bash id="8vbafp"
git add .
git commit -m "feat: add exercise feature"
```

---

## 6. GitHub 업로드

```bash id="gbv5pp"
git push origin feature/exercise
```

---

## 7. Pull Request 생성

```text id="3o4zmy"
feature/exercise → develop
```

---

# Pull Request 규칙

1. develop 브랜치 기준으로 Pull Request를 생성한다.
2. 앱 빌드 확인 후 merge한다.
3. 충돌 발생 시 팀원과 상의 후 해결한다.
4. 기능 동작 확인 후 merge한다.

---

# 커밋 메시지 규칙

| 타입       | 설명       |
| -------- | -------- |
| feat     | 기능 추가    |
| fix      | 버그 수정    |
| design   | UI 수정    |
| refactor | 코드 구조 개선 |
| docs     | 문서 수정    |
| chore    | 설정 파일 수정 |

---

# 커밋 메시지 예시

```text id="mjlwmv"
feat: add login feature
feat: implement room database
fix: resolve firebase login issue
design: update home screen ui
docs: add firebase design document
```

---

# 협업 규칙

1. main 브랜치에는 직접 push하지 않는다.
2. 작업 전 반드시 pull을 수행한다.
3. build.gradle.kts 수정 시 팀원과 공유한다.
4. MainActivity.kt 수정 시 팀원과 공유한다.
5. 같은 파일 동시 수정 시 팀원과 상의한다.
6. API Key는 GitHub에 업로드하지 않는다.

---

# 충돌 발생 가능 파일

아래 파일은 충돌 가능성이 높으므로 수정 전 팀원과 공유한다.

```text id="k2rwfa"
MainActivity.kt
AppNavGraph.kt
build.gradle.kts
AndroidManifest.xml
Theme.kt
```

---

# 브랜치 전략 목적

1. 기능별 코드 분리
2. 코드 충돌 최소화
3. 안정적인 버전 관리
4. 협업 효율 향상
5. 유지보수 편의성 향상

---
