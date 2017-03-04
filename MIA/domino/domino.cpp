#include<iostream>

using namespace std;

int main(){
    int n = 1000001;
    int m,a,b;
    cin >> m;
    int deg[n];
    for(int i = 0; i < n; i++){
        deg[i] = 0;
    }
    for(int i = 0; i < m; i++){
        cin >> a >> b;
        deg[a]++;
        deg[b]++;
    }
    int p = -1;
    int k = -1;
    bool wiecej = false;
    for(int i = 1; i < n; i++){
        if(deg[i] == 1){
            if(p == -1){
                p = i;
            } else{
                if(k == -1){
                    k = i;
                } else{
                    wiecej = true;
                }
            }
        }
    }
    if(wiecej) cout << "NIE\n";
    else{
        cout << p << " " << k << "\n";
    } 
    return 0;
}


