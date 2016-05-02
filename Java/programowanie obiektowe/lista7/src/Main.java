


public class Main
{
	public static void main(String[] args)
	{
		Pojazd[] P = new Pojazd[3];
		P[0] = new Pojazd("Ciagnik","czarny",20);
		P[1] = new Tramwaj("Skoda","Nowa",1,33,"Pilczyce","Stadion");
		P[2] = new Samochod("Toyota","Yaris",5,100000,"benzyna","Continental");
		
		//for(int i = 0; i <= 2; i ++)System.out.print(P[i].toString());
		//System.out.print(P[0].toString());
		SamochodEdit x = new SamochodEdit((Samochod) P[2]);
		x.start();
		String nazwa = "/home/stachu/programy/java/pojazd.ser";
		Tramwaj b =(Tramwaj) P[1];
		System.out.print(b.toString());
		Tramwaj a = new Tramwaj();
		a.readTramwaj(nazwa);
		System.out.print(a.toString());
		//System.out.print(P[0].toString());
	}

}
