# Room DB 설계 문서

## 중간보고서 기준 구현 내용

중간보고서용 로컬 MVP에서는 Firebase/Firestore 연동 없이 Room DB로 운동 기록과 식단 기록을 저장한다. `AppDatabase`는 `ExerciseEntity`, `DietEntity` 두 테이블을 관리하며, ViewModel은 DAO를 직접 호출하지 않고 Repository를 통해 데이터에 접근한다.

현재 구현된 주요 흐름은 다음과 같다.

* 운동 기록: 추가, 오늘 날짜 기준 조회, 전체 조회, 삭제
* 식단 기록: 추가, 오늘 날짜 기준 조회, 전체 조회, 즐겨찾기 조회, 즐겨찾기 상태 변경, 삭제
* 조회 반환 타입: `Flow<List<ExerciseEntity>>`, `Flow<List<DietEntity>>`
* 운동 칼로리 계산식: `setCount * repCount * weight * 0.05`
* 식단 칼로리: 사용자가 입력한 값을 그대로 저장

임시 로그인 상태에서는 `demo_user` 값을 로컬 사용자 식별자로 사용한다. 실제 Firebase 사용자 uid 연동은 추후 구현 범위이다.

## 데이터베이스 개요

Room DB는 사용자의 개인 운동 기록과 식단 기록을 로컬에 저장하기 위해 사용한다.

---

# 1. ExerciseEntity

## 설명

사용자의 운동 기록 데이터를 저장한다.

## 컬럼 구조

| 컬럼명          | 타입     | 설명        |
| ------------ | ------ | --------- |
| id           | Int    | 기본 키      |
| userId       | String | 사용자 uid   |
| date         | String | 운동 날짜     |
| exerciseName | String | 운동명       |
| setCount     | Int    | 세트 수      |
| repCount     | Int    | 반복 횟수     |
| weight       | Double | 운동 무게     |
| calorie      | Int    | 예상 소모 칼로리 |

## Kotlin 구조 예시

```kotlin
@Entity(tableName = "exercises")
data class ExerciseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: String,

    val date: String,

    val exerciseName: String,

    val setCount: Int,

    val repCount: Int,

    val weight: Double,

    val calorie: Int
)
```

---

# 2. DietEntity

## 설명

사용자의 식단 기록 데이터를 저장한다.

## 컬럼 구조

| 컬럼명          | 타입      | 설명      |
| ------------ | ------- | ------- |
| id           | Int     | 기본 키    |
| userId       | String  | 사용자 uid |
| date         | String  | 식단 날짜   |
| foodName     | String  | 음식명     |
| calorie      | Int     | 칼로리     |
| carbohydrate | Int     | 탄수화물    |
| protein      | Int     | 단백질     |
| fat          | Int     | 지방      |
| isFavorite   | Boolean | 즐겨찾기 여부 |

## Kotlin 구조 예시

```kotlin
@Entity(tableName = "diets")
data class DietEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: String,

    val date: String,

    val foodName: String,

    val calorie: Int,

    val carbohydrate: Int,

    val protein: Int,

    val fat: Int,

    val isFavorite: Boolean = false
)
```

---

# 3. ExerciseDao

## 설명

운동 기록 데이터를 조회 및 저장하는 DAO 클래스이다.

## 주요 기능

* 운동 기록 저장
* 운동 기록 조회
* 운동 기록 삭제

## 예상 함수

```kotlin
@Insert
suspend fun insertExercise(exercise: ExerciseEntity)

@Query("SELECT * FROM exercises WHERE userId = :userId AND date = :date ORDER BY id DESC")
fun getExercisesByDate(userId: String, date: String): Flow<List<ExerciseEntity>>

@Query("SELECT * FROM exercises WHERE userId = :userId ORDER BY date DESC, id DESC")
fun getAllExercises(userId: String): Flow<List<ExerciseEntity>>

@Delete
suspend fun deleteExercise(exercise: ExerciseEntity)
```

---

# 4. DietDao

## 설명

식단 기록 데이터를 조회 및 저장하는 DAO 클래스이다.

## 주요 기능

* 식단 기록 저장
* 식단 기록 조회
* 즐겨찾기 음식 조회
* 식단 기록 삭제

## 예상 함수

```kotlin
@Insert
suspend fun insertDiet(diet: DietEntity)

@Query("SELECT * FROM diets WHERE userId = :userId AND date = :date ORDER BY id DESC")
fun getDietsByDate(userId: String, date: String): Flow<List<DietEntity>>

@Query("SELECT * FROM diets WHERE userId = :userId AND isFavorite = 1 ORDER BY foodName ASC")
fun getFavoriteDiets(userId: String): Flow<List<DietEntity>>

@Query("UPDATE diets SET isFavorite = :isFavorite WHERE id = :id")
suspend fun updateFavorite(id: Int, isFavorite: Boolean)

@Delete
suspend fun deleteDiet(diet: DietEntity)
```

---

# 5. AppDatabase

## 설명

ExerciseEntity와 DietEntity를 관리하는 Room Database 클래스이다.

## 포함 테이블

* exercises
* diets

## 예상 구조

```kotlin
@Database(
    entities = [
        ExerciseEntity::class,
        DietEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    abstract fun dietDao(): DietDao
}
```

---
