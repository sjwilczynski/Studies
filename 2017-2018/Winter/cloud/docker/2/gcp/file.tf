provider "google" {
  project = "swilczynski-182913"
  region = "europe-west1"
}


resource "google_compute_firewall" "rule1" {
  name = "rule1"
  network = "default"

  allow {
    protocol = "tcp"
    ports    = ["22", "80", "8080", "8081", "8082", "8083" ]
  }
}

resource "google_compute_address" "public_ip" {
  count = 2
  name         = "server-ip-${count.index}"
  address_type = "EXTERNAL"
}

resource "null_resource" "known-hosts" {
  provisioner "local-exec" {
    command = "mv /home/stachu/.ssh/known_hosts /home/stachu/.ssh/known_hosts.bck 2>/dev/null"
  }
}

resource "google_compute_instance" "two-docker-servers" {
  count = 2
  machine_type = "f1-micro"
  name = "myserver-${count.index}"
  zone = "europe-west1-b"
  boot_disk {
    initialize_params {
      image = "cos-cloud/cos-stable-55-8872-76-0"
    }
  }
  network_interface {
    network = "default"
    access_config {
      nat_ip = "${element(google_compute_address.public_ip.*.address, count.index)}"
    }
  }

  provisioner "local-exec" {
    command = <<SCRIPT
    sleep 20
    scp -r /home/stachu/programy/cloud/docker/2/docker/ ${element(google_compute_address.public_ip.*.address, count.index)}:dockerfiles
    SCRIPT
  }
}

output "list-of-ips" {
  value = "${google_compute_address.public_ip.*.address}"
}
