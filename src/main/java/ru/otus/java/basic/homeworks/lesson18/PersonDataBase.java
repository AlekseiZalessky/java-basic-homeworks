package ru.otus.java.basic.homeworks.lesson18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDataBase {
    private List<Person> people;
    private Map<Long, Person> persons;

    public PersonDataBase() {
        this.people = new ArrayList<>();
        this.persons = new HashMap<>();
    }

    public Person findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id!");
        }
        return persons.get(id);
    }

    public void add(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Invalid person!");
        }
        people.add(person);
        persons.put(person.getId(), person);
    }

    public boolean isManager(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Invalid person!");
        }
        Position position = person.getPosition();
        if (position == null) {
            return false;
        }
        return position.equals(Position.MANAGER)
                || position.equals(Position.DIRECTOR)
                || position.equals(Position.BRANCH_DIRECTOR)
                || position.equals(Position.SENIOR_MANAGER);
    }

    public boolean isEmployee(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id!");
        }
        Person person = persons.get(id);
        if (person == null) {
            return false;
        }
        Position position = person.getPosition();
        if (position == null) {
            return false;
        }

        return !isManager(person);
    }
}
