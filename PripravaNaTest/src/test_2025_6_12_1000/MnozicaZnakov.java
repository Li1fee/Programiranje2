package test_2025_6_12_1000;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MnozicaZnakov extends TreeSet<Character> {
    Set<Character> seen = new HashSet<>();

    public boolean add(Character c) {
        if (!"1234567890".contains(String.valueOf(c)) || seen.contains(c)) {
            return false;
        } else if (c != '0') {
            seen.add(c);
            super.add(c);
        }
        return true;
    }
}
