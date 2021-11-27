## 1. 필터링

### 1. 프레디케이트로 필터링

filter 메서드는 **프레디케이트**(불리언을 반환하는 함수)를 인수로 받아서 프리데케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.

```java
// 모든 채식요리를 필터링해서 채식 메뉴를 만듦
List<Dish> vegetarianMenu = menu.stream()
    .filter(Dish::isVegetarian) // 채식 요리인지 확인하는 메서드 참조
    .collect(toList());
```



### 2. 고유 요소 필터링

스트림은 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드도 지원한다.

```java
// 리스트의 모든 짝수를 선택하고 중복을 필터링
List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
numbers.stream()
    .filter(i -> i % 2 == 0)
    .distinct()
    .forEach(System.out::println);
```



## 2. 스트림 슬라이싱

### 1. 프레디케이트를 이용한 슬라이싱

자바 9는 스트림의 요소를 효과적으로 선택할 수 있도록 takeWhile, dropWhile 두 가지 새로운 메서드를 지원한다.

- `TAKEWHILE` 활용

  ```java
  List<Dish> specialMenu = Arrays.asList(
  	new Dish("sesonal fruit", true, 120, Dish.Type.OTHER),
      new Dish("prawns", false, 300, Dish.Type.FISH),
      new Dish("rice", ture, 350, Dish.Type.OTHER),
      new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER)
  );
  
  // 320 칼로리 이하의 요리 선택 => filter를 사용해보자
  List<Dish> filteredMenu
      = specialMenu.stream()
      .filter(dish -> dish.getCalopries() < 320)
      .collect(toList());
  // filter 연산을 이용하면 전체 스트림을 반복하면서 각 요소에 프레디케이트를 적용하게 된다.
  // 리스트가 이미 정렬되어있다는 사실을 이용해 320칼로리보다 크거나 같은 요리가 나왔을 때
  // 반복 작업을 중단할 수 있다
  
  // takeWhile을 이용하면 무한스트림을 포함한 모든 스트림에 프레디케이트를 적용해
  // 스트림을 슬라이스할 수 있다.
  List<Dish> slicedMenu1
      = specialMenu.stream()
      .takeWhile(dish -> dish.getCalories() < 320)
      .collect(toList());
  ```

- `DROPWHILE` 활용

  ```java
  // dropWhile은 takeWhile과 정반대의 작업을 수행한다.
  // dropWhile은 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.
  List<Dish> slicedMenu2
      = specialMenu.stream()
      .dropWhile(dish -> dish.getCalories() < 320)
      .collect(toList());
  ```



### 2. 스트림 축소

스트림은 주어진 값 이하의 크기를 갖는 새로운 스트림을 반환하는 **limit(n)** 메서드를 지원한다.

스트림이 정렬되어 있으면 최대 요소 n개를 반환할 수 있다.

```java
List<Dish> dishes = specialMenu.stream()
    .filter(dish-> dish.getCalories() > 300)
    .limit(3) // 300칼로리 이상의 세 요리를 선택해서 리스트를 만듦
    .collect(toList());
```



### 3. 요소 건너뛰기

스트림은 처음 n개 요소를 제외한 스트림을 반환하는 **skip(n)** 메서드를 지원한다.

n개 이하의 요소를 포함하는 스트림에 skip(n)을 호출하면 빈 스트림이 반환된다.

```java
List<Dish> dishes = menu.stream()
    .filter(d-> d.getCalories() > 300)
    .skip(2) // 300칼로리 이상의 처음 두 요리를 건너뛴 다음 300칼로리가 넘는 나머지 요리 반환
    .collect(toList());
```



## 3. 매핑

스트림 API의 map과 flatMap 메서드는 특정 데이터를 선택하는 기능을 제공한다.



### 1. 스트림의 각 요소에 함수 적용하기

스트림은 함수를 인수로 받는 map 메서드를 지원한다.

인수로 제공된 함수는 각 요소에 적용되며 함수를 적용한 결과가 새로운 요소로 매핑된다.

```java
// Dish::getName을 map 메서드로 전달해서 스트림의 요리명을 추출하는 코드
List<String> dishNames = menu.stream()
    .map(Dish::getName)
    .collect(toList());

// 단어 리스트가 주어졌을 때 각 단어가 포함하는 글자 수의 리스트를 반환
// 각 요소에 적용할 함수는 단어를 인수로 받아서 길이를 반환
List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
List<Integer> wordLengths = words.stream()
    .map(String::length)
    .collect(toList());

// 요리명의 길이 추출
List<Integer> dishNameLengths = menu.stream()
    .map(Dish::getName)
    .map(String::length)
    .collect(toList());

```



### 2. 스트림 평면화

리스트에서 고유 문자로 이루어진 리스트를 반환

flatMap은 각 배열을 스트림이 아니라 스트림의 콘텐츠로 매핑한다.

```java
// 1. 리스트에 있는 각 단어를 문자로 매핑한 다음
// distinct로 중복된 문자를 필터링해봄
words.stream()
    .map(word->word.split(""))
    .distinct()
    .collect(toList());
/**
* map으로 전달한 람다는 각 단어의 String[]을 반환한다.
* 따라서 map 메소드가 반환한 스트림의 형식은 Stream<String[]> 이다
* 우리가 원하는 것은 Stream<String>
*/

// 2. map과 Arrays.stream 활용
// Arrays.stream() : 문자열을 받아 스트림을 만들어줌
String[] arrayOfWords = {"GoodBye", "World"};
Stream<String> streamOfwords = Arrays.stream(arrayOfWords);

words.stream()
    .map(word -> word.split("")) // 각 단어를 개별 문자열 배열로 변환
    .map(Arrays::stream) // 각 배열을 별도의 스트림으로 생성
    .distinct()
    .collect(toList());

/**
* 결국 스트림 리스트 (List<Stream<String>>) 가 만들어지면서 문제가 해결되지 않음
* 문제를 해결하려면 먼저 각 단어를 개별 문자열로 이루어진 배열로 만든 다음에
* 각 배열을 별도의 스트림으로 만들어야 함
*/

// 3. flatMap 사용
List<String> uniqueCharacters = words.stream()
    .map(word->word.split("")) // 각 단어를 개별 문자를 포함하는 배열로 변환
    .flatMap(Arrays::stream) // 생성된 스트림을 하나의 스트림으로 평면화
    .distinct()
    .collect(toList());
```

flatMap 메서드는 스트림의 각 값을 다른 스트림으로 만든 다음에 모든 스트림을 하나의 스트림으로 연결하는 기능 수행



## 4. 검색과 매칭

스트림 API는 allMatch, anyMatch, noneMatch, findFirst, findAny



### 1. 프레디케이트가 적어도 한 요소와 일치하는지 확인

프레디케이트가 주어진 스트림에서 적어도 한 요소와 일치하는지 확인할 때

`anyMatch` 메서드를 이용한다.

```java
// menu에 채식요리가 있는지 확인
if (menu.stream().anyMatch(Dish::isVegetarian)) {
    System.out.println("The menu is (somewhat) vegetarian friendly")
}
```



### 2. 프레디케이트가 모든 요소와 일치하는지 검사

`allMatch` 메서드는 anyMatch와 달리 스트림의 모든 요소가 주어진 프레디케이트와 일치하는지 검사

```java
// 메뉴가 건강식인지 확인할 수 있음
boolean isHealthy = menu.stream()
    .allMatch(dish -> dish.getCalories() < 1000);

// noneMatch는 allMatch와 반대 연산 수행
boolean isHealthy2 = menu.stream()
    .noneMatch(d-> d.getCalories() >= 1000);
```



### 3. 요소 검색

`findAny` 메서드는 현재 스트림에서 임의의 요소를 반환한다.

findAny 메서드를 다른 스트림 연산과 연결해서 사용할 수 있다

```java
Optional<Dish> dish = 
    menu.stream()
    .filter(Dish::isVegetarian)
    .findAny();
```

- `isPresent() ` : Optional이 값을 포함하면 참을 반환하고 값을 포함하지 않으면 거짓을 반환한다.
- `ifPresent(Consumer<T> block)` : 값이 있으면 주어진 블록을 실행한다
- `T get()` : 값이 존재하면 값을 반환하고 값이 없으면 NoSuchElementException을 일으킨다.
- `T orElse (T other)`는 값이 있으면 값을 반환하고, 값이 없으면 기본값을 반환한다.

```java
menu.stream()
    .filter(Dish::isVegetarian)
    .findAny() // Optional<Dish> 반환
    .ifPresent(dish-> System.out.println(dish.getName())); // 값이 있으면 출력되고, 값이 없으면 아무일도 일어나지 앟는ㄴ다
```



### 4. 첫 번째 요소 찾기

`findFirst`



## 5. 리듀싱

모든 스트림 요소를 처리해서 값으로 도출하는 질의를 **리듀싱 연산** 이라고 한다.



### 1. 요소의 합

```java
// for-each 루프를 이용해서 리스트의 숫자 요소를 더하는 코드
int sum =0; // sum 변수의 초깃값 0
for (int x : numbers) {
    sum += x; // 리스트의 모든 요소를 조합하는 연산 (+)
}

// reduce를 이용하면 애플리케이션의 반복된 패턴을 추상화 할 수 있음
int sum1 = numbers.stream().reduce(0, (a,b) -> a+b);
```

![캡처](C:\Users\82104\Desktop\study\java\캡처.PNG)!![캡처](https://user-images.githubusercontent.com/58503875/142384972-79fa2aba-03b1-4c55-bfb4-0f7d5dcd130e.PNG)