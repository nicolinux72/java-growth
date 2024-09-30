package it.nicolasanti;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static it.nicolasanti.SwitchCaseTest.println;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

// JEP 378
public class TextBlocksTest {

    @Test
    void basicBlockTest() {
        var block = """
                row 1""";
        var sameAs = "row 1";
        assertThat(block, equalTo(sameAs));

        block = """
                row 1
                row 2""";
        sameAs = "row 1\nrow 2";
        assertThat(block, equalTo(sameAs));

        block = """
                row 1
                row 2
                """;
        sameAs = "row 1\nrow 2\n";
        assertThat(block, equalTo(sameAs));

    }

    @Test
    void escapeTest() {
        var block = """
                Don't escape "Quotation Mark"
                """;
        var sameAs = "Don't escape \"Quotation Mark\"\n" ;
        assertThat(block, equalTo(sameAs));

        block = """
                Escape only on last "Quotation Mark\"""";
        sameAs = "Escape only on last \"Quotation Mark\"" ;
        assertThat(block, equalTo(sameAs));

        block = """
                row 1 \r
                row 2 \r
                """;
        sameAs = "row 1 \r\nrow 2 \r\n";
        assertThat(block, equalTo(sameAs));

        block = """
            row 1
            """;
        sameAs = "row 1\n" ;
        assertThat(block, equalTo(sameAs));
    }

    @Test
    void trailingSpacesTest() {
        var block = """
                row 1
                 row 2
                  row 3
                """;
        var sameAs = "row 1\n row 2\n  row 3\n";
        assertThat(block, equalTo(sameAs));

        //there are 2 spaces at the end of row 2: "row 2  "
        block = """
                row 1
                row 2  """;
        sameAs = "row 1\nrow 2";
        assertThat(block, equalTo(sameAs));

        //there are 2 spaces at the end of row 2: "row 2  "
        block = """
                row 1
                row 2  \s
                """;
        sameAs = "row 1\nrow 2   \n";
        assertThat(block, equalTo(sameAs));

        block = """
                row 1 \
                same row 1
                """;
        sameAs = "row 1 same row 1\n";
        assertThat(block, equalTo(sameAs));


    }

}
