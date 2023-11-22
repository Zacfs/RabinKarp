import java.util.*;
import java.io.*;
import java.text.*;
class Main{
	static void toBin(){
		try{
		int id = 0;
		String s = "a";
		FileReader fr = new FileReader("animes.csv");
		BufferedReader br = new BufferedReader(fr);
		while(br.readLine()!=null){
			id++;
		}
		fr.close();
		fr = new FileReader("animes.csv");
		br = new BufferedReader(fr);
		s = br.readLine();
		FileOutputStream arq;
        DataOutputStream dos;
		arq = new FileOutputStream("animes.bin");
        dos = new DataOutputStream(arq);
		dos.writeInt(id-1);
		id = 0;
		while(s!=null){
		Anime tst = new Anime();
		tst.ler(s);
		tst.id = id;
		id++;
        byte[] ba;
        int len;
		//if(tst.titulo.length()<150){
		  try {
            ba = tst.toByteArray();
			dos.writeChar('a');
            dos.writeInt(ba.length); //Tamano do registro em bytes
            dos.write(ba);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		//}
		s=br.readLine();
		}
		dos.close();
		br.close();
		fr.close();
		}
		catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
		} 
	}
	static void index() throws Exception{
		RandomAccessFile raf = new RandomAccessFile("animes.bin","rw");
		RandomAccessFile ind = new RandomAccessFile("animesIndex.bin","rw");
		raf.seek(4);
		int id=0;
		long pos=0;
		int size=0;
		char a= ' ';
		while(raf.getFilePointer()<raf.length())
		{
			a=raf.readChar();
			if(a=='a'){
				pos = raf.getFilePointer();
				size=raf.readInt();
				id=raf.readInt();
				ind.writeInt(id);
				ind.writeLong(pos);
				raf.skipBytes(size-4);
			}
			else{raf.skipBytes(raf.readInt());}
		}
	}
	static void fromBin() throws IOException
	{
		FileInputStream arq2;
        DataInputStream dis;
		arq2 =  new FileInputStream("animes.bin");
        dis = new DataInputStream(arq2);
		int total = dis.readInt();
		for(int i =0;i<total;i++)
		{
		if((char)dis.readChar()=='a'){
			int len = dis.readInt();
			byte[] data = new byte[len];
			dis.read(data);
			Anime tst = new Anime();
			tst.ler(data);
		}
		}
	}
	public static void main(String[] args) throws Exception
	{
		int x = 0;
		Scanner sc = new Scanner(System.in);
		//toBin();
		while(x!=5){
			System.out.println("\n\nMenu Principal\n\n1- Rabin Karp\n\n2- Boyer-Moore\n\n5- Sair");
			x = sc.nextInt();
			switch(x){
				case 1: MainRabinKarp.mainCaller();break;
				case 2: MainBoyerMoore.mainCaller();break;
			}
		}
	}
}

