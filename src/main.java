import java.util.*;

class Encryptor {
	public Encryptor() {
	}

	public static String Encrypt(String str) {
		String result;
		if (str.length() <= 32) {
			result = str;
			result = fillStr(result);
			result = toHex(result);
			result = permutation(result);
			result = saltGenerater(result);
			result = shift(result);
		} else {
			result = "error";
		}
		return result;
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

	private static String permutation(String str) {
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
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tmp[i][j] += metrix[j][i];
			}
		}
		// permutation
		char tmp2[][] = new char[8][8];
		k = 0;
		for (int i = 0; i < 8; i++) {
			if (i % 2 == 1) {
				for (int j = 0; j < 8; j++) {
					tmp2[k][j] += tmp[i][j];
				}
				k++;
			}
		}
		for (int i = 0; i < 8; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < 8; j++) {
					tmp2[k][j] += tmp[i][j];
				}
				k++;
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

	private static String fillStr(String inputStr) {
		String tmp = inputStr;
		String filled = tmp;
		for (int i = 0; i < 32 - tmp.length(); i++) {
			filled += " ";
		}
		return filled;
	}

	private static String toHex(String input) {
		String res = "";
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) < 16) {
				// กรณี เลขฐาน 16 หลักเดียว เดิม 0 ข้างหน้า
				res += "0";
				res += Integer.toHexString(input.charAt(i));
			} else {
				res += Integer.toHexString(input.charAt(i));
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

	private static String shift(String str) {
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
					t = j + i + 17 + salt + t;
				} else {
					t = j + i + 23 + salt + t;
				}
				t = t % 16;
				metrix[i][j] = Integer.toHexString(t).charAt(0);
				res += metrix[i][j];
				k++;
			}
		}
		res += str.substring(64, 68);
		return res;
	}

	private static String saltGenerater(String res) {
		// random
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < 2) {
			sb.append(Integer.toHexString(r.nextInt()));
		}
		String ran = sb.toString().substring(0, 2);
		String ar = "d7";
		for (int i = 0; i < res.length() - 1; i = i + 2) {
			int a = Integer.parseInt(ar, 16);
			ar = "";
			ar += res.charAt(i);
			ar += res.charAt(i + 1);
			int b = Integer.parseInt(ar, 16);
			a = a + b;
			ar = "";
			String tmp = "0";
			tmp += Integer.toHexString(a);
			ar += tmp.charAt(tmp.length() - 2);
			ar += tmp.charAt(tmp.length() - 1);
		}
		String total = res + ar.charAt(0) + ran.charAt(0) + ar.charAt(1) + ran.charAt(1);
		return total;
	}
}

public class main {
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		String input;
		System.out.println("============Welcome To Encryptor============");
		System.out.println("Please Input Your String");
		System.out.println("Length Of String Is Between 1-32 Charactor");
		do {
			System.out.print("Input Text:");
			input = kb.nextLine();
			if (input.length() > 32) {
				System.out.println("Try Again!!!");
			}
		} while (input.length() > 32);
		String res = Encryptor.Encrypt(input);
		System.out.println("Encrypted Text:" + res);
		res = Encryptor.Decrypt(res);
		System.out.println("Decrypted Text:" + res);
	}
}
