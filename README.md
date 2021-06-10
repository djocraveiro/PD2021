# PD2021
PD2021 


## Use "dockerfile" to setup a container for jenkins
docker build -t jenkins-docker .

sudo chmod 777 /var/run/docker.sock

### restart docker if required
sudo systemctl restart docker
docker container start jenkins