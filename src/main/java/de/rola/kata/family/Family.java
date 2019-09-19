package de.rola.kata.family;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Family {

    private final Map<String, Person> members = new HashMap<>();

    public boolean male(String name) {
        final Person person = getPerson(name);
        return person.setGender(Gender.Male);
    }

    public boolean isMale(String name) {
        final Person person = getPerson(name);
        return person.getGender() == Gender.Male;
    }

    public boolean female(String name) {
        final Person person = getPerson(name);
        return person.setGender(Gender.Female);
    }

    public boolean isFemale(String name) {
        final Person person = getPerson(name);
        return person.getGender() == Gender.Female;
    }

    public boolean setParentOf(String childName, String parentName) {
        if (childName.equals(parentName)) {
            return false;
        }
        final Person child = getPerson(childName);
        final Person parent = getPerson(parentName);
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
        if (members.isEmpty()) return new ArrayList<String>();
        final Person child = members.get(name);
        return child
            .getParents()
            .stream()
            .map(p -> p.getName())
            .sorted()
            .collect(Collectors.toList());
    }

    public List<String> getChildrenOf(String name) {
        if (members.isEmpty()) return new ArrayList<String>();
        return members
            .values()
            .stream()
            .filter(p -> p.getParentsNames().anyMatch(n -> n.equals(name)))
            .map(r -> r.getName())
            .sorted()
            .collect(Collectors.toList());
    }

}
