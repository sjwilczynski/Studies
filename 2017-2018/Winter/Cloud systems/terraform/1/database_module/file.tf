variable "server_public_ip" {
  description = "Public ip of the created server "
}

variable "main_server_name" {
  description = "name of the host server"
}


###### DATABASE ######

resource "google_sql_database_instance" "main-db" {
  name = "main-db-instance-new6"
  database_version = "MYSQL_5_6"
  region = "europe-west1"

  settings {
    tier = "db-f1-micro"
    disk_autoresize = "true"
    ip_configuration = {
      ipv4_enabled = true

      authorized_networks = [
        {
          name = "my_network"
          value = "${var.server_public_ip}/32"
        },
      ]
    }
  }
}

resource "google_sql_database" "users" {
  instance  = "${google_sql_database_instance.main-db.name}"
  name      = "users-database"
}

resource "google_sql_user" "machine-user" {
  instance  = "${google_sql_database_instance.main-db.name}"
  name      = "stachu"
  password  = "stachu"
  host      = "${var.server_public_ip}"
}



output "server_ip" {
  value = "${var.server_public_ip}"
}

output "database-ip" {
  value = "${google_sql_database_instance.main-db.ip_address}"
}

