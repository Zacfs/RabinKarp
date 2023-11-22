import java.util.*;
import java.io.*;
import java.text.*;
class Anime{
	int id;
	String titulo;
	String sinopse;
	String[] generos;
	Date exibido;
	int episodios;
	double score;
	Anime(){
		id=-1;
		titulo = "";
		sinopse = "";
		generos = new String[100];
		exibido = new Date();
		episodios = 0;
		score = 0.0;
	}
	Anime(String titulo, String sinopse, String[] generos, Date exibido, int eps, double score){
		this.titulo = titulo;
		this.sinopse = sinopse;
		this.generos = generos;
		this.exibido = exibido;
		this.episodios = eps;
		this.score = score;
	}
	//Conversao para array de bytes
	public byte[] toByteArray() throws IOException{

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(titulo);
		dos.writeUTF(sinopse);
		dos.writeInt(generos.length);
		for(int i =0;i<generos.length;i++)
		{
			dos.writeUTF(generos[i]);
		}
		dos.writeLong(exibido.getTime());
		dos.writeInt(episodios);
        dos.writeDouble(score);

        return baos.toByteArray();
    }
	//Leitura de atributos
	void ler(byte[] data) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
		this.id=dis.readInt();
		
		this.titulo=dis.readUTF();
		this.sinopse=dis.readUTF();
		int gen = dis.readInt();
		this.generos = new String[gen];
		for(int i = 0;i<gen;i++)
		{
			this.generos[i]=dis.readUTF();
		}
		this.exibido=new Date(dis.readLong());
		this.episodios=dis.readInt();
		this.score=dis.readDouble();
	}
	void ler(byte[] data,int id) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
		this.id=id;
		this.titulo=dis.readUTF();
		this.sinopse=dis.readUTF();
		int gen = dis.readInt();
		this.generos = new String[gen];
		for(int i = 0;i<gen;i++)
		{
			this.generos[i]=dis.readUTF();
		}
		this.exibido=new Date(dis.readLong());
		this.episodios=dis.readInt();
		this.score=dis.readDouble();
	}
	void ler(String s2){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int pos = 0;
		String[] attr = new String[10000];
		for(int i = 0;i<10000;i++){
			attr[i]="";
		}
		int cont = 0;
		if(pos<s2.length()&&s2.charAt(0)=='\"'){
			pos++;
			attr[cont]+=s2.charAt(pos);
			pos++;
		while(pos<s2.length()&&!((s2.charAt(pos)==','&&(s2.charAt(pos-1)=='\"'&&(s2.charAt(pos-1)!='\"'))))){
			if(s2.charAt(pos)!='\"'){
			attr[cont]+=s2.charAt(pos);
				pos++;
			}else{pos++;}
		}
		}
		else{
			while(pos<s2.length()&&!((s2.charAt(pos)==','))){
			if(s2.charAt(pos)!='\"'){
			attr[cont]+=s2.charAt(pos);
				pos++;
			}else{pos++;}
		}
		}
		pos++;
		cont++;
		while(pos<s2.length()&&!(s2.charAt(pos)==','&&(s2.charAt(pos+-1)=='\"' &&(s2.charAt(pos-2)!='\"')|| s2.charAt(pos+1)==','|| s2.charAt(pos+1)=='['))){
				attr[cont]+=s2.charAt(pos);
				pos++;
		}
		cont++;
		pos++;
		pos+=2;
		int contA = 0;
		if(pos<s2.length()&&s2.charAt(pos)!=','){
		while(pos<s2.length()&&!(s2.charAt(pos)==']'&&(s2.charAt(pos+1)=='\"'||s2.charAt(pos+1)==','))){
				attr[cont]+=s2.charAt(pos);
				if(s2.charAt(pos)=='\'')
				{
					contA++;
				}
				pos++;
		}
		}
		this.generos = new String[contA/2];
		if(pos<s2.length()&&s2.charAt(pos+1)=='\"'){
			pos++;
		}
		pos++;
		if(pos<s2.length()&&s2.charAt(pos+1)=='\"'){
			pos+=2;
		}
		else{pos++;}
		cont++;
		if(pos<s2.length()&&s2.charAt(pos-1)>='A' && (s2.charAt(pos-1)<='Z')){pos--;}
		if(pos<s2.length()&&s2.charAt(pos-1)=='\"'){
		while(pos<s2.length()&&!(s2.charAt(pos+1)==',' && s2.charAt(pos)=='\"')){
			if(s2.charAt(pos)!='\"'){
			attr[cont]+=s2.charAt(pos);
			pos++;
			}
			else{pos++;}
		}
		}
		else{
			while(pos<s2.length()&&!(s2.charAt(pos)==',')){
			if(s2.charAt(pos)!='\"'){
			attr[cont]+=s2.charAt(pos);
			pos++;
			}
			else{pos++;}
		}
		}
		pos+=2;
		cont++;
		if(pos<s2.length()&&(s2.charAt(pos-1)==','&&s2.charAt(pos-2)!=','))
		{
		while(pos<s2.length()&&!(s2.charAt(pos)==',')){
			attr[cont]+=s2.charAt(pos);
			pos++;
		}
		pos++;
		cont++;
		while(pos<s2.length()){
			attr[cont]+=s2.charAt(pos);
			pos++;
		}
		}
		else{
		pos++;
		cont+=2;
		while(pos<s2.length()){
			attr[cont]+=s2.charAt(pos);
			pos++;
		}
		}
			this.titulo = attr[0];
			this.sinopse = attr[1];
			pos = 1;
			for(int i =0;i<generos.length;i++){generos[i]="";}
			for(int i =0;i<generos.length;i++){
				while(attr[2].charAt(pos)!='\''){
					this.generos[i]+=attr[2].charAt(pos);
					pos++;
				}
				pos+=4;
			}
			if(attr[4].length()>0){
			this.episodios=Integer.parseInt(attr[4]);}
			if(attr[5].length()>0){
			this.score = Double.parseDouble(attr[5]);}
	}
	//Mostrar Atributos
	void print(){
		System.out.println(this.titulo);
			System.out.println(this.sinopse);
			System.out.println(this.episodios);
			System.out.println(this.score);
			System.out.println(this.exibido);
			System.out.println(Arrays.toString(this.generos));
	}
	
}
