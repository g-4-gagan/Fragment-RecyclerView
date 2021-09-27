package com.example.fragmentsrecyclerview;

import android.app.Application;
import android.content.ContentProvider;
import android.os.Build;

import java.util.ArrayList;

public class ApplicationClass extends Application
{
    public static ArrayList<Person> people;

    @Override
    public void onCreate() {
        super.onCreate();

        people = new ArrayList<Person>();
        people.add(new Person("Abhishek Rajput", "9536142548"));
        people.add(new Person("Adarsh shukla", "7007158649"));
        people.add(new Person("Ankit yadav", "5178942365"));
        people.add(new Person("Ayush singh", "9458611326"));

    }
}
