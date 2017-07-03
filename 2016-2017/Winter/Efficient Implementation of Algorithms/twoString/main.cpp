#include <bits/stdc++.h>
using namespace std;

int kmp( string pattern ){
	//cout << "in kmp:" << pattern << " " << text << "\n";
	int k = 0;
	int pi[pattern.size() + 3];
	for(int i = 0; i <= pattern.size(); i++){
		pi[i] = 0;
	}
	for(int q = 2; q <= pattern.size(); q++){
		while(k > 0 && pattern[k] != pattern[q-1]){
			k = pi[k];
		}
		if(pattern[k] == pattern[q-1]){
			k++;
		}
		pi[q] = k;
	}
	return pi[pattern.size()];
}

int main(){
	
	ios::sync_with_stdio(false);
    cin.tie(NULL);
	string s1, s2;
	string ret;
	cin >> s1 >> s2;
	string retStart;
	int start = 0;
	int end = 0;
	while(s1[start] != '*' && s2[start] != '*'){
		if( s1[start] != s2[start] ){
			cout << "impossible" << "\n";
			return 0;
		}
		start ++;
	}
	string retEnd;
  	while (s1[s1.size() - 1 - end] != '*' && s2[s2.size() - 1 - end] != '*') {
    	if (s1[s1.size() - 1 - end] != s2[s2.size() - 1 - end]){
      		cout << "impossible" << "\n";
			return 0;
		}
		end ++;
    }
    retStart = s1.substr(0, start);
    retEnd = s1.substr(s1.size() - end, end);
    s1 = s1.substr( start, s1.size() - start - end );
	s2 = s2.substr( start, s2.size() - start - end );
    //swap(s1,s2);
    //cout << s1 << " " << s2 << "\n\n";
    if(s1 == "*"){
    	swap(s1,s2);
    }
    if(s2 == "*"){
    	int i = s1.find("*"); 
    	ret = retStart + s1.erase(i,1) + retEnd;
    	cout << ret << "\n";
    	return 0;
    } else{
    	int k;
    	if(s1[0] == '*'){
    		swap(s1,s2);
    	}
    	if(s2[0] == '*'){
    		string a = s1.substr( 0, s1.size() - 1 );
			string b = s2.substr( 1, s2.size() - 1 );
    		k = kmp( b + '&' + a);
    		//cout << k << "\n";

    		ret = retStart + a.substr(0, a.size() - k) + b + retEnd;
    	}
    }

    cout << ret << "\n";
    //cout << x.compare(y);
	return 0;
}