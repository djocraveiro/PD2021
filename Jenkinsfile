pipeline {
    agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11'
            args '--network host -v $HOME/.m2:/root/.m2'
        }
    }

    parameters {
        string (
            defaultValue: 'df_inventory',
            description: 'ansible inventory',
            name : 'ANSIBLE_INVENTORY')
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
            /*agent {
                docker {
                    image 'postgres:13'
                    args '--network host --name postgres_vstore_ci -p 5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -v "postgres_vstore_ci:/var/lib/postgresql/data" -v "$(pwd)/docker/postgres/sql_scripts:/docker-entrypoint-initdb.d/"'
                    // Run the container on the node specified at the top-level of the Pipeline, in the same workspace, rather than on a new node entirely:
                    reuseNode true
                }
            }*/
            steps {
                echo "=== testing ==="
                //sh 'docker run --rm -u root --network host --name postgres_vstore_ci -p 5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -v "postgres_vstore_ci:/var/lib/postgresql/data" -v "$(pwd)/docker/postgres/sql_scripts:/docker-entrypoint-initdb.d/" -d postgres:13'
                sh 'mvn test --file ./stock/pom.xml'
                //sh 'docker stop postgres_vstore_ci'
                //sh 'docker volume rm postgres_vstore_ci'
            }
            post {
                always {
                    junit 'stock/target/surefire-reports/*.xml'
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