import csv.CsvMapper;
import csv.CsvParser;
import csv.CsvTable;
import entity.Person;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvParser parser = new CsvParser("unit12csvmapper/input/persons.csv");
        CsvTable csvTable = new CsvTable(parser);
        CsvMapper csvMapper = new CsvMapper();

        List<Person> persons = csvMapper.mapper(Person.class, csvTable);
        System.out.println(persons);
    }
}
