## 동작 파라미터화 코드 전달하기

**동작파라미터화** 를 이용하면 자주 바뀌는 요구사항에 효과적으로 대응할 수 있다.

동작 파라미터화란 아직은 어떻게 실행할 것인지 결정하지 않은 코드블록을 의미한다.



### 1. 변화하는 요구사항에 대응하기

기존의 농장재고목록 애플리케이션에 리스트에서 녹색 사과만 필터링하는 기능을 추가한다

1. **녹색 사과 필터링**

   `enum Color {RED, GREEN}`

   ```java
   public static List<Apple> filterGreenApples(List<Apple> inventory) {
       List<Apple> result = ne ArrayList<>(); // 사과 누적 리스트
       for (Apple apple : inventory) {
           if (GREEN.equlas(apple.getColor())) { // 녹색 사과만 선택
               result.add(apple);
           }
       }
       return result;
   }
   ```

   그런데 빨간 사과도 필터링 하고싶어짐 ➡ 새로운 메서드를 만든다?

   좀 더 다양한 색으로 필터링 하고싶어짐 ➡ 계속 새로운 메서드를 만들어야 함... 비효율적

   > **거의 비슷한 코드가 반복 존재한다면 그 코드를 추상화하자**

2. **색을 파라미터화**

   ```java
   public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
       List<Apple> result = new ArrayList<>();
       for (Apple apple: inventory) {
           if (apple.getColor().equals(color)) {
               result.add(apple);
           }
       }
   }
   ```

   요구사항의 변화에 따라 무게 정보 파라미터도 추가함

   ```java
   public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
       List<Apple> result = new ArrayList<>();
       for (Apple apple: inventory) {
           if (apple.getWeight() > weight) {
               result.add(apple);
           }
       }
       return result;
   }
   ```

   => 색 필터링 코드와 대부분 중복된다.. 이는 DLY (don't repeat yourself) 원칙을 어기는 것^_^

3. 가능한 모든 속성으로 필터링

   ```java
   public static List<Apple> filterApples(List<Apple> inventory, Color color, 
                                          int wieght, boolean flag) {
       List<Apple> result = new ArrayList<>();
       for (Apple apple: inventory) {
           if ((flag && apple.getColor().equlas(color)
               (!flag && apple.getWeight() > weight) {
               result.add(apple);
           }
       }
       return result;
   }
   ```

   뭐라는겅미? ㅠㅠ ➡ **동작 파라미터화**를 이용하자 ^_^

### 2. 동작 파라미터화

선택 조건을 결정하는 인터페이스

```java
public interface ApplePredicate {
    boolean test (Apple apple);
}
```



다양한 선택 조건을 대표하는 여러 버전의  ApplePredicate를 정의할 수 있다

```java
// 무거운 사과만 선택
public class AppleHeavyWeightPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}

// 녹색 사과만 선택
public class AppleGreenColorPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return GREEN.equals(apple.getColor());
    }
}
```

1. 추상적 조건으로 필터링

   ```java
   public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
       List<Apple> result = new ArrayList<>();
       for(Apple apple : inventory) {
           if (p.test(apple)) {
               result.add(apple);
           }
       }
       return result;
   }
   ```

   ```java
   public static void main(String[] args) {
       List<Apple> l = new ArrayList<>();
       l.add(new Apple(10, Color.GREEN));
       l.add(new Apple(160, Color.GREEN));
       l.add(new Apple(10, Color.RED));
       l.add(new Apple(160, Color.RED));
   
       // 색깔 [Apple{weight=10, color=GREEN}, Apple{weight=160, color=GREEN}]
       System.out.println(filterApples(l,new AppleGreenColorPredicate()));
   
       // 무게 [Apple{weight=160, color=GREEN}, Apple{weight=160, color=RED}] 
       System.out.println(filterApples(l,new AppleHeavyWeightPredicate()));
   
   }
   ```

### 3.  복잡한 과정 간소화

1. 익명 클래스

   익명 클래스는 말 그대로 이름이 없는 클래스다

   익명 클래스를 이용하면 클래스 선언과 인스턴스호ㅑㅏ를 동시에 할 수 있다

   ```java
   List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
      public boolean test(Apple apple) {
          return RED.equals(apple.getColor);
      }
   });
   ```

2. 람다 표현식 사용

   ```java
   List<Apple> result = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor));
   ```

3. 리스트 형식으로 추상화

   ```java
   public interface Predicate <T>{
       boolean test(T t);
   }
   ```

   ```java
   public class Run {
   	public static void main(String[] args) {
           // 리스트 형식으로 추상화
           List<Apple> redApples = filter(l, (Apple apple) -> Color.RED.equals(apple.getColor()));
           List<Integer> numbers = new ArrayList<>();
           numbers.add(10);
           numbers.add(5);
           List<Integer> evenNumbers = filter(numbers, (Integer i)-> i% 2 == 0);
   
           System.out.println(redApples); // [Apple{weight=10, color=RED}, Apple{weight=160, color=RED}]
           System.out.println(evenNumbers); // [10]
       }
       
       
   	static <T> List<T> filter(List<T> list, Predicate<T>p) {
           List<T> result = new ArrayList<>();
           for(T e : list){
               if(p.test(e)) {
                   result.add(e);
               }
           }
   
           return result;
       }
   }
   ```

   

