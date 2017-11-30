#!/bin/bash
gcloud compute instances -q delete $1 --zone=europe-west1-b

