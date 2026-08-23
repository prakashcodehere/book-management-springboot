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
            options {
                timeout(time: 2, unit: 'MINUTES')
            }
            steps {
                bat '''
                echo Waiting for MySQL to become healthy...

                :waitloop
                docker compose ps mysql-db | findstr "healthy" >nul

                if %ERRORLEVEL% EQU 0 (
                    echo MySQL is healthy!
                    goto :done
                )

                echo MySQL is not ready yet. Waiting 5 seconds...
                ping 127.0.0.1 -n 6 >nul
                goto :waitloop

                :done
                echo MySQL is ready!
                docker compose ps mysql-db
                '''
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker compose build book-management-app'
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker compose up -d book-management-app'
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'docker compose ps'
            }
        }
    }

    post {
        always {
            echo 'Deployment status:'
            bat 'docker compose ps'
        }

        success {
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}