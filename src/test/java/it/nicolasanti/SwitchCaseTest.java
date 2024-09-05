package it.nicolasanti;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SwitchCaseTest {
    static<T> void println(T arg) { System.out.println(arg); }

    String switchLegacyStatement(int error) {
        //legacy
        String errorMessage;
        switch (error) {
            case -1:
                errorMessage = "Message for error -1";
                break;
            case -2:
                errorMessage = "Message for error -2";
                break;
            default:
                errorMessage = "Unknown error";
                break;
        }

        return errorMessage;
    }

    //JDK 14 JEP 361: Switch Expressions

    String switchExpression(int error) {
        return switch (error) {
            case -1  -> { yield "Message for error -1"; }
            case -2  -> { yield "Message for error -2"; }
            default  -> { yield "Unknown error"; }
        };
    }
    @Test
    void switchExpressionTest() {
        assertThat(switchLegacyStatement(-1), equalTo("Message for error -1")); ;
        assertThat(switchExpression(-2), equalTo("Message for error -2"));
    }

    void legacyFallThrough(int n) {
        switch (n) {
            case 1  : println("one");
            case 2  : println("two");
            case 3  : println("three");
            default : println("many");
        }
    }

    void withoutFallThrough(int n) {
        switch (n) {
            case 1  -> println("one");
            case 2  -> println("two");
            case 3  -> println("three");
            default -> println("many");
        }
    }

    @Test
    void fallThroughTest() {
        legacyFallThrough(2);
        /*  output:
            two
            three
            many
         */

        withoutFallThrough(2);
        /*  output:
            two
         */
    }

    void exhaustiveness() {
        enum Seasions {SPRING, SUMMER, FALL, WINTER}

        var msg = switch (Seasions.SUMMER) {
            case SPRING, SUMMER -> "It's summer";
            case FALL -> "It's fall"; //comment out this line to get the "the switch expression does not cover all possible input values" error
            case WINTER -> "It's winter";
        };
    }

    @Test
    void exhaustivenessTest() {
        exhaustiveness();
    }

    String jdk19(Object o) {
        return  switch (o) {
            case Integer i when i > 10 -> String.format("a large Integer %d", i);
            case Integer i -> String.format("a small Integer %d", i);
            case Long l    -> String.format("a Long %d", l);
            default -> o.toString();
        };
    }

    int jdk18(String day) {
        return switch (day) {
            case "MONDAY", "FRIDAY", "SUNDAY" -> 6;
            case "TUESDAY"                    -> 7;
            default      -> { yield day.length(); }
        };
    }

    @Test
    void switchTest() {
        println(jdk19(10));
        println(jdk19(5l));
        println(jdk19("Try me!"));

        println(jdk18("MONDAY"));
    }
}
