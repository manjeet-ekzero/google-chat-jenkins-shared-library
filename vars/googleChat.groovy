def sendNotification(
    String google_chat_url,
    String job_name,
    String branch_name,
    String build_number,
    String status,
    String gitCommit,
    String authorName,
    String job_url,
    String gitRepoUrl,
    String msg
    ) {

    template = """
    {
        "cards": [
            {
                "header": {
                    "title": "${job_name}",
                    "subtitle": "${branch_name}",
                },
                "sections": [
                    {
                        "header": "Build Info",
                        "widgets": [
                            {
                                "keyValue": {
                                    "topLabel": "Build No.",
                                    "content": "${build_number}",
                                }
                            },
                            {
                                "keyValue": {
                                    "topLabel": "Status",
                                    "content": "${status}"
                                }
                            }
                        ]
                    },
                    {
                        "header": "Git Info",
                        "widgets": [
                            {
                                "keyValue": {
                                    "topLabel": "<b>Git Commit</b>",
                                    "content": "${gitCommit}",
                                }
                            },
                            {
                                "keyValue": {
                                    "topLabel": "Git aurther",
                                    "content": "${authorName}"
                                }
                            }
                        ]
                    },
                    {
                        "widgets": [
                            {
                                "buttons": [
                                    {
                                        "textButton": {
                                            "text": "OPEN PIPELINE",
                                            "onClick": {
                                                "openLink": {
                                                    "url": "${job_url}"
                                                }
                                            }
                                        }
                                    },
                                    {
                                        "textButton": {
                                            "text": "OPEN REPO",
                                            "onClick": {
                                                "openLink": {
                                                    "url": "${gitRepoUrl}"
                                                }
                                            }
                                        }
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    }
    """

    sh """
        curl -s -H 'Content-Type: application/json' \
        -X POST "${google_chat_url}" \
        --data '${template}' \
        > /dev/null
    """
}