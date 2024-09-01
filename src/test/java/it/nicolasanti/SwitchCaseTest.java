package it.nicolasanti;
import org.junit.jupiter.api.Test;

public class SwitchCaseTest {
    String jdk19(Object o) {
        return  switch (o) {
            case Integer i when i > 10 -> String.format("a large Integer %d", i);
            case Integer i -> String.format("a small Integer %d", i);
            case Long l -> String.format("a Long %d", l);
            default -> o.toString();
        };
    }

    @Test
    void main() {
        System.out.println(jdk19(10));
        System.out.println(jdk19(5l));
        System.out.println(jdk19("Try me!"));
    }
}
