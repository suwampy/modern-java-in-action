package ch2_동작파라미터화;


import java.util.ArrayList;
import java.util.List;

public class Run {
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

        // 람다식 사용
        List<Apple> result = filterApples(l, (Apple apple) -> Color.RED.equals(apple.getColor()));
        System.out.println(result);

        // 리스트 형식으로 추상화
        List<Apple> redApples = filter(l, (Apple apple) -> Color.RED.equals(apple.getColor()));
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(5);
        List<Integer> evenNumbers = filter(numbers, (Integer i)-> i% 2 == 0);

        System.out.println(redApples);
        System.out.println(evenNumbers);
    }


    static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e : list){
            if(p.test(e)) {
                result.add(e);
            }
        }

        return result;
    }
}