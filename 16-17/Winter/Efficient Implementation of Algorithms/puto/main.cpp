#include<iostream>
#include<vector>

using namespace std;

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    int n,c,w;
    int suma = 0;
    int suma_tmp;
    int result_tmp;
    cin >> n;
    cin >> c;
    int wagi[n];
    for(int i = 0; i < n; i++){
        cin >> wagi[i];
    }
    for(int i = 0; i < n; i++){
        suma_tmp = 0;
        result_tmp = 0;
        for (int j = i; j < n; j++){
            if(suma_tmp + wagi[j] <= c){
                suma_tmp += wagi[j];
                result_tmp++;
                }
        }
        //cout << i << " " << result_tmp << "\n";
        if(result_tmp > suma) suma = result_tmp;
    }
    cout << suma << "\n";
    return 0;
}


