import java.util.Random;


public class Tablice implements Runnable 
{
	int[] a;
	Random Rand = new Random();
	Tablice(int l)
	{
		a = new int[l];
		for(int i=0;i < l;i++)
		{
			a[i] = Rand.nextInt(1000);
		}
	}
	public void merge(int[] t1, int[] t2)
	{
		int i1=0;
		int i2=0;
		for(int i = 0;i < a.length; i ++)
		{
			if(i2 >= t2.length || (i1 < t1.length && t1[i1] < t2[i2]))
			{
				a[i] = t1[i1];
				i1++;
			}
			else
			{
				a[i] = t2[i2];
				i2++;
			}
		}
	}
	public void mergeSort()
	{
		if(a.length >= 2)
		{
			Tablice l = new Tablice(a.length/2);
			Tablice p = new Tablice(a.length-a.length/2);
			for(int i=0;i < a.length/2;i++)
			{
				l.a[i] = a[i];
			}
			for(int i = a.length/2;i < a.length;i++)
			{
				p.a[i-a.length/2] = a[i];
			}
			l.mergeSort();
			p.mergeSort();
			merge(l.a,p.a);
		}
	}
	public void DuoMergeSort()
	{
		Tablice l = new Tablice(a.length/2);
		Tablice p = new Tablice(a.length-a.length/2);
		for(int i=0;i < a.length/2;i++)
		{
			l.a[i] = a[i];
		}
		for(int i = a.length/2;i < a.length;i++)
		{
			p.a[i-a.length/2] = a[i];
		}
		Thread t1 = new Thread(l);
		Thread t2 = new Thread(p);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {}
		merge(l.a,p.a);

	}
	public void wypisz()
	{
		for(int i=0;i<a.length;i++)System.out.printf("%d ", a[i]);
		System.out.println();
	}
	public void run() 
	{
		mergeSort();	
	}
	public static void main(String[] args) 
	{
		Tablice tab1 = new Tablice(1000000);
		Tablice tab2 = new Tablice(1000000);
		for(int i = 0;i < tab1.a.length;i ++)tab2.a[i] = tab1.a[i];
		long time1 = System.currentTimeMillis();
		tab1.DuoMergeSort();
		long time2 = System.currentTimeMillis();
		long time3 = System.currentTimeMillis();
		tab2.mergeSort();
		long time4 = System.currentTimeMillis();
		System.out.printf("Czas dla 2 watkow:%d milisekund\n", time2-time1);
		System.out.printf("Czas dla 1 watku:%d milisekund\n", time4-time3);
		//tab1.wypisz();
		//tab2.wypisz();
		
	}
}
