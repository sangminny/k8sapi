def version
pipeline{
    agent{
        label any
    }

    parameters {
        string(name: 'projectName', defaultValue: 'k8sapi')
    }

    stages{
        stage("Clone Git"){
            steps{
                echo "========Clone Source from Github========"
                checkout scm
            }
            post{
                success{
                    echo "========Clonning successfully========"
                }
                failure{
                    echo "========Clonning failed========"
                }
            }
        }

        stage("Get Build Version"){
            steps{
                echo "========Get Build Version========"
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
            post{
                success{
                    echo "========Clonning successfully========"
                }
                failure{
                    echo "========Clonning failed========"
                }
            }
        }
        stage("Build"){
            steps{
                echo "========Build========"
                dir ('./'){
                    sh """
                    echo final version: ${version}
                    chmod +x gradlew
                    ./gradlew clean build
                    ls -al ./build
                    """
                }
            }
            post{
                success{
                    echo "========Build successfully========"
                }
                failure{
                    echo "========Build failed========"
                }
            }
        }

        stage("Deploy"){
            steps{
                echo "========Build docker image and push to dockerRegistry========"
                script {
                    docker.withRegistry("https://healthcare.kr.ncr.ntruss.com", 'dockerRegistry') {
                        def customImage = docker.build("healthcare.kr.ncr.ntruss.com/${params.projectName}:${version}")
                        customImage.push()
                    }
                }
            }
            post{
                success{
                    echo "========Deploy successfully========"
                }
                failure{
                    echo "========Deploy failed========"
                }
            }
        }
    }

    post{
        success{
            echo "========pipeline executed successfully ========"
        }
        failure{
            echo "========pipeline execution failed========"
        }
    }
}