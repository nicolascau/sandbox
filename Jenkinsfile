pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn install'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('SonarQube') {
            steps {
                // SonarQube : https://www.sonarqube.org/
                sh 'C:/sonarqube/sonar-scanner.bat -Dsonar.projectKey="MyTest" -Dsonar.sources=. -Dsonar.host.url=http://localhost:9000 -Dsonar.login=ncaudard'
            }
        }
        stage('Integration Tests') {
            steps {
                // SoapUI : https://www.soapui.org/
                sh 'C:/soapui/testrunner.bat -sTest Suite C:/Perso/Developpement/projets/github/sandbox/my-project.xml'
            }
        }
        stage('Security Tests') {
            steps {
                // OWASP Dependency-check : https://owasp.org/www-project-dependency-check/
                sh 'C:/dependency-check/dependency-check.bat --project "MyTest" --scan "C:/Perso/Developpement/projets/github/sandbox/'

                // OWASP ZAP : https://owasp.org/www-project-zap/
                sh 'C:/zap/zap.bat'

            }
        }
        stage('Deploy') {
            steps {
                // Ansible : https://www.ansible.com/
                sh 'ansible-playbook -i myplatform -u ansible sandbox/playbook.yml'
            }
        }
        stage('Acceptance Tests') {
            steps {
                // Selenium : https://www.selenium.dev/selenium-ide/
                sh 'C:/selenium/selenium-side-runner C:/Perso/Developpement/projets/github/sandbox/my-project.side'
            }
        }
    }
}