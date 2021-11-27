package ch5_스트림활용;

import ch4_스트림.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ch4_스트림.Dish.menu;

public class Filtering {
    public static void main(String[] args) {
        List<Dish> vegetarianDishes = new ArrayList<>();

        // 외부 반복 코드
        for(Dish d: menu) {
            if(d.isVegetarian()) {
                vegetarianDishes.add(d);
            }
        }

        // 스트림 API를 이용해서 내부적으로 처리
        // 프레디케이트로 필터링
        // 프레디 케이트 : 불리언을 반환하는 함수
        // filter 메서드는 프레디케이트를 인수로 받아서
        // 프리데케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.
        vegetarianDishes = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        // 고유 요소 필터링
        // 리스트의 모든 짝수를 선택하고 중복을 필터링
        List<Integer> numbers = Arrays.asList(1, 2, 3, 1, 3, 3, 2);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct() // 중복 제거
                .forEach(System.out::println);
    }
}
