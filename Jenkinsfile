def version
pipeline {
    agent any

    // triggers {
    //     pollSCM('*/3 * * * *')
    // }
    parameters {
        string(name: 'projectName', defaultValue: 'k8sapi')
    }

    stages {
        // 레포지토리를 다운로드 받음
        stage('Clone Github') {
            agent any
            steps {
                echo '======== Clonning Repository ========'
                checkout scm
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    echo 'Successfully Cloned Repository'
                }
                always {
                    echo "i tried..."
                }
                cleanup {
                    echo "after all other post condition"
                }
            }
        }

        stage('Build version') {
            agent any
            steps {
                script {
                    // echo "** version init : ${params.version} **"
                    version = sh( returnStdout: true, script: "cat build.gradle | grep -o 'version = [^,]*'" ).trim()
                    echo "** version temp : ${version} **"

                    version = version.split(/=/)[1]
                    version = version.replaceAll("'","").trim()
                    // params.put("version", tempSplit)
                    echo "** version load : ${version} **"
                }
            }
        }

        stage('Bulid') {
            agent any
            steps {
                    echo 'Build Backend'
                    dir ('./'){
                        sh """
                        echo final version: ${version}
                        chmod +x gradlew
                        ./gradlew clean build
                        ls -al ./build
                        """
                    }
                }


            post {
                success {
                    echo '======== Successfully Build ========'
                }

                failure {
                  error 'This pipeline stops here...'
                }
            }
        }

        stage('Push') {
            agent any
            steps {
                echo '======== build & registry push ========'

                // * To NDS NCP Image Repository
                script {
                    docker.withRegistry("https://healthcare.kr.ncr.ntruss.com", 'dockerRegistry') {
                        def customImage = docker.build("healthcare.kr.ncr.ntruss.com/${params.projectName}:${version}")
                        customImage.push()
                        //customImage.push("latest")
                    }
                }

                // * To Docker hub Repository - sangminny
                // script {
                //     docker.withRegistry("https://registry.hub.docker.com", 'docker-hub-sangminny') {
                //         def customImage = docker.build("sangminny/${params.projectName}")
                //         customImage.push("${version}")
                //         customImage.push("latest")
                //     }
                // }
            }
         }
    }
}