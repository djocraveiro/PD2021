pipeline {
    agent any

    parameters {
        booleanParam (
            defaultValue: true,
            description: 'run tests',
            name : 'RUN_TESTS')
        string (
            defaultValue: 'djocraveiro/pd_2021',
            description: 'dockerhub repository',
            name : 'DOCKERHUB_REP')
        string (
            defaultValue: 'dockerhub',
            description: 'dockerhub credentials',
            name : 'DOCKERHUB_CREDENTIALS')
        string (
            defaultValue: 'df_inventory',
            description: 'ansible inventory',
            name : 'ANSIBLE_INVENTORY')
    }

    environment {
        APP_NAME = "vstore-java-pipeline"
        APP_LISTENING_PORT = "8080"
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
                sh 'mvn -B -DskipTests clean compile --file ./stock/pom.xml'
            }
        }

        stage('Test Preparation') {
            when {
                expression { params.RUN_TESTS == true }
            }
            steps {
                echo "=== prepare for testing ==="
                sh 'docker run --rm --network host --name $PG_CONTAINER_NAME -p 5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -v "$PG_CONTAINER_NAME:/var/lib/postgresql/data" -v "$(pwd)/docker/postgres/sql_scripts:/docker-entrypoint-initdb.d/" -d postgres:13'
            }
        }

        stage('Test') {
            when {
                expression { params.RUN_TESTS == true }
            }
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
                    sh 'docker stop $PG_CONTAINER_NAME'
                    sh 'docker volume rm $PG_CONTAINER_NAME'
                }
            }
        }

        stage('Package') {
            agent {
                docker {
                    image 'maven:3.8.1-adoptopenjdk-11'
                    args '--network host -v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                echo "=== packaging project ==="
                sh "mvn package -DskipTests --file ./stock/pom.xml"
                archiveArtifacts artifacts: 'stock/target/*.jar', fingerprint: true
            }
        }

        stage('Push to DockerHub') { 
            steps {
                echo "=== build and push docker image ==="
                dockerBuildAndPublish {
                    repositoryName(params.DOCKERHUB_REP)
                    tag('${GIT_REVISION,length=9}')
                    registryCredentials(params.DOCKERHUB_CREDENTIALS)
                    forcePull(false)
                    forceTag(false)
                    createFingerprints(false)
                    skipDecorate()
                }
            }
        }

        stage('Deploy') { 
            steps {
                echo "=== deploy ==="
                //TODO call ansible playbook here
            }
        }
    }

    post {
        always {
            echo 'Lest clean up this mess -.-'
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