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

    stage('Test') {
      steps {
        // Compile the app and its dependencies
        bat """
        cd /d D:\Katalon_Studio_Windows_64-8.6.5
        katalonc -projectPath="C:\Users\User\Katalon Studio\tes4\tes4.prj" -retry=0 -testSuitePath="Test Suites/New Test Suite" -browserType="Chrome" -executionProfile="default" --config -proxy.auth.option=NO_PROXY -proxy.system.option=NO_PROXY -proxy.system.applyToDesiredCapabilities=true
        """
      }
    }
  }   
    
}
