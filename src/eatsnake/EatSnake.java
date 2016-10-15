
package eatsnake;

import java.util.Timer;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//开发者:Rear82
public class EatSnake
{
	static boolean suv;
	static int changdu = 3;
	static int bushu = 3;
	static int tu[][] = new int[20][20];
	static int tuback[][][]=new int[1000][20][20];
	static int fang = 1;
	static int fangback[]=new int[1000];
	static int lu[][]=new int[1000][5];
	static int luback[][][]=new int[1000][1000][5];
	
	public static void cundang()throws Exception{
		File file = new File("存档.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		BufferedWriter br=new BufferedWriter(new FileWriter(file));
		
		br.write(Integer.toString(changdu));
		br.newLine();
		br.write(Integer.toString(fang));
		br.newLine();
		br.write(Integer.toString(bushu));
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<20;j++)
			{
				br.newLine();
				br.write(Integer.toString(tu[i][j]));
			}
		}
		int j;
		for(j=bushu-changdu+1;j<=bushu;j++)
		{
			br.newLine();
			br.write(Integer.toString(lu[j][1]));
			br.newLine();
			br.write(Integer.toString(lu[j][2]));
		}
		
		br.close();
	}
	
	public static void duqu()throws Exception
	{
		File file =new File("存档.txt");
		if(!file.exists())
		{
			System.out.println("没有发现存档文件！");
			qingkong(tu);
			
		}
		else{
		InputStreamReader isr=new InputStreamReader(new FileInputStream(file),"utf-8");
		int i;
		int j;
		
		 BufferedReader br=new BufferedReader(isr);
		 String s,m;
		 m=null;
		 s=null;
		 int[] shuju=new int[10000];
		 
		 for(i=0;i<1000;i++){
			 shuju[i]=-1;
		 }
		 
		 i=0;
		while((s=br.readLine())!=null){
			shuju[i]=Integer.parseInt(s);
			i++;
		}
		
		changdu=shuju[0];
		fang=shuju[1];
		bushu=shuju[2];
		for(i=0;i<20;i++)
		{
			for(j=0;j<20;j++)
			{
				tu[i][j]=shuju[i*20+j+3];
			}
		}
		
		
		
		for(i=403;i<9999;i++){
			if(shuju[i]==-1){
				break;
			}
			else{
				lu[bushu-changdu+1+(int)((i-403)/2)][(i-403)%2+1]=shuju[i];
			}
		}
		
		br.close();
		}
	}
	
	public static void gameover()
	{
		suv =false;
		myprint("*",20);
		System.out.println("\n");
		myprint(" ",6,"game over!");
		System.out.println("\n");
		myprint(" ",3,"Your score is " + (changdu-3));
		System.out.println("\n");
		myprint("*",20);
		System.out.println("\n\n游戏结束，是否重新开始?(按y重新开始)\n");
		Scanner input = new Scanner(System.in);
		String m = input.next();
		switch (m){
			case "Y":
			case "y":
				qingkong(tu);
				//System.out.println(tu[19][19]);
				Timer timer = new Timer();
				timer.schedule(new mytask(),0,1);
				//timer.schedule(new shuru(),1,1)
				System.out.println("\n");
				System.out.println("\n");
				System.out.println("游戏开始!!!");
				xmlprint(tu);
				break;
			
				
			
				}
		
	}
	
	public static void back()
	{
		bushu--;
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<20;j++)
			{
				tu[i][j]=tuback[bushu][i][j];
			}
			
		}

		for(int i=0;i<1000;i++)
		{
			for(int j=0;j<5;j++)
			{		
				lu[i][j]=luback[bushu][i][j];
			}
		}
		
		fang=fangback[bushu];
	}
	
	public static void jilu()
	{
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<20;j++)
			{
				tuback[bushu][i][j]=tu[i][j];
			}
		}

		for(int i=0;i<1000;i++)
		{
			for(int j=0;j<5;j++)
			{		
				luback[bushu][i][j]=lu[i][j];
			}
		}
		
		fangback[bushu]=fang;
	}
	
	public static int ram(int a,int b)
	{
		int c =(int)(Math.random()*(b-a+1))+a;
		return c;
	}
	
	public static void xmlprint(int[][] arr)
	{
		for(int i =0;i<20;i++)
		{
			for(int j=0;j<20;j++)
			{
				if(j==19)
				{
					System.out.print("□");
					System.out.print("\n");
					
				}
				else
				{
				
					if(i==0||i==19||j==0)
					{
						System.out.print("□");
					}
					else
					{
						switch(arr[i][j])
						{
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
		
	static class shuru extends TimerTask
	{
		
		public void run()
		{
			
			Scanner input = new Scanner(System.in);
			String m = input.next();
			switch(m)
			{
				case "d":
					fang = 1;
					break;
				case "s":
					fang = 2;
					break;
				case "a":
					fang = 3;
					break;
				case "w":
					fang = 4;
					break;
			}
		}
	}
	
	static class mytask extends TimerTask{
		public void run()
		{
			if(suv==true){
			Scanner input = new Scanner(System.in);
			
			String m = input.next();
			String n=m;
			switch(n)
				{
					case "b":
					if(bushu>3){
					System.out.println("已撤销您的上一步操作。");
					back();
					xmlprint(tu);}
					break;
				
				case "s":
				case "a":
				case "w":
				case "d":
			switch(m)
			{
				case "d":
					if(fang!=3){
					fang = 1;}
					break;
				case "s":
					if(fang!=4){
					fang = 2;}
					break;
				case "a":
					if(fang!=1){
					fang = 3;}
					break;
				case "w":
					if(fang!=2){
					fang = 4;}
					break;
				case "":
					break;
				
			}
			bushu++;
			switch(fang)
			{
				case 1:
					lu[bushu][1]=lu[bushu-1][1];
					lu[bushu][2]=lu[bushu-1][2]+1;
					break;
				case 2:
					lu[bushu][1]=lu[bushu-1][1]+1;
					lu[bushu][2]=lu[bushu-1][2];
					break;
				case 3:
					lu[bushu][1]=lu[bushu-1][1];
					lu[bushu][2]=lu[bushu-1][2]-1;
					break;
				case 4:
					lu[bushu][1]=lu[bushu-1][1]-1;
					lu[bushu][2]=lu[bushu-1][2];
					break;
			}
			//for(int i = bushu;i>bushu-changdu;i--)
			//{
				
			//}
			if(tu[lu[bushu][1]][lu[bushu][2]]==1||tu[lu[bushu][1]][lu[bushu][2]]==2){
				gameover();
				cancel();
			}
			else if(tu[lu[bushu][1]][lu[bushu][2]]==3){
			changdu++;
			tu[lu[bushu][1]][lu[bushu][2]]=1;
				int x =ram(0,19);
				int y =ram(0,19);
				while(tu[x][y]!=0){
					x =ram(0,19);
					y =ram(0,19);
				}
				tu[x][y]=3;
				xmlprint(tu);
				
			}
			else{
				tu[lu[bushu][1]][lu[bushu][2]]=1;
				tu[lu[bushu-changdu][1]][lu[bushu-changdu][2]]=0;
				xmlprint(tu);
			}
			
			jilu();
			{
				try {
					cundang();
				} catch (Exception ex) {
					
				}
			}
			break;
			}	
		}
		}
	}
	
	public static void myprint(String c,int a,String b)
	{
		for(int i =0;i<a;i++)
		{
			System.out.print(c);
		}
		System.out.print(b);
	}
	
	public static void myprint(String c,int a)
	{
		for(int i =0;i<a;i++)
		{
			System.out.print(c);
		}
	}
	
	public static void qingkong(int[][] arr)
	{
		changdu = 3;
		bushu = 3;
		fang=1;
		suv=true;
		for(int i =0;i<20;i++)
		{
			for(int j=0;j<20;j++)
			{
				if(i==0 ||i==19 ||j==0 ||j==19){
					arr[i][j]=2;
				}
				else{
				arr[i][j] = 0;}
			}
		}	
	
		for(int i=0;i<1000;i++)
			{
				for(int j=0;j<5;j++)
				{
				lu[i][j]=-1;
				}
			}
		
		
		arr[1][1]=1;
		arr[1][2]=1;
		arr[1][3]=1;
		
		lu[1][1] = 1;
		lu[1][2] = 1;
		lu[2][1] = 1;
		lu[2][2] = 2;
		lu[3][1] = 1;
		lu[3][2] = 3;
		int x =ram(0,19);
		int y =ram(0,19);
		while(tu[x][y]!=0){
			x =ram(0,19);
			y =ram(0,19);
		}
		tu[x][y]=3;
		for(int i=0;i<10;i++){
		x =ram(0,19);
		y =ram(0,19);
		while(tu[x][y]!=0){
			x =ram(0,19);
			y =ram(0,19);
		}
		tu[x][y]=2;
		}
		jilu();
		
	}
	
	public static void main(String args[])
	{ 
		Timer timer = new Timer();
		System.out.println("欢迎来到贪吃蛇小游戏！\n开发者:Rear82\n操作说明:使用w键向上，s键向下，d键向右，a键向左。\n您也可以输入b键来撤销上一步操作。\n输入1来载入上次游戏，输入2开始新的游戏！");
		Scanner input = new Scanner(System.in);
		String m = input.next();
		int u=0;
		switch(m){
			case "1":
			suv=true;
			try {
			duqu();
		} catch (Exception ex) {
			Logger.getLogger(EatSnake.class.getName()).log(Level.SEVERE, null, ex);
		}
			u=1;
			jilu();
			
			break;
		}
		if(u==0){
		qingkong(tu);}
		//System.out.println(tu[19][19]);
		
		
		//timer.schedule(new shuru(),1,1)
		
		
		
		
			
		
		
		
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("游戏开始!!!");
		timer.schedule(new mytask(),0,1);
	
		
		xmlprint(tu);
	}

	
}
