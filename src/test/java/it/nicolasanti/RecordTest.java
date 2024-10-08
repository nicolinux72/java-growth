package it.nicolasanti;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Objects;

import static it.nicolasanti.SomeUtils.load;
import static it.nicolasanti.SomeUtils.save;
import static it.nicolasanti.SwitchCaseTest.println;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecordTest {

    public record Box(String owner, int id) {
    }

    /*  Let's inspect the record class generated by compiler

        > javap RecordTest$Box.class
        Compiled from "RecordTest.java"

        public final class it.nicolasanti.RecordTest$Box extends java.lang.Record {
            public it.nicolasanti.RecordTest$Box(java.lang.String, int); <-- Canonical constructor
            public final java.lang.String toString();
            public final int hashCode();
            public final boolean equals(java.lang.Object);
            public java.lang.String owner();                             <-- it's not a JavaBean getter (e.g. getOwner())
            public int id();                                             <-- it's not a JavaBean getter (e.g. getId())
        }

        Please, not also final keywords on class and methods that highlights the tuple nature of records
     */

    // Record could be structured
    public record Depot(List<Box> boxes) {
    }

    @Test
    public void structuredRecordTest() {
        Depot depot = new Depot(List.of(
                new Box("Nicola", 1),
                new Box("Mattia", 2)));
    }


    public record BasketPlayer(String name, Float high) {
        //default constructor
        public BasketPlayer() {
            this("ND", null);
        }

        //custom constructor
        public BasketPlayer(String name) {
            this(name, null);
        }

        //canonical constructor
        /*public BasketPlayer(String name, Float high) {
            if (Objects.isNull(name) || name.isBlank())
                throw new IllegalArgumentException("invalid name");

            this.name = name;
            this.high = high;
        }*/

        //compact canonical constructor (same result as previous)
        public BasketPlayer {
            if (Objects.isNull(name) || name.isBlank())
                throw new IllegalArgumentException("invalid name");
        }


    }

    @Test
    public void constructorsTest() {
        //default constructor
        println(new BasketPlayer()); //BasketPlayer[name=ND, high=null]

        // custom constructor
        println(new BasketPlayer("Mattia")); //BasketPlayer[name=Mattia, high=null]

        //canonical constructor
        println(new BasketPlayer("Nicola", 1.74f)); //BasketPlayer[name=Nicola, high=1.74]

        assertThrows(IllegalArgumentException.class, () -> new BasketPlayer(" "));

    }

    public record Methods(String name) {

        public String lowerCasedName() {
            return name().toLowerCase();
        }

    }

    @Test
    public void methodsTest() {
        Methods methods = new Methods("Albert");
        assertThat(methods.lowerCasedName(), equalTo("albert")); ;

    }

    @Test
    public void persistenceTest() throws Exception {
        save(new AccountRecord("cash"), "accountRecord");
        save(new AccountClass( "cash"), "accountClass");

        load(AccountRecord.class, "accountRecord"); // canonical constructor is use for rehydrate record
        load(AccountClass.class, "accountClass");   //        no constructor is use for rehydrate class
    }
}

record AccountRecord(String title) implements Serializable {
    public AccountRecord {
        // called when object is deserialized
        println("AccountRecord canonical constructor");
    }
}

class AccountClass implements Serializable {
    public AccountClass(String title) {
        //not called when object is deserialized
        println("AccountClass custom constructor");
    }
}

