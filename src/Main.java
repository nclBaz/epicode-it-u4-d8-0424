import com.github.javafaker.Faker;
import entities.User;
import functional_interfaces.StringModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
	public static void main(String[] args) {
		StringModifier dotsWrapper = str -> "..." + str + "...";
		StringModifier starsWrapper = str -> "*" + str + "*";
		// Essendo che l'interfaccia funzionale StringModifier ha uno ed un solo metodo
		// queste 2 lambda stanno implementando proprio quel metodo (il metodo modify())

		String dots = dotsWrapper.modify("CIAO");
		System.out.println(dots);
		System.out.println(starsWrapper.modify("CIAO"));

		StringModifier stringInverter = str -> {
			String[] splitted = str.split("");

			String inverted = "";
			for (int i = splitted.length - 1; i >= 0; i--) {
				inverted += splitted[i];
			}
			return inverted;
		};

		System.out.println(stringInverter.modify("CIAO"));

		// ---------------------------------------- PREDICATES --------------------------------
		Predicate<Integer> isMoreThanZero = num -> num > 0;
		Predicate<Integer> isLessThanHundred = num -> num < 100;
		Predicate<User> isUserAgeMoreThan18 = user -> user.getAge() > 18;

		User aldo = new User("Aldo", "Baglio", 20);
		System.out.println(isMoreThanZero.test(aldo.getAge()));
		System.out.println(isMoreThanZero.and(isLessThanHundred).test(aldo.getAge()));
		System.out.println(isMoreThanZero.negate().test(-20));
		System.out.println(isUserAgeMoreThan18.test(aldo));

		// ----------------------------------------- SUPPLIER ----------------------------------
		Supplier<Integer> randomNumberSupplier = () -> {
			Random rndm = new Random();
			return rndm.nextInt(1, 10000);
		};

		List<Integer> randomNumbers = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			randomNumbers.add(randomNumberSupplier.get());
		}

		System.out.println(randomNumbers);


		Supplier<User> userSupplier = () -> {
			Faker f = new Faker(Locale.ITALY);
			return new User(f.lordOfTheRings().character(), f.name().lastName(), randomNumberSupplier.get());
		};


		Supplier<List<User>> randomListSupplier = () -> {
			List<User> users = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				users.add(userSupplier.get());
			}
			return users;
		};

		List<User> randomUsers = randomListSupplier.get();

		randomUsers.forEach(user -> System.out.println(user));
		// randomUsers.forEach(System.out::println); <-- alternativa proposta da IntelliJ per rendere ulteriormente compatto il codice

	}
}