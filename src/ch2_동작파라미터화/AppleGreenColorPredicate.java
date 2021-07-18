package ch2_동작파라미터화;

public class AppleGreenColorPredicate implements ApplePredicate{
    public boolean test(Apple apple) {
        return Color.GREEN.equals(apple.getColor());
    }
}
