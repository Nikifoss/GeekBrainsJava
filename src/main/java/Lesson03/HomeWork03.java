package Lesson03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class HomeWork03 {

    public static void main(String[] args) {

        //Задание 1
       searchForUniqueWords(createArrayList()); //Поиск уникальных слов
       howManyTimesDoesEachWordOccur(createArrayList());//Подсчет сколько раз встречается каждое слово

        //Задание 2
        PhoneBook.add(new Person("Oleg","001"));
        PhoneBook.add(new Person("Oleg","002"));
        PhoneBook.add(new Person("Vasiliy","003"));
        PhoneBook.add(new Person("Sergey","004"));
        PhoneBook.add(new Person("Nikita","005"));

        PhoneBook.get("Oleg");

    }

    private static ArrayList<String> createArrayList(){
        ArrayList<String> listWords = new ArrayList<>();
        listWords.add("Cat");
        listWords.add("Dog");
        listWords.add("Cow");
        listWords.add("Cat");
        listWords.add("Rat");
        return listWords;
    }

    private static void searchForUniqueWords(ArrayList<String> arrayList){
        HashSet<String> hashSet = new HashSet<>(arrayList);
        for (String s: hashSet) {
            System.out.println(s);
        }
    }

    private static void howManyTimesDoesEachWordOccur(ArrayList<String> arrayList){
        Map<String, Integer> hm = new HashMap<>();
        for (String s: arrayList) {
            if (hm.containsKey(s)){
                hm.put(s, hm.get(s) + 1);
            } else hm.put(s,1);
        }
        System.out.println(hm);
    }
}
