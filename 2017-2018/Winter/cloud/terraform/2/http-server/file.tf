variable "server-count" {
  default = 1
  description = "number of servers to create"
}
variable "server-type" {
  description = "type of server"
}
variable "zone" {
  description = "zone to create servers in"
}
variable "www-source" {
  description = "source of www files"
}
variable "subnetwork-link" {
  description = "self-link of the subnetwork to create servers in"
}

resource "google_compute_address" "public_ip" {
  count = "${var.server-count}"
  name         = "server-ip-${count.index}"
  address_type = "EXTERNAL"
}

resource "google_compute_address" "private_ip" {
  count = "${var.server-count}"
  name  = "server--private-ip-${count.index}"
  subnetwork = "${var.subnetwork-link}"
  address_type = "INTERNAL"
}

resource "null_resource" "known-hosts" {
  provisioner "local-exec" {
    command = "mv /home/stachu/.ssh/known_hosts /home/stachu/.ssh/known_hosts.bck"
  }
}

resource "google_compute_instance" "httpservers" {

  count = "${var.server-count}"
  machine_type = "${var.server-type}"
  name = "myserver-${count.index}"
  zone = "${var.zone}"
  boot_disk {
    initialize_params {
      image = "ubuntu-os-cloud/ubuntu-1404-lts"
    }
  }
  network_interface {
    subnetwork = "${var.subnetwork-link}"
    address = "${element(google_compute_address.private_ip.*.address, count.index)}"
    access_config {
      nat_ip = "${element(google_compute_address.public_ip.*.address, count.index)}"
    }
  }

  tags = ["http-server"]
   provisioner "remote-exec" {
    inline = [
      "sudo apt-get -y update",
      "sudo apt-get -y install nginx",
    ]
     connection {
      type = "ssh"
      user = "stachu"
    }
  }
  provisioner "local-exec" {
    command = <<SCRIPT
    scp ${var.www-source}/index.html ${element(google_compute_address.public_ip.*.address, count.index)}:index.html
    ssh -o "StrictHostKeyChecking no" ${element(google_compute_address.public_ip.*.address, count.index)} -- sudo scp index.html /usr/share/nginx/html/index.html
    SCRIPT
  }
}

output "list-of-ips" {
  value = "${google_compute_address.private_ip.*.address}"
}
