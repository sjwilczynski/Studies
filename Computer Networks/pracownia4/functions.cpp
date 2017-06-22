//Stanisław Wilczyński 272955
#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <errno.h>
#include <unistd.h>
#include <iostream>
#include <sys/stat.h>
#include "functions.h"

using namespace std;
int my_port;

void launch_server(int port, string directory){

	if( !is_directory(directory) ){
		cout << "Directory not found. Server cannot be launched\n";
	} else {
		my_port = port;
		int sock = my_socket(AF_INET, SOCK_STREAM, 0);
		sockaddr_in my_addr;
		bzero(&my_addr, sizeof(my_addr));
	    my_addr.sin_port = htons(port);
	    my_addr.sin_family = AF_INET;
	    my_addr.sin_addr.s_addr = INADDR_ANY;
	    int optname = 1;
	    my_setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &optname, sizeof(int));
	    my_bind(sock, (struct sockaddr *) &my_addr, sizeof(my_addr));
	    my_listen(sock, 1);

	    int client_socket = 0, addrlen, ready = 0;
	    sockaddr_in address;
	    fd_set readfds;
	    while(true){
	    	if(client_socket > 0){
	    		FD_ZERO(&readfds);
	    		FD_SET(client_socket, &readfds);
	    		struct timeval time_limit;
	    		time_limit.tv_sec = 0; 
				time_limit.tv_usec = 300000;
				ready = my_select (client_socket+1, &readfds, NULL, NULL, &time_limit);
				while(ready){
					char buffer[1024];
					int len = my_recv(client_socket, &buffer, 1024, 0);
					if(len == 0){
                        break;
					} else{
						string request = buffer;
						if(generate_response(client_socket, request, directory)){
							break;
						}
					}
					ready = my_select (client_socket+1, &readfds, NULL, NULL, &time_limit);
				}
				close(client_socket);
				client_socket = 0;
	    	}

	    	FD_ZERO(&readfds);
			FD_SET(sock, &readfds);
			ready = my_select (sock+1, &readfds, NULL, NULL, NULL);
			if(ready){
				client_socket = my_accept(sock, (struct sockaddr *)&address, (socklen_t*)&addrlen);
			} 
	   	}
    }
}

bool generate_response(int client_socket, string req, string directory){

	HttpRequest request = HttpRequest(req, directory);
	if(request.method == "GET"){
	    if(request.path.substr(0,3) == "/.." || request.path.substr(0,2) == "/~"){
	       my_send(client_socket, code403.c_str(), code403.length(), 0);
	    } else{
	       string path_to_file = request.directory + request.domain + request.path;
	       if(is_directory(path_to_file)){
	       		send_code301(client_socket, request);
			} else{
				string type = "";
				if( request.path.find(".") != string::npos){
					type = request.path.substr(request.path.find(".") + 1);
				}
				if(!is_regular_file(path_to_file)){
					my_send(client_socket, code404.c_str(), code404.length(), 0);
				} else{
					send_code200(client_socket, path_to_file, type);
				}
			}
	    }	 
   } else {
      my_send(client_socket , code501.c_str() , code501.length() , 0 );
   }
   return request.close_conn;
}

void send_code200(int client_socket, string path_to_file, string type){

	uint8_t* buffer;
	FILE *file = fopen(path_to_file.c_str(), "rb");
	fseek(file, 0, SEEK_END);          
	long filelen = ftell(file);           
	rewind(file);                      
	buffer = new uint8_t[filelen];
	fread(buffer, 1, filelen, file);
	fclose(file);
	int len = (int) filelen;
	string response = "HTTP/1.1 200 OK\n";
	response += "Content-Type: ";
	if(type == "txt"){
		response += "text/plain";
	}
	else if(type == "html" || type == "css"){
		response += "text/" + type;
	}
	else if(type == "jpg" || type == "jpeg" || type == "png"){
		response += "image/" + type;
	}
	else if(type == "pdf"){
		response += "application/pdf";
	}
	else{
		response += "application/octet-stream";
	}
	response += "\nContent-Length: " + to_string(len) + "\n\n";
	cout << "response:\n" << response;
	my_send(client_socket, response.c_str(), response.length(), 0);
	int total_sent = 0;
	while(total_sent < len){
		int sent = my_send(client_socket, buffer + total_sent, len - total_sent, 0);
		total_sent += sent;
	}
}

void send_code301(int client_socket, HttpRequest request){
	
	string response = "HTTP/1.1 301 Moved Permamently\nLocation: http://";
	response += (request.domain + ":");
	response += to_string(my_port);
	response += request.path;
	response += request.path[request.path.length()-1] == '/' ? "" : "/";
	response += "index.html\n";
	cout << "response:\n" << response;
	my_send(client_socket, response.c_str(), response.length(), 0 );
}

HttpRequest::HttpRequest(string request, string dir){
	
	if(request.length() < 5 || request.find("Host") == string::npos || request.find("Connection") == string::npos){
		this->method = "invalid";
	} else{

		this->directory = dir;
		if(request[0] == 'G'){
			this->method = "GET";
			request = request.substr(4);
		} else{
			this->method = "POST";
			request = request.substr(5);
		}
		this->path = request.substr(0, request.find(" "));
		request = request.substr( request.find("Host: ") + 6 );
		this->domain = request.substr(0, request.find("\n"));
		this->domain = this->domain.substr(0, this->domain.find(":"));
		request = request.substr( request.find("Connection: ") + 12 );
		this->close_conn = request.substr(0, 6) == "close" ? true : false; 
	}
}

int is_regular_file(string path){
    struct stat path_stat;
    stat(path.c_str(), &path_stat);
    return S_ISREG(path_stat.st_mode);
}

int is_directory(string path){
	struct stat path_stat;
    stat(path.c_str(), &path_stat);
    return S_ISDIR(path_stat.st_mode);	
}
