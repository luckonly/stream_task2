package streams_task2;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static final int MAX_RECRUIT_AGE = 27;
    static final int MIN_RECRUIT_AGE = 18;
    static final int MAX_WORK_WOMAN_AGE = 60;
    static final int MAX_WORK_MAN_AGE = 65;

    public static void main(String[] args) {

        Collection<Person> persons = getPersons();
        getMinors(persons);
        System.out.println("-----------");
        getRecruits(persons);
        System.out.println("-----------");
        getEmployers(persons);

    }

    private static void getEmployers(Collection<Person> persons) {

        Comparator<Person> personComparator = Comparator.comparing(Person::getFamily);
        System.out.println("Список трудоспособных лиц: ");
        persons.stream()
                .filter(x -> x.getEducation().equals(Education.HIGHER) && x.getAge() > MIN_RECRUIT_AGE)
                .filter(x -> {
                    if (x.getSex().equals(Sex.WOMAN) && x.getAge() < MAX_WORK_WOMAN_AGE) return true;
                    if (x.getSex().equals(Sex.MAN) && x.getAge() < MAX_WORK_MAN_AGE) return true;
                    return false;
                })
                .sorted(personComparator)
                .map(x -> x.getFamily())
                .forEach(System.out::println);
    }

    private static void getMinors(Collection<Person> persons) {
       long minorsCount = persons.stream().filter(x -> x.getAge() < MIN_RECRUIT_AGE).count();
       System.out.println("Количество несовершеннолетних: " + minorsCount);
    }

    private static void getRecruits(Collection<Person> persons) {
        long under18 = persons.stream().filter(x -> x.getAge() > 18).count();
        System.out.println("В списке " + under18 + " лиц призывного возраста.");
        System.out.println("--------------------");

        List<String> recruitListFamilies = persons.stream()
                .filter(x -> x.getSex() == Sex.MAN)
                .filter(x -> x.getAge() >= MIN_RECRUIT_AGE && x.getAge() <= MAX_RECRUIT_AGE)
                .map(x -> x.getFamily()).collect(Collectors.toList());
    }

    public static Collection<Person> getPersons() {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        return persons;
    }

}
