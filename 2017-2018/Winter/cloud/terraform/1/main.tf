provider "google" {
  project = "swilczynski-182913"
  region = "europe-west1"

  # export GOOGLE_APPLICATION_CREDENTIALS=/path/to/credentials/file.json
}

resource "google_compute_address" "public_ip" {
  name         = "server-ip"
  address_type = "EXTERNAL"
}

module "database" {
  source = "./database_module/"
  server_public_ip = "${google_compute_address.public_ip.address}"
  main_server_name = "${google_compute_instance.main_server.name}"
}
resource "google_compute_network" "my-network" {
  name = "my-new-network2"
  auto_create_subnetworks = "false"
}

resource "google_compute_subnetwork" "my-subnetwork" {
  ip_cidr_range = "10.132.4.0/24"
  name = "my-new-subnetwork"
  network = "${google_compute_network.my-network.self_link}"
}

resource "google_compute_firewall" "my-firewall1" {
  name    = "firewall-rule1"
  network = "${google_compute_network.my-network.self_link}"

  allow {
    protocol = "tcp"
    ports    = ["80", "443"]
  }
}

resource "google_compute_firewall" "my-firewall2" {
  name    = "firewall-rule2"
  network = "${google_compute_network.my-network.self_link}"

  allow {
    protocol = "tcp"
    ports    = ["22"]
  }
  //temporary
  source_ranges = ["31.0.36.172/32", "156.17.4.0/24" ]
}


resource "google_compute_instance" "main_server" {
  name = "myserver"
  machine_type = "f1-micro"
  zone = "europe-west1-b"


  boot_disk {
    initialize_params {
      image = "ubuntu-os-cloud/ubuntu-1404-lts"
    }
  }

  network_interface {
    #network = "${google_compute_network.my-network.self_link}"
    subnetwork = "${google_compute_subnetwork.my-subnetwork.self_link}"
    access_config {
      nat_ip = "${google_compute_address.public_ip.address}"
    }
  }

  tags = ["http-server"]
  metadata_startup_script = <<SCRIPT
  apt-get -y update
  apt-get -y install nginx
  apt-get -y install mysql-client-5.6
  apt-get -y install mysql-client-core-5.6
  export HOSTNAME=$(hostname | tr -d '\n')
  export PRIVATE_IP=$(curl -sf -H 'Metadata-Flavor:Google' http://metadata/computeMetadata/v1/instance/network-interfaces/0/ip | tr -d '\n')
  echo "Welcome to $HOSTNAME - $PRIVATE_IP" > /usr/share/nginx/html/index.html
  service nginx start
  SCRIPT
}

output "server_instance_name" {
  value = "${google_compute_instance.main_server.name}"
}

output "server_ip_address" {
  value = "${google_compute_instance.main_server.network_interface.0.access_config.0.assigned_nat_ip}"
}


