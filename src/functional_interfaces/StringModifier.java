package functional_interfaces;

@FunctionalInterface
public interface StringModifier {
	public String modify(String str);

	// public void blabla(); <-- Un'interfaccia si dice funzionale se possiede uno ed un solo metodo astratto
}
