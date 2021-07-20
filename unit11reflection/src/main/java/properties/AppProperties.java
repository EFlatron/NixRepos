package properties;

import annotation.PropertyKey;

public class AppProperties {
    @PropertyKey(value = "person.firstName")
    public String firstName;
    @PropertyKey(value = "person.lastName")
    public String lastName;
    @PropertyKey(value = "person.age")
    public int age;
    @PropertyKey(value = "person.gender")
    public Gender gender;
    @PropertyKey(value = "person.yearOfBirth")
    public int yearOfBirth;
    @PropertyKey(value = "isPerson")
    public boolean isPerson;

    public enum Gender{
        MALE, FEMALE;
    }

    @Override
    public String toString() {
        return "AppProperties{ " +
                "FirstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", Age=" + age +
                ", Gender=" + gender +
                ", YearOfBirth=" + yearOfBirth +
                ", IsPerson=" + isPerson +
                '}';
    }
}
