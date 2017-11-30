#!/bin/bash
gcloud compute instances create $1 --machine-type=f1-micro --image-project=ubuntu-os-cloud --image-family=ubuntu-1404-lts --tags=http-server --zone=europe-west1-b
