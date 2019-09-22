package de.rola.kata.family;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class Family {

    private final Map<String, Person> members = new HashMap<>();

    public boolean male(String name) {
        final Person person = getPerson(name);
        if (!person.setGender(Gender.MALE)) {
            return false;
        }
        return getOtherParents(name)
            .stream()
            .allMatch(parent -> members.get(parent).setGender(Gender.FEMALE));
    }

    public boolean isMale(String name) {
        final Person person = getPerson(name);
        return person.getGender() == Gender.MALE;
    }

    public boolean female(String name) {
        final Person person = getPerson(name);
        if (!person.setGender(Gender.FEMALE)) {
            return false;
        }
        return getOtherParents(name)
            .stream()
            .allMatch(parent -> members.get(parent).setGender(Gender.MALE));
    }

    public boolean isFemale(String name) {
        final Person person = getPerson(name);
        return person.getGender() == Gender.FEMALE;
    }

    public boolean setParentOf(String childName, String parentName) {
        if (childName.equals(parentName)) {
            return false;
        }
        final Person child = getPerson(childName);
        final Person parent = getPerson(parentName);
        if (parent.isAncestor(childName)) {
            return false;
        }
        child.setParent(parent);
        return true;
    }

    private Person getPerson(final String childName) {
        final Person child;
        if (!members.containsKey(childName)) {
            child = new Person(childName);
            members.put(childName, child);
        } else {
            child = members.get(childName);
        }
        return child;
    }

    public List<String> getParentsOf(String name) {
        if (members.isEmpty()) return new ArrayList<>();
        final Person child = members.get(name);
        return child.getParents().stream().map(Person::getName).sorted().collect(Collectors.toList());
    }

    public List<String> getChildrenOf(String name) {
        if (members.isEmpty()) return new ArrayList<>();
        return members
            .values()
            .stream()
            .filter(p -> p.getParentsNames().anyMatch(n -> n.equals(name)))
            .map(Person::getName)
            .sorted()
            .collect(Collectors.toList());
    }

    private Set<String> getOtherParents(String name) {
        return getChildrenOf(name)
            .stream()
            .map(child -> members.get(child).getOtherParent(name))
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toSet());
    }

}
