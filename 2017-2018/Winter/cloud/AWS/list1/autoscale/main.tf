provider "aws"{
  region = "eu-central-1"
}

data "aws_availability_zones" "all-zones" {}

resource "aws_security_group" "my-default-new" {
  name = "my-default-new"
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
  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_security_group" "elb-sg" {
  name = "terraform-example-elb"
  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "server" {
  ami = "ami-df8406b0"
  instance_type = "t2.nano"
  key_name = "stachukey"
  availability_zone = "eu-central-1a"
  security_groups = ["${aws_security_group.my-default-new.name}"]
  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update -y
              sudo apt-get install -y nginx
              echo "Hello Stachu, cool server, I think" > index.html
              sudo scp index.html /var/www/html/index.html
              sudo chmod +x /etc/init.d/nginx
              update-rc.d -f nginx defaults
              EOF
  tags {
    name = "ins0"
  }
}

resource "aws_elb" "my-elb" {
  name = "my-elb-asg"
  security_groups = ["${aws_security_group.elb-sg.id}"]
  availability_zones = ["${data.aws_availability_zones.all-zones.names}"]

  health_check {
    healthy_threshold = 2
    unhealthy_threshold = 2
    timeout = 3
    interval = 30
    target = "HTTP:80/"
  }

  listener {
    lb_port = 80
    lb_protocol = "http"
    instance_port = 80
    instance_protocol = "http"
  }
}

resource "aws_launch_configuration" "my-launch-conf" {
  image_id = "ami-f68f1299"
  instance_type = "t2.micro"
  security_groups = ["${aws_security_group.my-default-new.id}"]
  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_autoscaling_group" "asg" {
  launch_configuration = "${aws_launch_configuration.my-launch-conf.id}"
  availability_zones = ["${data.aws_availability_zones.all-zones.names}"]
  min_size = 3
  max_size = 5
  load_balancers = ["${aws_elb.my-elb.name}"]
  health_check_type = "ELB"
  tag {
    key = "name"
    value = "asg-instance"
    propagate_at_launch = true
  }
}

output "elb_dns_name" {
  value = "${aws_elb.my-elb.dns_name}"
}
