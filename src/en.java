import java.util.Scanner;

public class en {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);

		System.out.print("In put: ");
		String text = kb.nextLine();
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321";
		String lowerCase = upperCase.toLowerCase();
		String rot13 = "", rot = "";
		int tt = 0;

		for (int k = 0; k < text.length(); k++) {
			String c = text.substring(k, k + 1);
			int j = upperCase.indexOf(c);
			if (j >= 0) {
				int i = (j + 3) % upperCase.length();
				rot13 = rot13 + upperCase.substring(i, i + 1);
			} else {
				j = lowerCase.indexOf(c);
				if (j >= 0) {
					int i = (j + 3) % lowerCase.length();
					rot13 = rot13 + lowerCase.substring(i, i + 1);
				} else {

					rot13 = rot13 + c;

				}
			}

		} // for

		// System.out.println(rot13);

		System.out.print(rot13.toLowerCase());
	}

}
