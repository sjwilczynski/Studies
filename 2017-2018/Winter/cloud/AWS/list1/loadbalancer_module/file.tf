variable "security-group-id" {
  description = "Id of the security group"
}
variable "instance-ids" {
  description = "Ids of the instances"
  type = "list"
}
variable "subnet-ids" {
  description = "Subnet ids for elb"
  type = "list"
}


resource "aws_elb" "elb" {
  name = "my-elb"
  security_groups = ["${var.security-group-id}"]
  subnets = ["${var.subnet-ids}"]
  listener {
    lb_port = 80
    lb_protocol = "http"
    instance_port = 80
    instance_protocol = "http"
  }
  instances = ["${var.instance-ids}"]
}

output "elb-id" {
  value = "${aws_elb.elb.id}"
}