# Room DB 설계 문서

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
@Entity(tableName = "exercise")
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
@Entity(tableName = "diet")
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

@Query("SELECT * FROM exercise WHERE date = :date")
suspend fun getExerciseByDate(date: String): List<ExerciseEntity>

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

@Query("SELECT * FROM diet WHERE date = :date")
suspend fun getDietByDate(date: String): List<DietEntity>

@Delete
suspend fun deleteDiet(diet: DietEntity)
```

---

# 5. AppDatabase

## 설명

ExerciseEntity와 DietEntity를 관리하는 Room Database 클래스이다.

## 포함 테이블

* exercise
* diet

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
