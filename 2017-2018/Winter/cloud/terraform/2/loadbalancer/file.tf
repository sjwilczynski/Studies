variable "subnetwork-link" {
  description = "self-link of the subnetwork to create servers in"
}
variable "list-of-ips" {
  type = "list"
  description = "list of server ips"
}

resource "google_compute_address" "public_ip" {
  name         = "server-ip-loadbalancer"
  address_type = "EXTERNAL"
}

resource "google_compute_instance" "loadbalancer" {

  machine_type  = "f1-micro"
  zone = "europe-west1-b"
  name = "loadbalancer-new"
  boot_disk {
    initialize_params {
      image = "ubuntu-os-cloud/ubuntu-1404-lts"
    }
  }
  network_interface {
    subnetwork = "${var.subnetwork-link}"
    access_config {
      nat_ip = "${google_compute_address.public_ip.address}"
    }
  }
  tags = ["load-balancer"]
  provisioner "local-exec" {
    command = "echo ${google_compute_address.public_ip.address} > hosts"
  }
  provisioner "local-exec" {
    command = "ssh-keygen -f /home/stachu/.ssh/known_hosts -R ${google_compute_address.public_ip.address}"
  }
  provisioner "remote-exec" {
    inline = [
      "echo kjagfjkagkjf",

    ]
     connection {
      type = "ssh"
      user = "stachu"
    }
  }
  provisioner "local-exec" {
    command = "ansible-playbook loadbalancer.yml -i hosts --extra-vars '{listofips: [${join(",", var.list-of-ips)}]}' "
  }
}

output "ip" {
  value = "${google_compute_address.public_ip.address}"
}


//ssh -o sctricthostkeychecking
//plik ansible.cfg - w tym flagi do ssh