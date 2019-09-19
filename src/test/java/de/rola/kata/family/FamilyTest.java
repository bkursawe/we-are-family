package de.rola.kata.family;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Bernd Kursawe <bernd.kursawe@praxisit.de>
 * @since 19.09.2019
 */
public class FamilyTest {

    public static class _The_basics {
        @Test
        public void Should_be_possible_to_create_a_family_instance_having_the_expected_methods() {
            String[] methods =
                {"male", "isMale", "female", "isFemale", "setParentOf", "getParentsOf",
                    "getChildrenOf"};
            Family fam = new Family();
            for (String method : methods) {
                boolean found = false;
                Class[] cArg = new Class[method == "setParentOf" ? 2 : 1];
                Arrays.fill(cArg, String.class);
                try {
                    Family.class.getMethod(method, cArg);
                    found = true;
                } catch (NoSuchMethodException | SecurityException e) {
                }
                assertEquals(String.format(
                    "Method %s should be defined with {} arguments of type String.\n",
                    method,
                    cArg.length), true, found);
            }
        }

        @Test
        public void Should_return_true_for_a_valid_parent_relationship() {
            Family fam = new Family();
            assertEquals(true, fam.setParentOf("Susan", "Otilia"));
        }

        @Test
        public void Should_return_true_for_a_valid_father_relationship() {
            Family fam = new Family();
            assertEquals(true, fam.setParentOf("Morgan", "Frank") && fam.male("Frank"));
        }

        @Test
        public void Should_return_true_for_a_valid_mother_relationship() {
            Family fam = new Family();
            assertEquals(true, fam.setParentOf("Megan", "Helen") && fam.female("Helen"));
        }

        @Test
        public void Should_not_accept_that_child_and_parent_have_the_same_name() {
            Family fam = new Family();
            assertEquals(false, fam.setParentOf("John", "John"));
        }

        @Test
        public void Should_echo_back_with_getParentsOf_what_was_affirmed_before() {
            Family fam = new Family();
            fam.setParentOf("Megan", "Helen");
            assertEquals(Arrays.asList("Helen"), fam.getParentsOf("Megan"));
        }

        @Test
        public void Should_echo_back_with_getChildrenOf_what_was_affirmed_before() {
            Family fam = new Family();
            fam.setParentOf("Morgan", "Frank");
            assertEquals(Arrays.asList("Morgan"), fam.getChildrenOf("Frank"));
        }

        @Test
        public void Should_not_include_a_relationship_that_was_rejected_before() {
            Family fam = new Family();
            fam.setParentOf("John", "John");
            assertEquals(Arrays.asList(), fam.getParentsOf("John"));
        }

        @Test
        public void Should_allow_to_add_a_second_parent() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Susan", "Otilia") && fam.setParentOf("Susan", "Jeremy"));
        }

        @Test
        public void Should_list_parents_in_alphabetical_order() {
            Family fam = new Family();
            fam.setParentOf("Susan", "Otilia");
            fam.setParentOf("Susan", "Jeremy");
            assertEquals(Arrays.asList("Jeremy", "Otilia"), fam.getParentsOf("Susan"));
        }

        @Test
        public void Should_allow_to_add_a_second_and_third_child() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Susan", "Jeremy") && fam.setParentOf("Penelope", "Jeremy")
                    && fam.setParentOf("Hank", "Jeremy"));
        }

        @Test
        public void Should_list_children_in_alphabetical_order() {
            Family fam = new Family();
            fam.setParentOf("Susan", "Jeremy");
            fam.setParentOf("Penelope", "Jeremy");
            fam.setParentOf("Hank", "Jeremy");
            assertEquals(Arrays.asList("Hank", "Penelope", "Susan"), fam.getChildrenOf("Jeremy"));
        }

        @Test
        public void Should_not_complain_when_the_same_info_is_provided_twice_but_not_store_it_twice_either() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Megan", "Helen") && fam.setParentOf("Megan", "Helen"));
            assertEquals(Arrays.asList("Helen"), fam.getParentsOf("Megan"));
        }

        @Test
        public void Should_reject_a_gender_assignment_that_does_not_match_with_previous_information() {
            Family fam = new Family();
            assertEquals(true, fam.male("Frank"));
            assertEquals(false, fam.female("Frank"));
        }

        @Test
        public void Should_support_grandparents() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Susan", "Otilia") && fam.setParentOf("Susan", "Jeremy")
                    && fam.setParentOf("Otilia", "Peter"));
            assertEquals(Arrays.asList("Jeremy", "Otilia"), fam.getParentsOf("Susan"));
            assertEquals(Arrays.asList("Peter"), fam.getParentsOf("Otilia"));
        }

        @Test
        public void Should_keep_two_family_instances_separate() {
            Family fam = new Family();
            Family fam2 = new Family();
            assertEquals(true,
                fam.setParentOf("Otilia", "Peter") && fam2.setParentOf("Otilia", "Jeff"));
            assertEquals(Arrays.asList("Jeff"), fam2.getParentsOf("Otilia"));
            assertEquals(Arrays.asList("Peter"), fam.getParentsOf("Otilia"));
        }
    }

    public static class _The_example_in_the_description {
        @Test
        public void Should_confirm_the_introduction_of_the_family_members() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Frank", "Morgan") && fam.setParentOf("Frank", "Dylan") && fam.male(
                    "Dylan") && fam.setParentOf("Joy", "Frank") && fam.male("Frank"));
        }

        @Test
        public void Should_deduce_that_Morgan_is_a_woman() {
            Family fam = new Family();
            fam.setParentOf("Frank", "Morgan");
            fam.setParentOf("Frank", "Dylan");
            fam.male("Dylan");
            assertEquals(false, fam.male("Morgan"));
        }

        @Test
        public void Should_be_able_to_continue_after_a_rejected_assertion() {
            Family fam = new Family();
            fam.setParentOf("Frank", "Morgan");
            fam.setParentOf("Frank", "Dylan");
            fam.male("Dylan");
            fam.male("Morgan");
            assertEquals(true, fam.setParentOf("July", "Morgan"));
        }

        @Test
        public void Should_not_know_Joys_gender() {
            Family fam = new Family();
            fam.setParentOf("Frank", "Morgan");
            fam.setParentOf("Frank", "Dylan");
            fam.male("Dylan");
            fam.setParentOf("Joy", "Frank");
            fam.male("Frank");
            assertEquals(false, fam.isMale("Joy") || fam.isFemale("Joy"));
        }

        @Test
        public void Should_know_Morgans_children() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Frank", "Morgan") && fam.setParentOf("July", "Morgan"));
            assertEquals(Arrays.asList("Frank", "July"), fam.getChildrenOf("Morgan"));
        }

        @Test
        public void Should_know_Morgans_children_after_a_new_child_was_declared() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Frank", "Morgan") && fam.setParentOf("July", "Morgan")
                    && fam.setParentOf("Jennifer", "Morgan"));
            assertEquals(Arrays.asList("Frank", "Jennifer", "July"), fam.getChildrenOf("Morgan"));
        }

        @Test
        public void Should_only_consider_Frank_as_Dylans_child() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Frank", "Dylan") && fam.setParentOf("Frank", "Morgan")
                    && fam.setParentOf("July", "Morgan") && fam.setParentOf("Jennifer", "Morgan"));
            assertEquals(Arrays.asList("Frank"), fam.getChildrenOf("Dylan"));
        }

        @Test
        public void Should_confirm_the_obvious() {
            Family fam = new Family();
            assertEquals(true,
                fam.setParentOf("Frank", "Dylan") && fam.setParentOf("Frank", "Morgan"));
            assertEquals(Arrays.asList("Dylan", "Morgan"), fam.getParentsOf("Frank"));
        }

        @Test
        public void Should_know_someones_parent_cannot_be_their_child() {
            Family fam = new Family();
            assertEquals(true, fam.setParentOf("Frank", "Morgan"));
            assertEquals(false, fam.setParentOf("Morgan", "Frank"));
        }
    }
}

