import java.util.*;
import java.io.*;
import java.util.concurrent.*;
//Classe para instanciar as Threads
class RK{
	RandomAccessFile raf;
	int qt;
	RKT[] rkt;
	RK(int qt,String pt, String fl) throws Exception{
		this.qt=qt;
		raf = new RandomAccessFile(fl, "rw");
		rkt = new RKT[qt];
		long ln = raf.length()/(long)qt;
		for(int i =0;i<qt;i++)
		{
			if(i!=qt-1){
			rkt[i] = new RKT(i, (long)i*ln, (i+1)*ln, pt, fl);
			}
			else{rkt[i] = new RKT(i, (long)i*ln, raf.length(), pt, fl);}
		}
	}
	void start(){
		for(int i =0;i<qt;i++)
		{
			rkt[i].start();
		}
		
	}
}