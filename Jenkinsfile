pipeline {
  agent any

  triggers {
    pollSCM('@daily')
  }
  options {
    // Hace que si hay pasos en paralelo y uno falla, todo el bloque paralelo falle
    parallelsAlwaysFailFast()
    buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '1')) // Conserva las ultimas 10 ejecuciones pero solo la ultima con su workspace
  }

  tools {
    maven 'Mvn_3.6.2'
    jdk "Jdk_1.8.221"
  }

  stages {
    stage('Clean') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('Build'){
      steps {
        sh "mvn verify source:jar -Dgpg.passphrase=$GPG_PASSPHRASE -e -U"
      }
    }
    // Despues de buildear podemos correr estas tareas en paralelo sin interferencia
    stage('Post-Build'){
      parallel {

        stage('Deploy'){
          input {
            id 'confirmDeployInput'
            message "Deployar artefactos al repositorio?"
            ok "Continuar"
            parameters {
              choice(name: 'CHOICES', choices: ['Sí', 'No'], description: 'Decision de deploy')
            }
          }
          options {
            timeout(time: 3, unit: 'MINUTES') // Si no respondemos la confirmacion queremos que termine igual
          }
          when {
            beforeInput false // Queremos esperar el input
            equals expected: 'Sí', actual: "${CHOICES}"
          }
          steps {
            sh "mvn deploy -e"
          }
        }

        stage('Sonar') {
          tools {
            jdk 'Jdk_11' // SonarScanner will require Java 11+ to run starting in SonarQube 8.x
          }
          when {
            branch 'master' // Sonar ignora el branch en su version community (por lo que solo se puede analizar uno)
          }
          steps {
            withSonarQubeEnv(installationName: 'Sonar@IkariSrv02') {
              sh "mvn $SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -e"
            }
          }
        }

      }
    }
  }
}