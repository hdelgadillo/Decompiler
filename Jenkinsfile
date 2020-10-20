node('maven') {
  stage('Build') {
    git url: "https://github.com/avrdevops/git-usuarios.git"
    sh "mvn package"
    stash name:"jar", includes:"target/Docauto-0.0.1.jar"
  }
  stage('Build Image') {
    unstash name:"jar"
    sh 'ls -l -R'
    sh "oc start-build usuarios --from-file=target/Docauto-0.0.1.jar --follow"
    
    
  }
  stage('Deploy') {
    openshiftDeploy depCfg: 'usuarios'
    openshiftVerifyDeployment depCfg: 'usuarios', replicaCount: 1, verifyReplicaCount: true

   
  }
}

