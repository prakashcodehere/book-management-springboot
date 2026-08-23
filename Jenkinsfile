pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Source code checked out from GitHub'
            }
        }

        stage('Start Infrastructure') {
            steps {
                bat 'docker compose up -d mysql-db redis'
            }
        }

        stage('Wait for MySQL') {
            steps {
                bat 'timeout /t 20 /nobreak'
                bat 'docker compose ps'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvnw.cmd clean test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker compose build book-management-app'
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker compose up -d'
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'timeout /t 10 /nobreak'
                bat 'curl http://localhost:9090'
            }
        }
    }

    post {

        always {
            echo 'Deployment status:'
            bat 'docker compose ps'
        }

        failure {
            echo 'Pipeline failed!'
        }

        success {
            echo 'CI/CD pipeline completed successfully!'
        }
    }
}