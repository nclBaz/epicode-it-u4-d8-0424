import com.github.javafaker.Faker;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
	/*	StringModifier dotsWrapper = str -> "..." + str + "...";
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

		randomUsers.forEach(user -> System.out.println(user));*/
		// randomUsers.forEach(System.out::println); <-- alternativa proposta da IntelliJ per rendere ulteriormente compatto il codice

		// ---------------------------------------------- STREAMS - FILTER -------------------------------------------------------------

		Supplier<Integer> randomNumberSupplier = () -> {
			Random rndm = new Random();
			return rndm.nextInt(1, 100);
		};

		List<Integer> randomNumbers = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			randomNumbers.add(randomNumberSupplier.get());
		}

		System.out.println(randomNumbers);

	/*	Stream<Integer> stream = randomNumbers.stream().filter(num -> num > 50 && num < 1000);
		stream.forEach(num -> System.out.println(num));*/

		// Gli Stream vanno sempre "aperti" utilizzando .stream(), poi posso fare tutte le operazioni INTERMEDIE che voglio, tipo .filter()
		// alla fine però per ottenere un risultato dovrò concludere lo Stream. Concludere lo Stream si può fare in più maniere, ad esempio
		// .forEach può essere utile per concluderlo magari stampando a video i risultati

		// randomNumbers.stream().filter(num -> num > 50 && num < 1000).forEach(System.out::println);

		// Nei Filter posso utilizzare anche dei Predicate definiti in precedenza
		Predicate<Integer> isMoreThanZero = num -> num > 0;
		Predicate<Integer> isLessThanHundred = num -> num < 100;
		Predicate<User> isUserAgeMoreThan18 = user -> user.getAge() > 18;
		randomNumbers.stream().filter(isMoreThanZero.and(isLessThanHundred)).forEach(System.out::println);

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

		System.out.println(randomUsers);

		randomUsers.stream().filter(isUserAgeMoreThan18.negate()).forEach(System.out::println);

		System.out.println("---------------------------------------------- STREAMS - MAP -------------------------------------------------------------");

		randomUsers.stream().map(user -> user.getAge()).forEach(user -> System.out.println(user));
		// randomUsers.stream().map(User::getAge).forEach(System.out::println); <-- Equivalente alla riga sopra
		System.out.println("---------------------------------------------- STREAMS - MAP & FILTER -------------------------------------------------------------");
		randomUsers.stream().filter(user -> user.getAge() < 18).map(user -> user.getName() + " - " + user.getAge()).forEach(System.out::println);

		// ************************************************ COME TERMINARE GLI STREAM ************************************************************************
		// Oltre al .forEach posso terminare gli Stream anche in maniere più utili, come ad esempio:
		// - reduce, quando voglio 'ridurre' una collezione dati ad un singolo valore, magari effettuando un qualche calcolo
		// - collect oppure toList, per ricondurre lo Stream ad una nuova lista
		// - matching, tramite allMatch e anyMatch posso testare le collezione su una certa condizione

		System.out.println("---------------------------------------------- STREAMS - REDUCE -------------------------------------------------------------");
		int totale = randomUsers.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.reduce(0, (partialSum, currentAge) -> partialSum + currentAge);

		System.out.println("Il totale è:" + totale);

		System.out.println("---------------------------------------------- COME OTTENERE UNA LISTA DA UNO STREAM -------------------------------------------------------------");
		List<Integer> listaEtàMinorenni = randomUsers.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.collect(Collectors.toList());

		System.out.println(listaEtàMinorenni);

		List<User> minorenni = randomUsers.stream()
				.filter(user -> user.getAge() < 18)
				.toList(); // .toList() è un'alternativa equivalente al collect però più compatta
		System.out.println(minorenni);

		List<String> nomiMinorenni = randomUsers.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getName() + " " + user.getSurname())
				.toList();
		System.out.println(nomiMinorenni);

		System.out.println("---------------------------------------------- STREAMS - ALLMATCH & ANYMATCH -------------------------------------------------------------");
		// .some() e .every() di JS corrispondono a .anyMatch() e .allMatch() di Java
		// Questi metodi ci consentono di controllare se ALMENO UN ELEMENTO (.some e .anyMatch) della lista passa una certa condizione, o se TUTTI GLI ELEMENTI (.every, .allMatch)
		// passano una certa condizione

		if (randomUsers.stream().allMatch(user -> user.getAge() >= 18)) System.out.println("tutti gli utenti sono maggiorenni");
		else System.out.println("C'è qualche minorenne");

		if (randomUsers.stream().anyMatch(user -> user.getAge() >= 99)) System.out.println("Esiste qualche utente con più di 98 anni");
		else System.out.println("Sono tutti giovani");

		// -------------------------------------------------------------- DATE ----------------------------------------------------------------------------
		System.out.println("-------------------------------------------------------------- DATE ----------------------------------------------------------------------------");
		LocalDate today = LocalDate.now();
		System.out.println(today);

		System.out.println("La data di domani è: " + today.plusDays(1));
		System.out.println("La data di ieri era: " + today.minusDays(1));
		System.out.println("Il giorno di oggi prossimo anno: " + today.plusYears(1));

		LocalDate yesterday = today.minusDays(1);
		System.out.println(yesterday.isBefore(today));

		LocalDateTime orario = LocalDateTime.now();
		System.out.println(orario);

		LocalDate date = LocalDate.parse("2024-02-02");
		LocalDate date2 = LocalDate.of(2024, 2, 13);
		System.out.println(date);
		System.out.println(date2);

	}
}