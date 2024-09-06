package it.nicolasanti;

import org.junit.jupiter.api.Test;

import javax.swing.text.Position;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.toMap;


// JDK 22
// https://openjdk.org/projects/jdk/22/
// https://openjdk.org/jeps/456
public class UnnamedVariablesAndPatternsTest {
    static<T> void println(T arg, Object... args) {
        System.out.printf(String.valueOf(arg) + "%n", args);
    }

    public class CloseableClass implements AutoCloseable {
        @Override public void close()  {
         println("close method called");
        }
    }

    void tryWithResourceSideEffect() {
        try (var _ = new CloseableClass()) {
            println("closeableClass instance is never used but still automatically closed ");
        }
    }

    @Test void tryWithResourceSideEffectTest() {
        tryWithResourceSideEffect();
    }



    void exceptionSideEffect(String s) {
        try {
            float pi = Float.parseFloat(s);
        } catch (NumberFormatException _) { // variable not used
            println("Bad number: %s",s);
        }
    }

    @Test void exceptionSideEffect() {
        exceptionSideEffect("3.14");
        exceptionSideEffect("three point fourteen");
    }

    enum Color { RED, GREEN, BLUE }
    record Point(int x, int y) { }
    record ColoredPoint(Point p, Color c) {}

    void decustruction(Object object) {
        if (object instanceof ColoredPoint(Point(int x, int y),  _)) {
            //Color in never used
            println("Coordinates x: %d, y: %d",x, y);
        }
    }

    @Test void decustructionTest() {
        decustruction(new ColoredPoint(new Point(1,2), Color.RED));
    }


    void otherUnusedVariable1() {
        var list = List.of("XC234", "XC254", "XC764");
        var map = list.stream().collect(toMap(k -> k.substring(2), _ -> "Not Interested"));
        println(map);
    }

    int otherUnusedVariable2() {
        Iterable<String> iter = List.of("XC234", "XC254", "XC764");
        int total = 0;
        for ( var _: iter) { total++; } //items are not used
        return total;
    }


    @Test void otherUnusedVariableTest() {
        otherUnusedVariable1();
        otherUnusedVariable2();
    }

}
