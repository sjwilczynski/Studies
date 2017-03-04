#include <iostream>

using namespace std;

int main()
{
    int ff,fs,sf,ss;
    cin >> ff >> fs >> sf >> ss;
    int res = 0;
    if( ff == 0 && fs == 0){
        res = ss + min(sf,1);
    }
    else{
        if(fs > 0 && sf > 0){
            res += ff+ss+2*min(fs,sf);
            if(fs > sf){
                res += 1;
            }
        }
        else if(fs == 0){
            res = ff;
        }
        else if(sf == 0){
            res = ff+1+ss;
        }
    }
    cout << res << "\n";
    return 0;
}
