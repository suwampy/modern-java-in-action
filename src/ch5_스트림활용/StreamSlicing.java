package ch5_스트림활용;

import ch4_스트림.Dish;

import java.util.Arrays;
import java.util.List;

import static ch4_스트림.Dish.menu;
import static java.util.stream.Collectors.toList;

public class StreamSlicing {
    public static void main(String[] args) {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("sesonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER)
        );

        // 320 칼로리 이하의 요리 선택 => filter를 사용해보자
        List<Dish> filteredMenu
                = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
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

        // 스트림 축소
        List<Dish> dishes = specialMenu.stream()
                .filter(dish-> dish.getCalories() > 300)
                .limit(3)
                .collect(toList());
    }

    void quiz_5_1() {
        // 스트림을 이용해서 처음 등장하는 두 고기 요리를 필터링하세요
        List<Dish> dishes = menu.stream()
                .filter(d-> d.getType() == Dish.Type.MEAT) // 고기로 필터링
                .limit(2) // 두 고기
                .collect(toList());
    }
}
