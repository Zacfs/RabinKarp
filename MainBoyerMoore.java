import java.util.*;
import java.io.*;
import java.util.concurrent.*;
class MainBoyerMoore{
	static void mainCaller() throws Exception{
		main(null);
	}
	public static void main(String[] args) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int x = 0;
		System.out.println("\n\nBoyer-Moore\n\n1- animes.bin\n\n2- animes.csv\n\n5- Sair");
		while(x!=5){
		x = sc.nextInt();
		switch(x){
			case 1:{
			System.out.println("Digite o Padrão");
			sc.nextLine();
			String pt = sc.nextLine();
			System.out.println("Digite o Numero de Threads(Sera Exibido o Primeiro Resultado de cada Thread)");
			int t = sc.nextInt();
			BMPP tst = new BMPP(t,pt, "animes.bin");
			tst.start();
			break;
			}
			case 2:{
			System.out.println("Digite o Padrão");
			sc.nextLine();
			String pt = sc.nextLine();
			System.out.println("Digite o Numero de Threads(Sera Exibido o Primeiro Resultado de cada Thread)");
			int t = sc.nextInt();
			BMPP tst = new BMPP(t,pt, "animes.csv");
			tst.start();
			break;
			}
		}
		}
	}
}