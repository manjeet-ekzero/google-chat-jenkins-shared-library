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
                                "textParagraph": {
                                    "text": "<b>Build No. :</b><br>${build_number}"
                                }
                            },
                            {
                                "textParagraph": {
                                    "text": "<b>Status :</b><br>${status}"
                                }
                            }
                        ]
                    },
                    {
                        "header": "Git Info",
                        "widgets": [
                            {
                                "textParagraph": {
                                    "text": "<b>Git Commit :</b><br>${gitCommit}"
                                }
                            },
                            {
                                "textParagraph": {
                                    "text": "<b>Git aurther :</b><br>${authorName}"
                                }
                            }
                        ]
                    },
                    {
                        "header": "Additional Message",
                        "widgets": [
                            {
                                "textParagraph": {
                                    "text": "<b>Message :</b><br>${msg}"
                                }
                            },
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