pipeline {
	agent any
	stages {
		stage ('Build Backend') {
			steps {
				bat 'mvn clean package -DskipTests=true'
			}
		}
		stage ('Unit Tests') {
			steps {
				bat 'mvn test'
			}
		}
		stage ('Deploy Backend') {
			steps {
				deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'taks-backend', war: 'target/tasks-backend.war'
			}
		}
		stage ('API Test') {
			steps {
			    dir('api-test') {
					git credentialsId: 'github_login', url: 'https://github.com/masestudos/tasks-api-test'
					bat 'mvn test'
				}
			}
		}
		stage ('Deploy Frontend') {
			steps {
				dir('frontend') {
					git credentialsId: 'github_login', url: 'https://github.com/masestudos/tasks-frontend'
					bat 'mvn clean package'
					deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'taks', war: 'target/tasks.war'
				}
			}
		}
		stage ('Functional Test') {
			steps {
			    dir('functional-test') {
					git credentialsId: 'github_login', url: 'https://github.com/masestudos/tasks-functional-tests'
					bat 'mvn test'
				}
			}
		}
	}
}