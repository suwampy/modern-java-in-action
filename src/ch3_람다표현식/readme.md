## 람다 표현식

### 1. 람다란

| 특징       | 설명                                                         |
| ---------- | ------------------------------------------------------------ |
| **익명**   | 보통의 메서드와 달리 이름이 없음                             |
| **함수**   | 람다는 메서드처럼 *특정 클래스에 종속되지 않음*<br />하지만 메서드처럼 파라미터 리스트, 바디, 반환 형식, 가능한 예외 리스트를 포함 |
| **전달**   | 메서드 인수로 전달하거나 변수로 저장할 수 있음               |
| **간결성** | 간결함                                                       |

```java
// 기존 Comparator 객체
Comparator<Apple> byWeight = new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2){
        return a1.getWeight().compareTo(a2.getWeight());
    }
};

// 람다 이용
Comparator<Apple> byWeight = (Apple a1, Apple a2)-> a1.getWeight().compareTo(a2.getWeight)
```

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fc5a3X5%2Fbtq97OjUKXz%2F24qbKVatsCrkmNxmAVyQk1%2Fimg.png)

```java
// String 형식의 파라미터 하나를 가지며 int를 반환한다.
// 람다 표현식에는 return이 함축되어 으므로 return 문을 명시적으로 사용하지 않아도 된다
(String s) -> s.length()

// Apple 형식의 파라미터 하나를 가지며
// boolean (사과가 150그램보다 무거운지 결정)을 반환한다
(Apple a) -> a.getWeight() > 150

// int 형식의 파라미터 두 개를 가지며 리턴값이 없다(void 리턴)
// 이 예제에서 볼 수 있뜻이 람다 표현식은 여러 행의 문장을 포함할 수 있다.
(int x , int y) -> {
    System.out.println("Result:");
    System.out.println(x + y);
}

// 파라미터가 없으며 int 42를 반환한다.
() -> 42;

// Apple 형식의 파라미터 두 개를 가지며
// int(두 사과의 무게 비교 결과)를 반환한다.
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())

```

| 사용 사례          | 람다 예제                                                    |
| ------------------ | ------------------------------------------------------------ |
| 불리언 표현식      | (List<String> list) -> list.isEmpty()                        |
| 객체 생성          | ()->new Apple(10)                                            |
| 객체에서 소비      | (Apple a) -> {<br /><br />System.out.println(a.getWeight());<br />} |
| 객체에서 선택/추출 | (String s) -> s.length()                                     |
| 두 값을 조합       | (int a, int b) -> a*b                                        |
| 두 객체 비교       | (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()) |



### 2. 람다 사용하기

1. 함수형 인터페이스
    - 함수형 인터페이스는 정확히 하나의 추상 메서드를 지정하는 인터페이스
    - 람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으므로 **전체 표현식을 함수형 인터페이스의 인스턴스로 취급**(기술적으로 따지면 함수형 인터페이스를 구현한 클래스의 인스턴스) 할 수 있다.

2. 함수 디스크럽터

    - 람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라고 부른다.

    - ()->void 표기는 파라미터 리스트가 없으며 void를 반환하는 함수를 의미한다.

      ```java
      public void process(Runnable r) {
          r.run();
      }
      
      process(()-> System.out.println("This is awesome!!"));
      ```

> ### @FunctionalInterface
>
> : 함수형 인터페이스임을 가리키는 어노테이션

### 3. 람다 활용 : 실행 어라운드 패턴

실행 어라운드 패턴 : 실제 자원을 처리하는 코드를 설정과 정리 두 과정이 둘러싸는 형태

```java
public String processFile() throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) { // 초기화, 준비코드 + 정리,마무리 코드
        return br.readLine(); // 실제 필요한 작업을 하는 행
    }
}
```

1. **1단계 : 동작 파라미터화를 기억하라**

    - processFile의 동작을 파라미터화 하자 : processFile 메서드가 BufferedReader를 이용해서 다른 동작을 수행할 수 있도록 processFile 메서드로 동작을 전달해야 함

   ```java
   // BufferedReader를 인수로 받아서 String을 반환하는 람다가 필요
   // BufferedREader에서 두 행을 출력하는 코드
   String result = processFile((BufferedREader br) -> br.readLine() + br.readLine());
   ```

2. **2단계 : 함수형 인터페이스를 이용해서 동작 전달**

    - BufferedReader->String과 IOException을 던질 수 있는 시그니처와 일치하는 함수형 인터페이스를 만들어야 함

      ```java
      @FunctionalInterface
      public interface BufferedReaderProcessor {
          String process(BufferedReader b) throws IOException;
      }
      ```

3. **3단계 : 동작 실행**

   ```java
   public String processFile(BufferedeReaderProcessor p) throws IOException {
       try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
           return p.process(br); // BufferedReader 객체 처리
       }
   }
   ```

4.  **4단계 : 람다 전달**

```java
String oneLine = processFile((BufferedREader br) -> br.readLine());
String twoLines = processFile((BufferedREader br) -> br.readLine() + br.readLine());
```



### 4. 함수형 인터페이스 사용

1. **Predicate**

    - `java.util.function.Predicate<T>` 인터페이스는 test라는 추상 메서드를 정의하며 test는 제네릭 형식 T의 객체를 인수로 받아 **불리언**을 반환

   ```java
   @FunctionalInterface
   public interface Predicate<T> {
       boolean test(T t);
   }
   
   public <T> List<T> filter(List<T> list, Predicate<T> p) {
       List<T> results = new ArrayList<>();
       for (T t : list) {
           if(p.test(t)) {
               results.add(t);
           }
       }
       
       return results;
   }
   
   Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
   List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
   ```

2. **Consumer**

    - `java.util.function.Consumer<T>` 인터페이스는 제네릭 형식 T 객체를 받아서 void를 반환하는 accept라는 추상 메서드를 정의한다.
    - T 형식의 객체를 인수로 받아서 어떤 동작을 수행하고 싶을 떄 Consumer 인터페이스를 사용할 수 있다

   ```java
   // forEach와 람다를 이용해서 리스트의 모든 항목을 출력하는 예제
   @FunctionalInterface
   public interface Consumer<T> {
       void accept(T t);
   }
   
   public <T> void forEach(List<T> list, Consumer<T> c) {
       for (T t : list){
           c.accept(t);
       }
   }
   forEach(
   	Arrays.asList(1,2,3,4,5), // Integer 리스트를 인수로 받음
       (Integer i) ->System.out.println(i); // Consumer의 accept 메서드를 구현하는 람다
   )
   ```

3. **Function**

    - `java.util.function.Function<T,R>` 인터페이스는 제네릭 형식 T를 인수로 받아서 제네릭 형식 R 객체를 반환하는 추상 메서드 apply를 정의한다.
    - 입력을 출력으로 매핑하는 람다를 정의할 때 Function 인터페이스를 활용할 수 있다)

   ```java
   // String 리스트를 인수로 받아 각 String의 길이를 포함하는 Integer 리스트로 변환하는 map 메서드를 정의하는 예제
   @FunctionalInterface
   public interface Function<T, R> {
       R apply(T t);
   }
   
   public <T, R> List<R> map(List<T> list, Function<T, R> f) {
       List<R> result = new ArrayList<>();
       for(T t : list) {
           result.add(f.apply(t));
       }
       return result;
   }
   
   // [7, 2, 6]
   List<Integer> l = map(
   	Arrays.asList("lambdas", "in", "action"),
       (String s)->s.length()
   );
   ```



### 5. 형식 검사, 형식 추론, 제약

1. **형식 검사**
    - 람다가 사용되는 콘텍스트를 이용해서 람다의 형식을 추론할 수 있다. 어떤 콘텍스트에서 기대되는 람다 표현식의 형식을 대상 형식이라고 부른다.
    - `List<Apple> heavierThan150g = filter(inventory, (Apple apple) -> apple.getWeight() > 150);`
        1. filter 메서드의 선언을 확인한다
        2. filter 메서드는 두 번째 파라미터로 Predicate<Apple> 형식을 기대한다
        3. Predicate<Apple> 은 test라는 한 개의 추상 메서드를 정의하는 함수형 인터페이스다
        4. test 메서드는 Apple을 받아 boolean을 반환하는 함수 디스크립터를 묘사
        5. filter 메서드로 전달된 인수는 이와 같은 요구사항을 만족해야 한다

2. 같은 람다, 다른 함수형 인터페이스
3. 형식 추론
4. 지역 변수 사용



### 6. 메서드 참조

메서드 참조를 이용하면 기존의 메서드 정의를 재활용해서 람다처럼 전달할 수 있다.

```java
inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
inventory.sort(comparing(Apple::getWeight)); 
```

**메서드 참조를 만드는 방법**

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbhIf8F%2FbtranXnjnJ7%2FpsF2Dmw1uOTsAZtkfkqJ30%2Fimg.png)

### 7. ㅇㅖ제

```java
public class Sort {
    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));

        // 2단계 : 익명 클래스 활용
        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2){
                return a1.getWeight()-a2.getWeight();
            }
        });

        // 3단계 : 람다 표현식 사용
        inventory.sort((Apple a1, Apple a2)-> a1.getWeight()-a2.getWeight());

        // 4단계 : 메서드 참조 사용
        inventory.sort(comparing(Apple::getWeight));

    }

    static class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight() - a2.getWeight();
        }

    }
}

```

### 8. 유용한 메서드들

1. Comparator

   ```java
   inventory.sort(comparing(Apple::getWeight))
       .reversed() // 무게를 내림차순으로 정렬
       .thenComparing(Apple::getCountry)); // 두 사과의 무게가 같으면 국가별로 정렬
   ```

2. Predicate : 복잡한 predicate를 만들 수 있도록 `negate`, `and`, `or` 세 가지 메소드를 제공

   ```java
   // redApple의 결과를 반전시킨 객체
   Predicate<Apple> notRedApple = redApple.negate();
   
   // 두 프레디케이트를 연결해서 새로운 객체를 만듦
   Predicate<Apple> redAndHeavyApple = redApple.and(apple->apple.getWeight()>150);
   
   // 프레디케이트 메서드를 연결해서 더 복잡한 프레디케이트 객체를 만듦
   Predicate<Apple> redAndHeavyAppleOrGreen =
       redApple.and(apple->apple.getWeight()>150)
       .or(apple->GREEN.equals(a.getColor()));
   ```

3. Function : `andThen`, `compose` 메소드 제공