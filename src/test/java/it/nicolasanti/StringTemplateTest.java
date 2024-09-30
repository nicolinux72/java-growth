package it.nicolasanti;

import org.junit.jupiter.api.Test;

import static it.nicolasanti.SomeUtils.println;
import static java.lang.StringTemplate.RAW;

public class StringTemplateTest {

    //@Test
    static void basicTest() {
        int i = 1;
        StringTemplate stringTemplate = RAW."i = \{i}";
        println(stringTemplate.interpolate());
        i= 745;
        println(stringTemplate.interpolate());

        StringTemplate.Processor<String, RuntimeException> STR = StringTemplate::interpolate;
    }



}
