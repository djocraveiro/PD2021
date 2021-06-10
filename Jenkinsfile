pipeline {
    agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11' 
            //args '--network vstore'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    environment {
        APP_NAME = "vstore-java-pipeline"
        APP_CONTEXT_ROOT = "/"
        APP_LISTENING_PORT = "8080"
        TEST_CONTAINER_NAME = "ci-${APP_NAME}-${BUILD_NUMBER}"
        DOCKER_HUB = credentials("dockerhub")
    }

    stages {
        stage('Build') { 
            steps {
                echo "=== building ==="
                sh 'mvn -B -DskipTests clean package --file ./stock/pom.xml' 
            }
        }
        stage('Test') { 
            steps {
                echo "=== testing ==="
                sh 'mvn test' 
            }
            /*post {
                always {
                    junit 'target/surefire-reports/*.xml' 
                }
            }*/
        }
        stage('Deliver') { 
            steps {
                echo "=== deploying ==="
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}