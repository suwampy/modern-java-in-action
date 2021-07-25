package ch3_람다표현식;

public class Run {
    public static void main(String[] args) {
        // 람다 사용
        Runnable r1 =()-> System.out.println("Hello Wrold 1");

        // 익명 클래스 사용
        Runnable r2 = new Runnable() {
            public void run() {
                System.out.println("Hellow world 2");
            }
        };

        process(r1); // Hello World 1 출력
        process(r2); // Hello World 2 출력
        process(()-> System.out.println("Hello world 3")); // 직접 전달된 람다식으로 Hello World 3 출력

    }

    static void process(Runnable r) {
        r.run();
    }
}

