pipeline {
    agent any

    parameters {
        string (
            defaultValue: 'df_inventory',
            description: 'ansible inventory',
            name : 'ANSIBLE_INVENTORY')
    }

    environment {
        APP_NAME = "vstore-java-pipeline"
        APP_LISTENING_PORT = "8080"
        DOCKER_HUB = credentials("dockerhub")
        PG_CONTAINER_NAME = "postgres_vstore_ci"
    }

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.8.1-adoptopenjdk-11'
                    args '--network host -v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                echo "=== building ==="
                sh 'mvn -B -DskipTests clean package --file ./stock/pom.xml' 
            }
        }
        stage('Test Preparation') {
            steps {
                sh 'docker run --rm --network host --name $PG_CONTAINER_NAME -p 5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -v "$PG_CONTAINER_NAME:/var/lib/postgresql/data" -v "$(pwd)/docker/postgres/sql_scripts:/docker-entrypoint-initdb.d/" -d postgres:13'
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'maven:3.8.1-adoptopenjdk-11'
                    args '--network host -v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                echo "=== testing ==="
                sh 'mvn test --file ./stock/pom.xml'
            }
            post {
                always {
                    junit 'stock/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Push to DockerHub') { 
            steps {
                echo "=== build and push docker image ==="
            }
        }
        stage('Deploy') { 
            steps {
                echo "=== deploy ==="
            }
        }
    }

    post {
        always {
            echo 'Lest clean up this mess -.-'

            sh 'docker stop $PG_CONTAINER_NAME'
            sh 'docker volume rm $PG_CONTAINER_NAME'

            //deleteDir() /* clean up our workspace */
        }
        success {
            echo 'I have succeeded :D'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I have failed :('
        }
        changed {
            echo 'Things were different than before :o'
        }
    }
}