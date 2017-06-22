//Stanisław Wilczyński 272955
#include "functions.h"
#include <cstdlib>
#include <iostream>
using namespace std;

int main(int argc, char** argv){

	if(argc != 3)
   	{
      	cout << "Wrong number of arguments\n";
      	return EXIT_FAILURE;
   	}
   	int port = atoi(argv[1]);
   	if(port < 1024){
   		cout << "Wrong port\n";
      	return EXIT_FAILURE;
   	}
   	string directory(argv[2]);
   	launch_server(port,directory);
	return EXIT_SUCCESS;
}