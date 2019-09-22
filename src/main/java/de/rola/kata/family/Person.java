package de.rola.kata.family;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class Person {
    private final String name;
    private final Set<Person> parents = new HashSet<>();
    private Gender gender = Gender.UNKNOWN;

    public Person(final String name) {
        this.name = name;
    }

    public void setParent(final Person parent) {
        if (parents.size() > 1) throw new TooMuchParentsException(name);
        parents.add(parent);
    }

    public Set<Person> getParents() {
        return new HashSet<>(parents);
    }

    public String getOtherParent(String name) {
        if (parents.size() != 2) {
            return null;
        }
        return parents
            .stream()
            .map(Person::getName)
            .filter(parentName -> !parentName.equals(name))
            .findFirst()
            .orElse(null);
    }

    public String getName() {
        return this.name;
    }

    public Stream<String> getParentsNames() {
        return this.parents.stream().map(Person::getName);
    }

    public boolean setGender(final Gender gender) {
        if (!this.gender.isAllowed(gender)) return false;

        this.gender = gender;
        return true;
    }

    public Gender getGender() {
        return this.gender;
    }

    public boolean isAncestor(String oldie) {
        return ancestors()
            .stream()
            .map(Person::getName)
            .anyMatch(ancestorName -> ancestorName.equals(oldie));
    }

    private Set<Person> ancestors() {
        if (parents.isEmpty()) return parents;
        final Set<Person> ancestors = new HashSet<>(parents);
        parents.stream().map(Person::ancestors).forEach(ancestors::addAll);
        return ancestors;
    }
}
