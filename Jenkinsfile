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
				deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'taks-backend', war: 'target/taks-backend.war'
			}
		}
	}
}