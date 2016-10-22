package eatsnake;

import java.util.Timer;
import java.util.*;
import java.io.*;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
//开发者:Rear82

class Thread1 extends Thread {

	private String name;
	public String m;
	public int t;

	public Thread1(String name) {
		this.name = name;
	}
	public int Direction;

	public void run() {
		if (EatSnake.survive == true) {
			m = "d";
			t = 1;
			while (EatSnake.survive = true) {
				Scanner input = new Scanner(System.in);
				m = input.next();

			}
		}
		t = 0;

	}
}

public class EatSnake {

	static Thread1 th = new Thread1("a");
	static boolean survive;
	static int lengthOfSnake = 3;
	static int stepOfGame = 3;
	static int gameBoard[][] = new int[20][20];
	static int chessBoardBack[][][] = new int[1000][20][20];
	static int Direction = 1;
	static int DirectionBack[] = new int[1000];
	static int pathOfSnake[][] = new int[1000][5];
	static int pathOfSnakeBack[][][] = new int[1000][1000][5];

	public static void saveOnFile() throws Exception {
		File file = new File("存档.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter br = new BufferedWriter(new FileWriter(file));

		br.write(Integer.toString(lengthOfSnake));
		br.newLine();
		br.write(Integer.toString(Direction));
		br.newLine();
		br.write(Integer.toString(stepOfGame));
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				br.newLine();
				br.write(Integer.toString(gameBoard[i][j]));
			}
		}
		int j;
		for (j = stepOfGame - lengthOfSnake + 1; j <= stepOfGame; j++) {
			br.newLine();
			br.write(Integer.toString(pathOfSnake[j][1]));
			br.newLine();
			br.write(Integer.toString(pathOfSnake[j][2]));
		}

		br.close();
	}

	public static void readFromFile() throws Exception {
		File file = new File("存档.txt");
		if (!file.exists()) {
			System.out.println("没有发现存档文件！");
			clear(gameBoard);

		} else {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
			int i;
			int j;

			BufferedReader br = new BufferedReader(isr);
			String s, m;
			m = null;
			s = null;
			int[] shuju = new int[10000];

			for (i = 0; i < 1000; i++) {
				shuju[i] = -1;
			}

			i = 0;
			while ((s = br.readLine()) != null) {
				shuju[i] = Integer.parseInt(s);
				i++;
			}

			lengthOfSnake = shuju[0];
			Direction = shuju[1];
			stepOfGame = shuju[2];
			for (i = 0; i < 20; i++) {
				for (j = 0; j < 20; j++) {
					gameBoard[i][j] = shuju[i * 20 + j + 3];
				}
			}

			for (i = 403; i < 9999; i++) {
				if (shuju[i] == -1) {
					break;
				} else {
					pathOfSnake[stepOfGame - lengthOfSnake + 1 + (int) ((i - 403) / 2)][(i - 403) % 2 + 1] = shuju[i];
				}
			}

			br.close();
		}
	}

	public static void gameover() {
		survive = false;

		myprint("*", 20);
		System.out.println("\n");
		myprint(" ", 6, "Game over!");
		System.out.println("\n");
		myprint(" ", 3, "Your score is " + (lengthOfSnake - 3));
		System.out.println("\n");
		myprint("*", 20);
		if (th.t == 1) {
			System.out.println("\n\n是否重新开始?(按y重新开始)【请输入两至三遍】\n");
			Scanner input = new Scanner(System.in);
			try {
				sleep(1);
			} catch (InterruptedException ex) {
				Logger.getLogger(EatSnake.class.getName()).log(Level.SEVERE, null, ex);
			}
			String m = input.next();
			survive = false;
			switch (m) {
				case "Y":
				case "y":

					//System.out.println(gameBoard[19][19]);
					System.out.println("请输入刷新间隔时间【请输入两至三遍】:");
					int t = input.nextInt();
					clear(gameBoard);
					Timer timer = new Timer();
					th.m = "d";

					timer.schedule(new maingame(), 100, t);
					System.out.println("\n");
					System.out.println("\n");
					xmlprint(gameBoard);

					//timer.schedule(new shuru(),1,1)
					System.out.println("\n");
					System.out.println("\n");
					System.out.println("输入任意键来开始游戏！:");

					break;
			}
		}

	}

	public static void gameBack() {
		stepOfGame--;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				gameBoard[i][j] = chessBoardBack[stepOfGame][i][j];
			}

		}

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 5; j++) {
				pathOfSnake[i][j] = pathOfSnakeBack[stepOfGame][i][j];
			}
		}

		Direction = DirectionBack[stepOfGame];
	}

	public static void gameSave() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				chessBoardBack[stepOfGame][i][j] = gameBoard[i][j];
			}
		}

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 5; j++) {
				pathOfSnakeBack[stepOfGame][i][j] = pathOfSnake[i][j];
			}
		}

		DirectionBack[stepOfGame] = Direction;
	}

	public static int ram(int a, int b) {
		int c = (int) (Math.random() * (b - a + 1)) + a;
		return c;
	}

	public static void xmlprint(int[][] arr) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (j == 19) {
					System.out.print("□");
					System.out.print("\n");

				} else {

					if (i == 0 || i == 19 || j == 0) {
						System.out.print("□");
					} else {
						switch (arr[i][j]) {
							case 0:
								System.out.print("　");
								break;
							case 1:
								System.out.print("■");
								break;
							case 2:
								System.out.print("□");
								break;
							case 3:
								System.out.print("★");
						}
					}
				}
			}
		}
	}

	static class shuru extends TimerTask {

		public void run() {

			Scanner input = new Scanner(System.in);
			String m = input.next();
			switch (m) {
				case "d":
					Direction = 1;
					break;
				case "s":
					Direction = 2;
					break;
				case "a":
					Direction = 3;
					break;
				case "w":
					Direction = 4;
					break;
			}
		}
	}

	static class maingame extends TimerTask {

		public void run() {
			if (survive == true) {

				String m = th.m;
				String n = m;
				switch (n) {
					case "b":
						if (stepOfGame > 3) {
							System.out.println("已撤销您的上一步操作。");
							gameBack();
							xmlprint(gameBoard);
						}
						break;

					default:
						switch (m) {
							case "d":
								if (Direction != 3) {
									Direction = 1;
								}
								break;
							case "s":
								if (Direction != 4) {
									Direction = 2;
								}
								break;
							case "a":
								if (Direction != 1) {
									Direction = 3;
								}
								break;
							case "w":
								if (Direction != 2) {
									Direction = 4;
								}
								break;
							case "":
								break;

						}
						stepOfGame++;
						switch (Direction) {
							case 1:
								pathOfSnake[stepOfGame][1] = pathOfSnake[stepOfGame - 1][1];
								pathOfSnake[stepOfGame][2] = pathOfSnake[stepOfGame - 1][2] + 1;
								break;
							case 2:
								pathOfSnake[stepOfGame][1] = pathOfSnake[stepOfGame - 1][1] + 1;
								pathOfSnake[stepOfGame][2] = pathOfSnake[stepOfGame - 1][2];
								break;
							case 3:
								pathOfSnake[stepOfGame][1] = pathOfSnake[stepOfGame - 1][1];
								pathOfSnake[stepOfGame][2] = pathOfSnake[stepOfGame - 1][2] - 1;
								break;
							case 4:
								pathOfSnake[stepOfGame][1] = pathOfSnake[stepOfGame - 1][1] - 1;
								pathOfSnake[stepOfGame][2] = pathOfSnake[stepOfGame - 1][2];
								break;
						}
						//for(int i = stepOfGame;i>stepOfGame-lengthOfSnake;i--)
						//{

						//}
						if (gameBoard[pathOfSnake[stepOfGame][1]][pathOfSnake[stepOfGame][2]] == 1 || gameBoard[pathOfSnake[stepOfGame][1]][pathOfSnake[stepOfGame][2]] == 2) {
							survive = false;
							cancel();
							gameover();

						} else if (gameBoard[pathOfSnake[stepOfGame][1]][pathOfSnake[stepOfGame][2]] == 3) {
							lengthOfSnake++;
							gameBoard[pathOfSnake[stepOfGame][1]][pathOfSnake[stepOfGame][2]] = 1;
							int x = ram(0, 19);
							int y = ram(0, 19);
							while (gameBoard[x][y] != 0) {
								x = ram(0, 19);
								y = ram(0, 19);
							}
							gameBoard[x][y] = 3;
							xmlprint(gameBoard);

						} else {
							gameBoard[pathOfSnake[stepOfGame][1]][pathOfSnake[stepOfGame][2]] = 1;
							gameBoard[pathOfSnake[stepOfGame - lengthOfSnake][1]][pathOfSnake[stepOfGame - lengthOfSnake][2]] = 0;
							xmlprint(gameBoard);
						}

						gameSave();
						 {
							try {
								saveOnFile();
							} catch (Exception ex) {

							}
						}
						break;
				}
			}
		}
	}

	public static void myprint(String c, int a, String b) {
		for (int i = 0; i < a; i++) {
			System.out.print(c);
		}
		System.out.print(b);
	}

	public static void myprint(String c, int a) {
		for (int i = 0; i < a; i++) {
			System.out.print(c);
		}
	}

	public static void clear(int[][] arr) {
		lengthOfSnake = 3;
		stepOfGame = 3;
		Direction = 1;
		survive = true;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (i == 0 || i == 19 || j == 0 || j == 19) {
					arr[i][j] = 2;
				} else {
					arr[i][j] = 0;
				}
			}
		}

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 5; j++) {
				pathOfSnake[i][j] = -1;
			}
		}

		arr[1][1] = 1;
		arr[1][2] = 1;
		arr[1][3] = 1;

		pathOfSnake[1][1] = 1;
		pathOfSnake[1][2] = 1;
		pathOfSnake[2][1] = 1;
		pathOfSnake[2][2] = 2;
		pathOfSnake[3][1] = 1;
		pathOfSnake[3][2] = 3;
		int x = ram(0, 19);
		int y = ram(0, 19);
		while (gameBoard[x][y] != 0) {
			x = ram(0, 19);
			y = ram(0, 19);
		}
		gameBoard[x][y] = 3;
		for (int i = 0; i < 10; i++) {
			x = ram(0, 19);
			y = ram(0, 19);
			while (gameBoard[x][y] != 0) {
				x = ram(0, 19);
				y = ram(0, 19);
			}
			gameBoard[x][y] = 2;
		}
		gameSave();

	}

	public static void main(String args[]) {
		Timer timer = new Timer();
		System.out.println("欢迎来到贪吃蛇小游戏！\n开发者:Rear82\n操作说明:使用w键向上，s键向下，d键向右，a键向左。\n您也可以输入b键来撤销上一步操作。\n输入1来载入上次游戏，输入2开始新的游戏！");
		Scanner input = new Scanner(System.in);
		String m = input.next();
		System.out.println("请输入刷新间隔时间:");
		int t = input.nextInt();
		int u = 0;
		switch (m) {
			case "1":
				survive = true;
				try {
					readFromFile();
				} catch (Exception ex) {
					Logger.getLogger(EatSnake.class.getName()).log(Level.SEVERE, null, ex);
				}
				u = 1;
				gameSave();

				break;
		}
		if (u == 0) {
			clear(gameBoard);
		}
		//System.out.println(gameBoard[19][19]);

		//timer.schedule(new shuru(),1,1)
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("游戏开始!!!");

		th.start();
		timer.schedule(new maingame(), 100, t);

		xmlprint(gameBoard);
	}

}
