pipeline {
    agent any

    stages {

        stage('Build and Test') {
            steps {
                bat 'mvnw.cmd clean test'
            }
        }

    }
}