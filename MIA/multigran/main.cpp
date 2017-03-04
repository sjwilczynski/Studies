#include <iostream>

using namespace std;
string word;
int len;
int stat_word[27];
int stat_next[27];

bool check(int i){
    for(int j = i; j < len; j += i){
        //cout <<"(i,j): "<< i << " " << j << "\n";
        for(int ind = 0; ind < 27; ind++){
            stat_next[ind] = 0;
            //cout << stat_word[ind] << " ";
        }
        //cout << "\n";
        for(int k = j; k < j+i; k++){
            stat_next[ word[k] - 'a' ]++;
        }
        for(int ind = 0; ind < 27; ind++){
            //cout << stat_next[ind] << " " << stat_word[ind] << "\n";
            if(stat_next[ind] != stat_word[ind]) return false;
        }

    }
    return true;
}
int main()
{
    //ios::sync_with_stdio(false);
    //cin.tie(NULL);
    cin >> word;
    len = word.size();
    int p = -1;
    for(int i = 1; i < len; i++){
        stat_word[ word[i-1] - 'a' ]++;
        if( len%i == 0 && p == -1 ){
            if( check(i) ) p = i;
        }
    }

    if(p == -1) cout << "-1\n";
    else cout << word.substr(0,p);
    return 0;
}
