## 스트림

### 1. 스트림이란?

스트림을 이용하면 선언형으로 컬렉션 데이터를 처리할 수 있다.

또한, 멀티스레드 코드를 구현하지 않아도 데이터를 투명하게 병렬로 처리할 수 있다.



**기존 코드**

```java
void oldCode(){
    // 기존 코드
    // 1. 가비지 변수를 선언한다
    List<Dish> lowCaloricDishes = new ArrayList<>();

    // 2. 칼로리가 400 미만으로 필터링 한다
    for(Dish dish: menu) {
        if (dish.getCalories() < 400) {
            lowCaloricDishes.add(dish);
        }
    }

    // 3. 필터링된 가비지 변수를 정렬한다
    Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
        public int compare(Dish dish1, Dish dish2) {
            return Integer.compare(dish1.getCalories(), dish2.getCalories());
        }
    });

    // 4. 가비지 변수의 이름만 가져와 새로 담는다
    List<String> lowCaloricDishesName = new ArrayList<>();
    for(Dish dish: lowCaloricDishes) {
        lowCaloricDishesName.add(dish.getName());
    }
}
```



**java8 코드**

```java
void newCode(){
    List<String> lowCaloricDishesName =
        menu.stream()
        .filter(d-> d.getCalories() < 400) // 400 칼로리 이하의 요리 선택
        .sorted(comparing(Dish::getCalories)) // 칼로리로 요리 정렬
        .map(Dish::getName) // 요리명 추출
        .collect(toList()); // 모든 요리명을 리스트에 저장

}
```

>**⬆에서 사용한 스트림 연산들**
>
>**👉filter**
>
>람다를 인수로 받아 스트림에서 특정 요소를 제외시킨다.
>
>**👉map**
>
>람다를 이용해서 한 요소를 다른 요소로 변환하거나 정보를 추출한다.
>
>**👉limit**
>
>정해진 개수 이상의 요소가 스트림에 저장되지 못하게 스트림 크기를 축소한다
>
>**👉collect**
>
>스트림을 다른 형식으로 변환한다.
>
>

### 2. 스트림 시작하기

**스트림이란?**

| 정의             | 설명                                                         |
| ---------------- | ------------------------------------------------------------ |
| 연속된 요소      | 컬렉션에서는 요소 저장 및 접근 연산이 주를 이룬다면 **스트림에서는 filter, sorted, map 처럼 표현 계산식이 주를 이룬다.**<br />➡`컬렉션`의 주제는 `데이터` 이고 `스트림` 의 주제는 `계산` 이다. |
| 소스             | 리스트로 스트림을 만들면 스트림의 요소는 리스트의 요소와 같은 순서를 유지한다. |
| 데이터 처리 연산 | 스트림 연산은 순차적 또는 병렬로 실행할 수 있다.             |
| 파이프라이닝     | 대부분으 ㅣ스트림 연산은 스트림 연산끼리 연결해서 커다란 파이프라인을 구성할 수 있도록 스트림 자신을 반환한다. |
| 내부 반복        | 반복자를 이용해서 명시적으로 반복하는 컬렉션과 달리, 스트림은 내부 반복을 지원한다. |



### 3. 스트림과 컬렉션

**데이터를 언제 계산하느냐**가 컬렉션과 스트림의 가장 큰 차이다.

컬렉션은 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조

스트림은 요청할 때만 요소를 계산하는 고정된 자료구조

스트림 : 내부 반복

컬렉션 : 외부 반복



### 4. 스트림 연산

1. **중간 연산** : 연결할 수 있는 스트림 연산

   중간 연산의 중요한 특징은 단말 연산을 스트림 파이프라인에 실행하기 전까지는 아무 연산도 수생하지 않는다는 것이다. 중간 연산을 합친 다음에 합쳐진 중간 연산을 최종 연산으로 한 번에 처리

   | 연산     | 반환형식  | 연산의인수     | 함수 디스크립터 |
      | -------- | --------- | -------------- | --------------- |
   | filter   | Stream<T> | Predicate<T>   | T -> boolean    |
   | map      | Stream<R> | Function<T, R> | T -> R          |
   | limit    | Stream<T> |                |                 |
   | sorted   | Stream<T> | Comparator<T>  | (T,T) -> int    |
   | distinct | Stream<T> |                |                 |





2. **최종 연산** : 스트림을 닫는 연산

   보통 최종 연산에 의해 스트림 이외의 결과가 반환된다.

   | 연산    | 반환 형식     | 목적                                                         |
      | ------- | ------------- | ------------------------------------------------------------ |
   | forEach | void          | 스트림의 각 요소를 소비하면서 람다를 적용한다                |
   | count   | long(generic) | 스트림의 요소 개수를 반환한다.                               |
   | collect |               | 스트림을 리듀스해서 리스트, 맵, 정수 형식의 컬렉션을 만든다. |

   

