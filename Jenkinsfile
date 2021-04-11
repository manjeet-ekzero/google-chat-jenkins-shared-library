#!/usr/bin/groovy
def project_name = 'oneviz'
def project_service_name = 'communication-service'
def buildLabel = "slave.${env.JOB_NAME}.${env.BUILD_NUMBER}"

podTemplate(label: buildLabel, containers: [
    containerTemplate(name: 'build-container', image: 'docker:dind', command: 'dockerd', ttyEnabled: true, privileged: true,),
],
) {
    node(buildLabel) {
        deleteDir()

        try{
            stage('Checkout') {
                echo "Checkout Started"
                myRepo = checkout scm
                print myRepo
                gitRepoUrl = myRepo.GIT_URL
                gitCommit = myRepo.GIT_COMMIT
                gitBranch = myRepo.GIT_BRANCH
                shortGitCommit = "${gitCommit[0..10]}"
                branchName = myRepo.GIT_LOCAL_BRANCH
                imageTag = "community-backend-"+ env.BRANCH_NAME + '-' + shortGitCommit + '-' + env.BUILD_ID
                authorName = sh(script: "git log -1 --pretty=%an ${gitCommit}", returnStdout: true).trim()
                echo "Checkout Done"
            }
            stage('Notification') {
                postAlways()
            }
        }
        catch (err) {                   
            print("Pipeline failed. ERROR: " + err)
            postFailure(err)
        }
    }
}


def postFailure(e) {
    currentBuild.result = "FAILURE"
    msg =  'failed: ' + e
    sendNotification("Failure", msg)
}

def postAlways() {
    currentBuild.result = "SUCCESS"
    msg =  'Successfully deployed'
    sendNotification("Success", msg)
}

def sendNotification(String status, String msg) {

    String google_chat_url = "https://chat.googleapis.com/v1/spaces/AAAAouuYqOI/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=Trd0apwSaH_4mF6yYTpVXLTtvGDfwrdGEVdZJ3KnX7w%3D"
    String branch_name = "${env.BRANCH_NAME}"
    String build_number = "${env.BUILD_NUMBER}"
    String job_name = env.JOB_NAME.split('/')[0]
    String job_url = "${env.BUILD_URL}console"
    googleChat.sendNotification(google_chat_url,job_name,branch_name,build_number,status,gitCommit,authorName,job_url,gitRepoUrl,msg)
}