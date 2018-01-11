variable "instance-count" {
  description = "number of instances to create"
  default = 1
}
variable "subnet-ids" {
  description = "Ids of the subnets to put the instance in"
  type = "list"
}
variable "subnet-azs" {
  description = "Availibility zones of the subnets"
  type = "list"
}
variable "security-group-id" {
  description = "Id of the security group"
}
/*
resource "null_resource" "known-hosts" {
  provisioner "local-exec" {
    command = "mv /home/stachu/.ssh/known_hosts /home/stachu/.ssh/known_hosts.bck"
  }
}
*/

resource "aws_instance" "instances" {
  count = "${var.instance-count}"
  ami = "ami-0def3275"
  instance_type = "t2.nano"
  vpc_security_group_ids = ["${var.security-group-id}"]
  key_name = "newkey"
  subnet_id = "${element(var.subnet-ids,count.index)}"
  availability_zone = "${element(var.subnet-azs,count.index)}"
  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update -y
              sudo apt-get install -y nginx
              echo "Hello Stachu, cool server${count.index}" > index.html
              sudo scp index.html /var/www/html/index.html
			  sudo chmod +x /etc/init.d/nginx
              update-rc.d -f nginx defaults
              EOF

  tags {
    name = "ins${count.index}"
  }
}

output "public-ips" {
  value = "${aws_instance.instances.*.public_ip}"
}
 output "private-ips" {
   value = "${aws_instance.instances.*.private_ip}"
 }
output "ids" {
  value = "${aws_instance.instances.*.id}"
}

