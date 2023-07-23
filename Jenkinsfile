pipeline {
  agent any
  parameters{
    choice(name: 'choice', choices: ['Release', 'Debug'], description: 'Pick something')
  }

  options {
    // Stop the build early in case of compile or test failures
    skipStagesAfterUnstable()
  }
  stages {

    stage('Build') {
      steps {
        // Compile the app and its dependencies
        bat "./gradlew clean assemble${params.choice}"
      }
    }

    stage('Deploy') {
      steps {
        // Compile the app and its dependencies
        archiveArtifacts artifacts: '**/*.apk', fingerprint: true, onlyIfSuccessful: true
      }
    }
  }   
    
}
