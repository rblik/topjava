package ru.javawebinar.topjava.tags;

public class Functions {
    public static String leetTalk(String s){
        return s.replace("f", "ph")
                .replace("e", "4")
                .replace("a", "4")
                .replace("s", "z");
    }
}
