node('maven') {
  stage('Build') {
    git url: "https://github.com/hdelgadillo/Decompiler.git"
    sh "mvn package"
    stash name:"jar", includes:"target/decompiler-app-0.0.1-SNAPSHOT.jar"
  }
  stage('Build Image') {
    unstash name:"jar"
    sh 'ls -l -R'
    sh "oc start-build usuarios --from-file=target/decompiler-app-0.0.1-SNAPSHOT.jar --follow"
    
    
  }
  stage('Deploy') {
    openshiftDeploy depCfg: 'usuarios'
    openshiftVerifyDeployment depCfg: 'usuarios', replicaCount: 1, verifyReplicaCount: true

   
  }
}

