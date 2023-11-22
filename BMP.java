/* Java program for Boyer Moore Algorithm with 
Good Suffix heuristic to find pattern in
given text string */
import java.util.*;
import java.io.*;
class BMP extends Thread
{
	RandomAccessFile raf;
	int id;
	int tm;
	long ini;
	long fim;
	String pt;
	int cmp;
	BMP(int id, long ini, long fim, String pt, String fl) throws Exception{
		raf = new RandomAccessFile(fl, "rw");
		this.id=id;
		this.ini=ini;
		this.fim=fim;
		this.pt = pt;
		tm = pt.length();
		cmp =0;
	}
	public void run()
    { 
		try{
        search();
		}
		catch(Exception e){System.out.println("Erro " + id);}
    }
  void badCharHeuristic( String str, int badchar[])
     {
 
      // Initialize all occurrences as -1
      for (int i = 0; i < 256; i++)
           badchar[i] = -1;
 
      // Fill the actual value of last occurrence 
      // of a character (indices of table are ascii and values are index of occurrence)
      for (int i = 0; i < tm-1; i++)
           badchar[(int) str.charAt(i)] = i;
     }
	 long skip(char a, int pos, int[] badchar, int[] shift){
		 if((int) a > 255){a=0;}
		return badchar[(int) a] > shift[pos]? (long)badchar[(int) a] : (long)shift[pos];
	 }
// preprocessing for strong good suffix rule
 void preprocess_strong_suffix(int []shift, int []bpos,
                                      String pat, int m)
{
    // m is the length of pattern 
    int i = m, j = m + 1;
    bpos[i] = j;
 
    while(i > 0)
    {
        /*if character at position i-1 is not 
        equivalent to character at j-1, then 
        continue searching to right of the
        pattern for border */
        while(j <= m && pat.charAt(i - 1) != pat.charAt(j - 1))
        {
            /* the character preceding the occurrence of t 
            in pattern P is different than the mismatching 
            character in P, we stop skipping the occurrences 
            and shift the pattern from i to j */
            if (shift[j] == 0)
                shift[j] = j - i;
 
            //Update the position of next border 
            j = bpos[j];
        }
        /* p[i-1] matched with p[j-1], border is found.
        store the beginning position of border */
        i--; j--;
        bpos[i] = j; 
    }
}
 
//Preprocessing for case 2
 void preprocess_case2(int []shift, int []bpos,
                              String pat, int m)
{
    int i, j;
    j = bpos[0];
    for(i = 0; i <= m; i++)
    {
        /* set the border position of the first character 
        of the pattern to all indices in array shift
        having shift[i] = 0 */
        if(shift[i] == 0)
            shift[i] = j;
 
        /* suffix becomes shorter than bpos[0], 
        use the position of next widest border
        as value of j */
        if (i == j)
            j = bpos[j];
    }
}
 
/*Search for a pattern in given text using
Boyer Moore algorithm with Good suffix rule */
 boolean search() throws Exception
{
    // s is shift of the pattern 
    // with respect to text
	int j;
    int m = pt.length();
    long s = 0;
	if(ini!=0){
		s=ini-m;
	}
	long n = fim;
 
    int []bpos = new int[m + 1];
    int []shift = new int[m + 1];
	int []badchar = new int[256];
    //initialize all occurrence of shift to 0
    for(int i = 0; i < m + 1; i++) 
        shift[i] = 0;
 
    //do preprocessing
    preprocess_strong_suffix(shift, bpos, pt, m);
    preprocess_case2(shift, bpos, pt, m);
	badCharHeuristic(pt, badchar);
	/*for(int i = 0; i<256;i++)
	{
		System.out.print(badchar[i]+ " ");
	}
	System.out.println();
	for(int i = 0; i<pat.length();i++)
	{
		System.out.print(shift[i]+ " ");
	}*/
	shift[m] = 8;
    while(s <= n - m)
    {
        j = m - 1;
		char a = 'a';
        /* Keep reducing index j of pattern while 
        characters of pattern and text are matching 
        at this shift s*/
		raf.seek(s+(long)j);
		cmp++;
        while(j >= 0 && (char)pt.charAt(j) == (a = (char)raf.readByte()) && raf.getFilePointer()>-1){
            j--;
			cmp++;
			if(j>-1){
			raf.seek(s+(long)j);}
		}
        /* If the pattern is present at the current shift, 
        then index j will become -1 after the above loop */
        if (j < 0)
        {
            System.out.println("Encontrado na Thread " +id +" na posicao = "+ s + " Comparacoes: " + cmp);
            s += shift[0];return true;
        }
        else
         
            /*pat[i] != pat[s+j] so shift the pattern
            shift[j+1] times */
            s += skip(a, j, badchar, shift)-j;
    }
	System.out.println("Nao encontrado na Thread "+ id);
	if(	activeCount() <= 2){
			System.out.println("\n1- animes.bin\n\n2- animes.csv\n\n5- Sair");
		}
	return false;
}
} 
