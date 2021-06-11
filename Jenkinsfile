pipeline {
    agent any

    options {
        buildDiscarder(
            logRotator(
                numToKeepStr:'5', // number of build logs to keep
                //daysToKeepStr: '15', // history to keep in days
                //artifactDaysToKeepStr: '15', // artifacts are kept for days
                artifactNumToKeepStr: '5' // number of builds have their artifacts kept
            )
        )
    }

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
        APP_NAME = "vstore"
        APP_LISTENING_PORT = "8080"
        PG_CONTAINER_NAME = "postgres_vstore_ci"
        GIT_COMMIT_REV=''
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
            when {
                //TODO remove this block later
                expression { params.RUN_TESTS == true }
            }
            steps {
                echo "=== build and push docker image ==="
                script {    
                    GIT_COMMIT_REV = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
                }

                sh "docker build -t ${params.DOCKERHUB_REP}:${GIT_COMMIT_REV} -t ${params.DOCKERHUB_REP}:latest ."

                withDockerRegistry([ credentialsId: params.DOCKERHUB_CREDENTIALS, url: "" ]) {
                    sh "docker push ${params.DOCKERHUB_REP}:${GIT_COMMIT_REV}"
                    sh "docker tag ${params.DOCKERHUB_REP}:${GIT_COMMIT_REV} ${params.DOCKERHUB_REP}:latest"
                }
            }
        }

        stage('Deploy') {
            agent {
                docker {
                    image 'cicd-ansible:latest'
                    args '--network host -v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                echo "=== deploy ==="
                script {
                    //TODO remove this block later  
                    GIT_COMMIT_REV = "2a15074"
                }

                sh "ansible --version"
                //TODO call ansible playbook
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