package ch3_람다표현식;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {
    public static void main(String[] args) throws IOException {
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        // String oneLine = processFile(BufferedReader::readLine); 로도 쓸수잇음 ㅎㅎ
    }

    // 람다 적용 전
    public static String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) { // 초기화, 준비코드 + 정리,마무리 코드
            return br.readLine(); // 실제 필요한 작업을 하는 행
        }
    }

    /**
    * 2단계 : 함수형 인터페이스를 이용해서 동작 전달
     * */
    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    /**
     * 3단계 : 동작 실행
     * */
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br); // BufferedReader 객체 처리
        }
    }






}
