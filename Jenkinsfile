pipeline {
    agent any
    stages {
        stage('Smoke Tests') {
            steps {
                sh 'mvn test -Dgroups="smoke"'
            }
        }
        stage('API Tests') {
            steps {
                sh 'mvn test -Dgroups="api"'
            }
        }
        stage('Regression Tests') {
            when {
                branch 'main'
            }
            steps {
                sh 'mvn test -Dgroups="regression"'
            }
        }
        stage('E2E Tests') {
            when {
                branch 'release/*'
            }
            steps {
                sh 'mvn test -Dgroups="e2e"'
            }
        }
    }
}