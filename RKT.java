import java.util.*;
import java.io.*;
import java.util.concurrent.*;
//Rabin-Karp em Java implementado com uso de multi-threading
class RKT extends Thread { 
    int id;
	int tm;
	long ini;
	int cmp;
	long fim;
	long Q;
	String pt;
	RandomAccessFile raf;
	RKT(int id, long ini, long fim, String pt, String fl) throws Exception{
		raf = new RandomAccessFile(fl, "rw");
		this.id=id;
		this.ini=ini;
		this.fim=fim;
		this.pt = pt;
		tm = pt.length();
		Q = 314325453;
		cmp = 0;
	}
    public void run()
    { 
		try{
        busca();
		}
		catch(Exception e){System.out.println("Erro " + id);}
    }
	//Função hash para comparar strings
	long hash(String key) {
      long h = 0;
      for (int j = 0; j < tm; j++)
         h = (256 * h + key.charAt(j)) % Q;
      return h;
   }
   //Metodo de busca do padrão
	boolean busca() throws Exception
	{
		long sv = 0;
		long sv2 = 0;
		boolean resp = false;
		String tst = "";
		long pfirst = ini;
		if(ini!=0)
		{
		raf.seek(ini-pt.length());
		}
		else{raf.seek(0);}
		sv = hash(pt);
		for(int i = 0;i<pt.length() &&raf.getFilePointer()<raf.length();i++)
			{
				tst+=(char)raf.readByte();
			}
		while(raf.getFilePointer()<fim && resp == false)
		{
			sv2 = hash(tst);
			if(sv2==sv)
			{
				cmp++;
				if(tst.equals(pt)){resp=true;System.out.println("Encontrado na Thread " +id +" posicao: " + pfirst + " Comparacoes: " + cmp);}
				else{
					pfirst+=(long)1;
					tst = tst.substring(1);
					tst+=(char)raf.readByte();
				}
			}
			else{
				cmp++;
				pfirst+=(long)1;
				tst = tst.substring(1);
				tst+=(char)raf.readByte();
			}
		}
		if(!resp){
			System.out.println("Nao encontrado na Thread " +id);
		}
		if(	activeCount() <= 2){
			System.out.println("\n1- animes.bin\n\n2- animes.csv\n\n5- Sair");
		}
		return resp;
	}
} 
