package Lesson03;

import java.util.ArrayList;
import java.util.List;


public class PhoneBook {
    private static List<Person> personSet = new ArrayList<Person>();

    public static void add(Person person){
        personSet.add(person);
    }

    public static void get(String name){
        for (Person person: personSet) {
            if (person.getName().equals(name)){
                System.out.println(person.toString());
            }
        }
    }
}
