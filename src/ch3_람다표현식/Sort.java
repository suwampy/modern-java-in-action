package ch3_람다표현식;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

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
