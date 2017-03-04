#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

int n,m;

vector< pair<int,int> > primes;
int ilePar[22][22];

int NWD(int a, int b){
    if(b == 0)
        return a;
    else return NWD(b,a%b);
}

int ilePary(int x, int y){
    int wynik = 0;
    for(unsigned int i = 0; i < primes.size(); i++){
        if(primes[i].first >= x && primes[i].second < y) wynik++;
    }
    return wynik;
}

int next(bool* A, int index){
    for(int i = index+1; i <= n; i++)
        if(A[i]) return i;
    return n+1;
}
int intersection(bool* A){
    int wynik = 1;
    int ind1 = 1;
    int ind2 = next(A,ind1);
    while(ind1 != n+1){
        for(int i = 0; i < ilePar[ind1][ind2]; i++)
            wynik = (wynik*2)%m;
        ind1 = ind2;
        ind2 = next(A, ind1);
    }
    //cout << "WYNIK: " << wynik-1 << "\n";
    return wynik-1;
}


int suma()
{
  int answer = 0;
  int sign = 1;
  bool *A = new bool[n+2];

  A[2] = true;
  for (int i = 3; i <= n+1; i++)
    A[i] = false;

  while (!A[n+1])
  {
    answer = (answer + sign * intersection(A))%m;
    if(answer < 0) answer += m;
    //cout << answer << "\n";

    int j = 2;
    while (A[j])
    {
      A[j] = false;
      sign = -sign;
      j++;
    }
    A[j] = true;
    sign = -sign;
  }
  //cout << "RESULT: " << answer << "\n";
  return answer;
}

bool cmp(pair<int,int> a, pair<int,int>b){
    if(a.second != b.second) return a.second < b.second;
    else return a.first < b.first;
}

int main()
{
    for(int i = 1; i <= 20; i++){
        for(int j = i+1; j <=20; j++)
            if(NWD(i,j) == 1) primes.push_back(pair<int,int>(i,j));
    }
    //cout << primes.size();
    cin >> n >> m;
    //cin >> m;
    int wszystkichPar = 1;
    int liczbaPar = 0;
    for(unsigned int i = 0; i < primes.size(); i++){
        if(primes[i].second <= n) liczbaPar++;
    }
    for(int i = 0; i <= 21; i++){
        for(int j = i+1; j <= 21; j++)
            ilePar[i][j] = ilePary(i,j);
    }

    //bool* B = new bool[n+2];
    //for (int i = 2; i <= n+1; i++)
    //    B[i] = false;
    //B[4] = true;

    //cout << "BUUU: " << next(B,1) << " " << next(B,4) << "\n";
    //cout << "BUU: " << ilePar[1][4] << " " <<  ilePar[4][6] << "\n";
    for(int i = 0; i < liczbaPar; i++){
        wszystkichPar = (wszystkichPar * 2) % m;
    }
    //cout << wszystkichPar << " " << suma() << "\n";
    int wynik = wszystkichPar - 1 - suma();
    if(wynik < 0) cout << wynik + m;
    else cout << wynik;
    return 0;
}
