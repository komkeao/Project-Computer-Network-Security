import java.util.*;

class Decryption {
	public Decryption() {
	}
	public static String Decrypt(String str) {
		String result;
		if (str.length() == 68) {
			result = str;
			result = shiftInv(result);
			result = permutationInv(result);
			result = toHexInv(result);
			result = fillStrInv(result);
		} else {
			result = "error";
		}
		return result;
	}

	private static String fillStrInv(String str) {
		String res = str.trim();
		return res;
	}

	private static String toHexInv(String input) {
		String res = "";
		String tmp;
		for (int i = 0; i < input.length(); i = i + 2) {
			tmp = input.substring(i, i + 2);
			char t = (char) Integer.parseInt(tmp, 16);
			res += t;
		}
		return res;
	}

	private static String shiftInv(String str) {
		String res = "";
		String salt1 = str.substring(64, 65);
		String salt2 = str.substring(66, 67);
		int salt3 = Integer.parseInt(salt1, 16);
		int salt4 = Integer.parseInt(salt2, 16);
		int salt = salt3 + salt4;
		char metrix[][] = new char[8][8];
		int k = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String tmp = "";
				tmp += str.charAt(k);
				int t = Integer.parseInt(tmp, 16);
				if (k % 2 == 1) {
					t -= (j + i + 17 + salt);
				} else {
					t -= (j + i + 23 + salt);
				}
				while (t < 0) {
					t += 16;
				}
				metrix[i][j] = Integer.toHexString(t).charAt(0);
				res += metrix[i][j];
				k++;
			}
		}
		return res;
	}

	private static String permutationInv(String str) {
		String res = "";
		// convert Hex to array 8*8
		char metrix[][] = new char[8][8];
		int k = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				metrix[i][j] = str.charAt(k);
				k++;
			}
		}
		// transpose
		// k = 1;
		char tmp[][] = new char[8][8];
		k = 0;
		// permutation
		int l[] = { 4, 0, 5, 1, 6, 2, 7, 3 };
		char tmp2[][] = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tmp[i][j] += metrix[l[i]][j];
			}
			k++;
		}
		// transpote
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tmp2[i][j] += tmp[j][i];
			}
		}
		// to string
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				res += tmp2[i][j];
			}
		}
		return res;
	}

}

public class Decrypt {
	public static void main(String[] args) {
		Scanner k = new Scanner(System.in);
		String input;
		System.out.println("============Welcome To Decryptor============");
		System.out.println("Please Input Your String");
		System.out.println("Length Of String Is 68 Charactor");
		do {
			System.out.print("Input Text:");
			input = k.nextLine();
			if (input.length() != 68) {
				System.out.println("Try Again!!!");
			}
		} while (input.length() != 68);
		String res = Decryption.Decrypt(input);
		System.out.println("Decrypted Text:" + res);
	}

}
