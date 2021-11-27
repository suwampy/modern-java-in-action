package ch4_스트림;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ch4_스트림.Dish.menu;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        List<Dish> list = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("fork", false, 900, Dish.Type.MEAT)
        );

//        Stream<Dish> stream = list.stream();
//        stream.forEach(s-> {
//            String name = s.getName();
//            int calories = s.getCalories();
//            System.out.println(name + "-" + calories);
//        });
        Double avg = list.stream().mapToInt(Dish::getCalories)
                .average()
                .getAsDouble();

        System.out.println("avg : " + avg);



    }

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

    void newCode(){
        List<String> lowCaloricDishesName =
                menu.stream()
                    .filter(d-> d.getCalories() < 400) // 400 칼로리 이하의 요리 선택
                    .sorted(comparing(Dish::getCalories)) // 칼로리로 요리 정렬
                    .map(Dish::getName) // 요리명 추출
                    .collect(toList()); // 모든 요리명을 리스트에 저장

    }
}
