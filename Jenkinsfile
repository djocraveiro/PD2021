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
                sh 'docker run --rm --name postgres_vstore_ci -p 5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -v "postgres_vstore_ci:/var/lib/postgresql/data" -v "$(pwd)/docker/postgres/sql_scripts:/docker-entrypoint-initdb.d/" -d postgres:13'
                sh 'mvn test --file ./stock/pom.xml'
                sh 'docker stop postgres_vstore_ci'
                sh 'docker volume rm postgres_vstore_ci'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml' 
                }
            }
        }
        stage('Deliver') { 
            steps {
                echo "=== delivering ==="
                sh './maven-deliver.sh' 
            }
        }
    }
}