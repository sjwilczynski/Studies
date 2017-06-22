//Stanisław Wilczyński 272955
#ifndef FUN
#define FUN

#include "wrapper.h"
using namespace std;

struct HttpRequest{
	
	string method;
	string directory;
	string path;
	string domain;
	bool close_conn;

	HttpRequest(string request, string dir);
};

const string code403 = "HTTP/1.1 403 Forbidden\nContent-Type: text/html\nContent-Length: 74\n\n<html><head><title>403</title></head>\n<body>403 - Forbidden</body></html>\n\n";
const string code404 = "HTTP/1.1 404 Not Found\nContent-Type: text/html\nContent-Length: 74\n\n<html><head><title>404</title></head>\n<body>404 - Not Found</body></html>\n\n"; 
const string code501 = "HTTP/1.1 501 Not Implemented\nContent-Type: text/html\nContent-Length: 79\n\n<html><head><title>501</title></head>\n<body>501 - Not Implemented</body></html>\n\n";
void launch_server(int port, string directory);
bool generate_response(int client_socket, string req, string directory);
void send_code200(int client_socket, string path_to_file, string type);
void send_code301(int client_socket, HttpRequest request);

int is_regular_file(string path);
int is_directory(string path);

#endif