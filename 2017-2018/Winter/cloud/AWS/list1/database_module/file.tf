variable "subnet-group-name" {
  description = "Id of the subnet for the database"
}
variable "sg-id" {
  description = "Id of the security group for db"
}
variable "port" {
  description = "Port for db"
}

resource "aws_db_instance" "my-rds" {
  allocated_storage    = 5
  engine               = "mysql"
  engine_version       = "5.6.37"
  instance_class       = "db.t2.micro"
  name                 = "mydb"
  username             = "foo"
  password             = "bar-1234"
  db_subnet_group_name = "${var.subnet-group-name}"
  port                 = "${var.port}"
  vpc_security_group_ids = ["${var.sg-id}"]
}