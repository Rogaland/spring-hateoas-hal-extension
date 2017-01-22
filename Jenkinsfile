#!groovy

node {
    currentBuild.result = "SUCCESS"

    try {
        stage('checkout') {
            checkout scm
        }

        stage('build') {
            sh './mvnw clean package'
        }

        stage('deploy') {
        }
    }

    catch (err) {
        currentBuild.result = "FAILURE"
        throw err
    }
}