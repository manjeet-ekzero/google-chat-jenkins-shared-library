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
                throw new Exception("Something Weired happened")
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
    msg =  'HEAD_TITLE==title;;HEAD_SUBTITLE==subtitle;;MESSAGE==failure'
    sendNotification("Failure", msg)
}

def postAlways() {
    currentBuild.result = "SUCCESS"
    msg =  'HEAD_TITLE==title;;HEAD_SUBTITLE==subtitle;;MESSAGE==success'
    sendNotification("Success", msg)
}

def sendNotification(String status, String msg) {
    
    def job_name = env.JOB_NAME.split('/')[0]
    def job_url = "${env.BUILD_URL}console"
