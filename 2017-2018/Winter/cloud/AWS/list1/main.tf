provider "aws"{
  region = "us-west-2"
}


resource "aws_vpc" "my-new-vpc" {
  cidr_block = "10.2.0.0/16"
  enable_dns_support = true
  enable_dns_hostnames = true
  tags {
    Name = "my-vpc"

  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = "${aws_vpc.my-new-vpc.id}"
}


resource "aws_security_group" "http-server-sg"{
  name = "http-server-sg"
  vpc_id = "${aws_vpc.my-new-vpc.id}"

  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "elb-sg" {
  name = "elb-sg"
  vpc_id = "${aws_vpc.my-new-vpc.id}"
  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_route" "internet_access" {
  route_table_id         = "${aws_vpc.my-new-vpc.main_route_table_id}"
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = "${aws_internet_gateway.gw.id}"
}

resource "aws_subnet" "http-subnet1" {
  cidr_block = "${cidrsubnet(aws_vpc.my-new-vpc.cidr_block, 8, 0)}"
  vpc_id = "${aws_vpc.my-new-vpc.id}"
  availability_zone = "us-west-2a"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "http-subnet2" {
  cidr_block = "${cidrsubnet(aws_vpc.my-new-vpc.cidr_block, 8, 1)}"
  vpc_id = "${aws_vpc.my-new-vpc.id}"
  availability_zone = "us-west-2b"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "http-subnet3" {
  cidr_block = "${cidrsubnet(aws_vpc.my-new-vpc.cidr_block, 8, 2)}"
  vpc_id = "${aws_vpc.my-new-vpc.id}"
  availability_zone = "us-west-2c"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "elb-subnet" {
  cidr_block = "${cidrsubnet(aws_vpc.my-new-vpc.cidr_block, 8, 3)}"
  vpc_id = "${aws_vpc.my-new-vpc.id}"
  availability_zone = "us-west-2a"
  map_public_ip_on_launch = true
}

resource "aws_route_table_association" "public_subnet_association1" {
    subnet_id = "${aws_subnet.http-subnet1.id}"
    route_table_id = "${aws_vpc.my-new-vpc.main_route_table_id}"
}
resource "aws_route_table_association" "public_subnet_association2" {
    subnet_id = "${aws_subnet.http-subnet2.id}"
    route_table_id = "${aws_vpc.my-new-vpc.main_route_table_id}"
}
resource "aws_route_table_association" "public_subnet_association3" {
    subnet_id = "${aws_subnet.http-subnet3.id}"
    route_table_id = "${aws_vpc.my-new-vpc.main_route_table_id}"
}


module "servers" {
  source = "./http_server_module/"
  instance-count = 3
  subnet-azs = ["${aws_subnet.http-subnet1.availability_zone}", "${aws_subnet.http-subnet2.availability_zone}", "${aws_subnet.http-subnet3.availability_zone}"]
  subnet-ids = ["${aws_subnet.http-subnet1.id}", "${aws_subnet.http-subnet2.id}", "${aws_subnet.http-subnet3.id}"]
  security-group-id = "${aws_security_group.http-server-sg.id}"
  //replace subnet-azs using * notation - how?
}


module "loadbalancer" {
  source = "./loadbalancer_module/"
  subnet-ids = ["${aws_subnet.http-subnet1.id}", "${aws_subnet.http-subnet2.id}", "${aws_subnet.http-subnet3.id}"]
  security-group-id = "${aws_security_group.elb-sg.id}"
  instance-ids = "${module.servers.ids}"
}


output "public_ips" {
  value = "${module.servers.public-ips}"
}
output "private_ips" {
  value = "${module.servers.private-ips}"
}

output "ids" {
  value = "${module.servers.ids}"
}