//Stanisław Wilczyński 272955
#include "functions.h"

int main(){

	initialize();
	
	while(true){
		
		printRoutingTable();
		readAndSend();
		updateTimeouts();

	}

	return EXIT_SUCCESS;
}