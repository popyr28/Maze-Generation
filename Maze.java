package maze;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class DisjSets {
	private int[] Ds;

	public DisjSets(int numElements) {
		Ds = new int[numElements];
		for (int i = 0; i < Ds.length; i++)
			Ds[i] = -1;
	}

	public void union(int x, int y) {
		int findx = find(x);
		int findy = find(y);
		if (findx == -1 && findy == -1)
			Ds[x] = y;
		else if (findx != -1 && findy == -1)
			Ds[findx] = y;
		else if (findx == -1 && findy != -1)
			Ds[findx] = findy;
	}

	public int find(int x) {
		if (Ds[x] < 0)
			return x;
		else
			return Ds[x] = find(Ds[x]);
	}

}

public class Maze {
	private static Scanner inp;

	public static void main(String[] args) {
		inp = new Scanner(System.in);
		int p = 1;
		do {
			System.out.println("\n==== Maze Generation Using Disjoint Set ====\n");
			System.out.println("Inputkan jumlah baris dan kolom :");
			System.out.print("1. Baris : ");
			int baris = inp.nextInt();
			System.out.print("2. Kolom : ");
			int kolom = inp.nextInt();
			while (baris < 1 || kolom < 1) {
				System.out.println("Salah yaa, baris dan kolom harus lebih dari nol :))");
				System.out.println("\nInputkan jumlah baris dan kolom:");
				System.out.print("1. Baris : ");
				baris = inp.nextInt();
				System.out.print("2. Kolom : ");
				kolom = inp.nextInt();
			}
			DisjSets ds = new DisjSets(baris * kolom);
			Random randomno = new Random();
			int count = 1, wallCount = 5, cell1 = 0, cell2 = 0, wall = 0;
			boolean[] barArray = new boolean[baris * kolom];
			boolean[] underscoreArray = new boolean[baris * kolom];
			Arrays.fill(barArray, true);
			Arrays.fill(underscoreArray, true);
			while (count < baris * kolom) {
				cell1 = randomno.nextInt(baris * kolom);
				wall = randomno.nextInt(wallCount);
				if (wall == 2) {
					if ((cell1 + 1) % kolom == 0) {
						continue;
					}
					cell2 = cell1 + 1;
				} else if (wall == 1) {
					if (cell1 < kolom) {
						continue;
					}
					cell2 = cell1 - kolom;
				} else if (wall == 0) {
					if (cell1 % kolom == 0) {
						continue;
					}
					cell2 = cell1 - 1;
				} else {
					if (cell1 >= (baris * kolom - kolom)) {
						continue;
					}
					cell2 = cell1 + kolom;
				}

				if (ds.find(cell1) != ds.find(cell2)) {
					ds.union(ds.find(cell1), ds.find(cell2));
					if (wall == 0 || wall == 2) {
						barArray[Math.max(cell1, cell2)] = false;
					} else {
						underscoreArray[Math.min(cell1, cell2)] = false;
					}
					count++;
				}
			}

			for (int i = 0; i < kolom; i++) {
				if (i == 0) {
					System.out.print("   ");
				} else {
					System.out.print("_ ");
				}
			}
			System.out.println();
			for (int i = 0; i < baris * kolom; i++) {
				if (barArray[i] == true && i != 0) {
					System.out.print("|");
				} else {
					System.out.print(" ");
				}
				if (underscoreArray[i] == true && i < (baris * kolom - 1)) {
					System.out.print("_");
				} else {
					System.out.print(" ");
				}
				if (i % kolom == kolom - 1 && i < (baris * kolom - 1)) {
					System.out.println("|");
				}
			}
			System.out.println();
			System.out.print("\nApakah ingin membuat labirin lagi? <1/0> ");
			p = inp.nextInt();
		} while (p != 0);

	}
}
