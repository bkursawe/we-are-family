package de.rola.kata.family;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Person {
    private final String name;
    private final Set<Person> parents = new HashSet<>();
    private Gender gender = Gender.Unknown;

    public Person(final String name) {
        this.name = name;
    }

    public void setParent(final Person parent) {
        if (parents.isEmpty()) parents.add(parent);
    }

    public Set<Person> getParents() {
        return new HashSet<>(parents);
    }

    public String getName() {
        return this.name;
    }

    public Stream<String> getParentsNames() {
        return this.parents.stream().map(p -> p.getName());
    }

    public boolean setGender(final Gender gender) {
        if (!this.gender.isAllowed(gender)) return false;

        this.gender = gender;
        return true;
    }

    public Gender getGender() {
        return this.gender;
    }
}
