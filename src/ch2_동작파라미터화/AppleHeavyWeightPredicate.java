package ch2_동작파라미터화;

public class AppleHeavyWeightPredicate implements ApplePredicate{
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}
