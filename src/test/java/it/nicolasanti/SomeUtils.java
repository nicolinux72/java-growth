package it.nicolasanti;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public  class SomeUtils {
    static<T> void println(T arg, Object... args) {
        System.out.printf(String.valueOf(arg) + "%n", args);
    }

    public static void save(Object obj, String fileName) throws Exception {
        try (var fileStream   = new FileOutputStream(fileName);
             var objectStream = new ObjectOutputStream(fileStream)) {
            objectStream.writeObject(obj);
        }
    }

    @SuppressWarnings("unchecked")
    public static<T> T load(Class<T> classType, String fileName) throws Exception {
        try (var fileStream = new FileInputStream(fileName);
             var objectIn = new ObjectInputStream(fileStream)) {
            return (T) objectIn.readObject();
        }
    }
}
