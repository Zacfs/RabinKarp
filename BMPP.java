import java.util.*;
import java.io.*;
//Classe para instanciar as threads
class BMPP{
	RandomAccessFile raf;
	int qt;
	BMP[] bmp;
	BMPP(int qt,String pt, String fl) throws Exception{
		this.qt=qt;
		raf = new RandomAccessFile(fl, "rw");
		bmp = new BMP[qt];
		long ln = raf.length()/(long)qt;
		for(int i =0;i<qt;i++)
		{
			if(i!=qt-1){
			bmp[i] = new BMP(i, (long)i*ln, (i+1)*ln, pt, fl);
			}
			else{bmp[i] = new BMP(i, (long)i*ln, raf.length(), pt, fl);}
		}
	}
	void start(){
		for(int i =0;i<qt;i++)
		{
			bmp[i].start();
		}
	}
}