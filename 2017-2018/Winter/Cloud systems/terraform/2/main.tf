provider "google" {
  project = "swilczynski-182913"
  region = "europe-west1"
  # export GOOGLE_APPLICATION_CREDENTIALS=/path/to/credentials/file.json
}


resource "google_compute_network" "network-task3" {
  name = "network-task3"
  auto_create_subnetworks = "false"
  /*
  provisioner "local-exec" {
    command = "curl -qs ifconfig.co > /home/stachu/programy/cloud/task3/MOJEIP"
  }
  */
}

resource "google_compute_subnetwork" "subnetwork-task3" {
  name = "subnetwork-task3"
  ip_cidr_range = "10.132.10.0/24"
  network = "${google_compute_network.network-task3.self_link}"
}

resource "google_compute_firewall" "rule1" {
  name = "rule1"
  network = "${google_compute_network.network-task3.self_link}"

  allow {
    protocol = "tcp"
    ports    = ["22", "80", "443"]
  }
  source_ranges = ["31.0.36.172/32", "156.17.4.0/24", "10.132.10.0/24" ]
  target_tags = ["load-balancer"]
}

resource "google_compute_firewall" "rule2" {
  name = "rule2"
  network = "${google_compute_network.network-task3.self_link}"

  allow {
    protocol = "tcp"
    ports    = ["22", "80", "443"]
  }
  source_ranges = ["10.132.10.0/24", "31.0.36.172/32"]
  target_tags = ["http-server"]
}


module "httpservers" {
  source = "./http-server/"
  server-count = 1
  server-type = "f1-micro"
  zone = "europe-west1-b"
  www-source = "/home/stachu/programy/cloud/task3/2/www"
  subnetwork-link = "${google_compute_subnetwork.subnetwork-task3.self_link}"
}


module "loadbalancer" {
  source = "./loadbalancer/"
  list-of-ips = "${module.httpservers.list-of-ips}"
  subnetwork-link = "${google_compute_subnetwork.subnetwork-task3.name}"
}



output "Server-ips" {
  value = "${module.httpservers.list-of-ips}"
}

/*
output "loadbalancer ip" {
  value = "${module.loadbalancer.ip}"
}
*/



