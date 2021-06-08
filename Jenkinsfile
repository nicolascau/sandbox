pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn install'
                // echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
                // echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                // sh 'cp ./toto C:\toto'
                // echo 'Deploying....'
            }
        }
    }
}