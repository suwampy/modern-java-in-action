package ch5_스트림활용;

import ch4_스트림.Dish;

import java.util.Optional;

import static ch4_스트림.Dish.menu;

public class SearchAndMatching {
    public static void main(String[] args) {

        // 1. 프레디케이트가 적어도 한 요소와 일치하는지 확인
        // 프레디케이트가 주어진 스트림에서 적어도 한 요소와 일치하는지 확인할 때
        //`anyMatch` 메서드를 이용한다.
        // anyMatch는 불리언을 반환하므로 최종 연산임
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly");
        }

        // 2. 프레디케이트가 모든 요소와 일치하는지 검사
        // 메뉴가 건강식인지 확인할 수 있음
        boolean isHealthy = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000);

        // noneMatch는 allMatch와 반대 연산 수행
        boolean isHealthy2 = menu.stream()
                .noneMatch(d-> d.getCalories() >= 1000);

        // 3. 요소 검색
        // `findAny` 메서드는 현재 스트림에서 임의의 요소를 반환한다.
        // findAny 메서드를 다른 스트림 연산과 연결해서 사용할 수 있다
        Optional<Dish> dish =
                menu.stream()
                        .filter(Dish::isVegetarian)
                        .findAny();

    }
}
