package ch5_스트림활용;

import ch4_스트림.Dish;

import java.util.Arrays;
import java.util.List;

import static ch4_스트림.Dish.menu;
import static java.util.stream.Collectors.toList;

public class Mapping {
    public static void main(String[] args) {
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

    }

    void quiz_5_2() {
        // 1. 숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환
        // 예를 들어 [1,2,3,4,5] 가 주어지면 [1,4,9,16, 25] 발급
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        List<Integer> squares = numbers.stream()
                .map(n->n*n)
                .collect(toList());

        // 2. 두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환
        // 예를 들어 두 개의 리스트 [1,2,3]과 [3,4]가 주어지면
        // [(1,3), (1,4), (2,3), (2,4), (3,3), (3,4)]를 반환
        List<Integer> numbers1 = Arrays.asList(1,2,3);
        List<Integer> numbers2 = Arrays.asList(3,4);

        List<int[]> pairs = numbers1.stream()
                .flatMap(i->numbers2.stream()
                .map(j->new int[]{i,j}))
                .collect(toList());
    }
}
