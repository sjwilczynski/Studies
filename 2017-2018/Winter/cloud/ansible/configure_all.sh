#!/usr/bin/env bash
ansible-playbook -i hosts appserver.yml -t appserver
ansible-playbook -i hosts loadbalancer.yml -t loadbalancer
ansible-playbook -i hosts network.yml -t bastion