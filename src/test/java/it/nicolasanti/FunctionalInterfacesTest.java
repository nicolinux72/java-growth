package it.nicolasanti;

import org.junit.jupiter.api.Test;

import java.util.List;

import static it.nicolasanti.SomeUtils.println;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FunctionalInterfacesTest {



    @Test
    public void referenceToStaticMethod() {
        var integers = List.of(1, 2, 3);
        // same as:  ...map(i -> String.valueOf(i))...
        var strings = integers.stream().map(String::valueOf).toList();
        assertThat(strings.getFirst(), equalTo("1"));
    }

    @Test
    public void referenceToInstanceMethodOfParticularObject() {
        String template = "."; //<-- the particular object
        var integers = List.of(1, 2, 3);
        var strings = integers.stream().map(template::repeat).toList();
        assertThat(strings.getLast(), equalTo("..."));
    }


    public int alwaysOne() { return 1;}

    @Test
    public void ReferenceToInstanceMethodOfArbitraryObjectOfParticularType() {
        var tests = List.of(this);
        var integers = tests.stream().map(FunctionalInterfacesTest::alwaysOne).toList();
        assertThat(integers.getFirst(), equalTo(1));
    }

    record Box(String label) {}

    interface MyFunctional{
        String foo(Box label);
    }

    @Test
    public void testReferenceToInstance() {
        MyFunctional myFunctional = Box::label;
        MyFunctional f2 = (box) -> "box.label";

        var box1 = new Box("label 1");
        println(myFunctional.foo(box1));

        var box2 = new Box("label 2");
        println(myFunctional.foo(box2));

        var boxes = List.of(box1, box2);
        //boxes.stream().map(myFunctional).toList();
        boxes.stream().map(Box::label).toList();

    }

    @Test
    public void referenceToConstructor() {

        var chars = List.of(new char[] {'a', 'b', 'c'}, new char[] {'d', 'e', 'f'});
        var strings = chars.stream().map(String::new).toList();
        assertThat(strings.getFirst(), equalTo("abc"));
    }
}


