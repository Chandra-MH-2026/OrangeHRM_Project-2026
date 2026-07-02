pipeline {
    agent any

    tools {
        maven 'Maven_3.9.6'
    }

    environment {
        RECIPIENTS = 'chandrakantghasti99@gmail.com'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Chandra-MH-2026/OrangeHRM_Project-2026'
            }
        }

        stage('Build Project') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Archive Extent Report') {
            steps {
                archiveArtifacts artifacts: 'src/test/resources/ExtendReport/extentReport.html', fingerprint: true
            }
        }
    }

    post {

        success {

            emailext(
                subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                to: "${RECIPIENTS}",
                body: """
                <html>

                <body>

                <h2 style="color:green;">Jenkins Build Successful</h2>

                <table border="1" cellpadding="6">

                <tr>
                    <th>Project</th>
                    <td>${env.JOB_NAME}</td>
                </tr>

                <tr>
                    <th>Build Number</th>
                    <td>${env.BUILD_NUMBER}</td>
                </tr>

                <tr>
                    <th>Status</th>
                    <td style="color:green;"><b>SUCCESS</b></td>
                </tr>

                <tr>
                    <th>Duration</th>
                    <td>${currentBuild.durationString}</td>
                </tr>

                </table>

                <br>

                <b>Build URL:</b><br>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a>

                <br><br>

                <b>Console Output:</b><br>
                <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a>

                <br><br>

                <b>Extent Report:</b><br>
                <a href="${env.BUILD_URL}artifact/src/test/resources/ExtendReport/extentReport.html">
                Click Here to View Report
                </a>

                </body>
                </html>
                """
            )
        }

        failure {

            emailext(
                subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                mimeType: 'text/html',
                to: "${RECIPIENTS}",
                body: """
                <html>

                <body>

                <h2 style="color:red;">Jenkins Build Failed</h2>

                <table border="1" cellpadding="6">

                <tr>
                    <th>Project</th>
                    <td>${env.JOB_NAME}</td>
                </tr>

                <tr>
                    <th>Build Number</th>
                    <td>${env.BUILD_NUMBER}</td>
                </tr>

                <tr>
                    <th>Status</th>
                    <td style="color:red;"><b>FAILED</b></td>
                </tr>

                <tr>
                    <th>Duration</th>
                    <td>${currentBuild.durationString}</td>
                </tr>

                </table>

                <br>

                <b>Build URL:</b><br>
                <a href="${env.BUILD_URL}">${env.BUILD_URL}</a>

                <br><br>

                <b>Console Output:</b><br>
                <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a>

                </body>
                </html>
                """
            )
        }

        always {
            cleanWs()
        }
    }
}