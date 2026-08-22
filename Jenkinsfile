pipeline {
    agent any

    stages {
        stage('Test GitHub Connection') {
            steps {
                bat 'git --version'
                bat 'git ls-remote -h https://github.com/prakashcodehere/book-management-springboot.git HEAD'
            }
        }
    }
}