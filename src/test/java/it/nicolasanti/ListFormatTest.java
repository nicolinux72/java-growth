package it.nicolasanti;

import org.junit.jupiter.api.Test;

import java.text.ListFormat;
import java.util.List;
import java.util.Locale;

public class ListFormatTest {
    static<T> void print(T arg, Object... args) {
        System.out.printf(String.valueOf(arg), args);
    }

    @Test
    void localeDependentListPatterns() {
        List<String> seasions = List.of("Spring",  "Summer", "Fall", "Winter");
        List<Locale> locales = List.of(Locale.US, Locale.ITALIAN);

        for (Locale locale : locales) {
            print("\n\n%35s   ", locale);
            for (ListFormat.Style style : ListFormat.Style.values()) { print("%35s | ", style); }
            for (ListFormat.Type type: ListFormat.Type.values()) {
                print("\n%35s | ", type);
                for (ListFormat.Style style : ListFormat.Style.values()) {
                    ListFormat formatter = ListFormat.getInstance(locale, type, style);
                    print("%35s | ", formatter.format(seasions));
                }
            }
        }
    }
}
