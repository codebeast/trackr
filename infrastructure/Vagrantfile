# encoding: utf-8
# -*- mode: ruby -*-
# vi: set ft=ruby :

require 'yaml'

config_file    = ENV['TRACKR_CONFIG_LOCATION']
configs        = YAML.load_file(config_file)
vagrant_config = configs['configs']['default']

Vagrant.configure("2") do |config|

  config.vm.box = "gce"
  
config.vm.provider :google do |google, override|
    google.google_project_id = vagrant_config["project_id"]
    
    google.google_client_email = vagrant_config['client_email']
    
    google.google_json_key_location = vagrant_config['json_key_location']
    
    google.name = "provisioner"

    # `gcloud compute zones list`.
    google.zone = "europe-west1-b"

    # `gcloud compute machine-types list --zone asia-east1-c`.
    google.machine_type = "g1-small"

    # `$ gcloud compute images list`.
    google.image = "ubuntu-1404-trusty-v20150901a"
    
    override.ssh.username = "ajeffrey"
    override.ssh.private_key_path = "~/.ssh/id_rsa"
  end
  
  config.vm.provision "ansible" do |ansible|
    ansible.verbose = "v"
    ansible.playbook = "plays/vagrant.yml"
  end

  config.vm.synced_folder "./", "/vagrant", :nfs => true

end
