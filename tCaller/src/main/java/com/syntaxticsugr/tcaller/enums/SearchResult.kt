package com.syntaxticsugr.tcaller.enums

enum class SearchResult {
    SUCCESS,
    //    {
    //        "data": [
    //            {
    //                "id": "String",
    //                "name": "String",
    //                "imId": "String",
    //                "gender": "String",
    //                "image": "String (image url)",
    //                "score": "Float",
    //                "access": "String",
    //                "enhanced": "Boolean",
    //                "phones": [
    //                    {
    //                        "e164Format": "String",
    //                        "numberType": "String",
    //                        "nationalFormat": "String",
    //                        "dialingCode": "Int",
    //                        "countryCode": "String",
    //                        "carrier": "String",
    //                        "spamScore": "Int",
    //                        "spamType": "String",
    //                        "type": "String"
    //                    }
    //                ],
    //                "addresses": [
    //                    {
    //                        "address": "String",
    //                        "city": "String",
    //                        "countryCode": "String",
    //                        "timeZone": "String",
    //                        "type": "String"
    //                    }
    //                ],
    //                "internetAddresses": [
    //                    {
    //                        "id": "String",
    //                        "service": "String",
    //                        "caption": "String",
    //                        "type": "String"
    //                    }
    //                ],
    //                "badges": ["String"],
    //                "tags": [],
    //                "businessProfile": {
    //                    "logoUrl": "String",
    //                    "imageUrls": ["String"],
    //                    "openHours": [],
    //                    "name": "String",
    //                    "appStores": [],
    //                    "brandedMedia": [],
    //                    "mediaCallerIDs": [],
    //                    "businessMessages": []
    //                },
    //                "spamInfo": {
    //                    "spamScore": "Int",
    //                    "spamType": "String",
    //                    "spamStats": {
    //                        "numReports": "Int",
    //                        "numReports60days": "Int",
    //                        "numSearches60days": "Int",
    //                        "numCallsHourly": ["Int"],
    //                        "numCalls60days": "Int",
    //                        "numCallsNotAnswered": "Int",
    //                        "numCallsAnswered": "Int",
    //                        "topSpammedCountries": [
    //                            {
    //                                "countryCode": "String",
    //                                "numCalls": "Int"
    //                            }
    //                        ],
    //                        "numMessages60days": "Int",
    //                        "numCalls60DaysPointerPosition": "Int",
    //                        "spammerType": "Caller",
    //                        "numMessagesHourly": ["Int"],
    //                        "numMessages60DaysPointerPosition": "Int"
    //                    },
    //                    "spamCategories": [],
    //                    "spamVersion": "Int"
    //                },
    //                "cacheTtl": "Int",
    //                "sources": [],
    //                "searchWarnings": [],
    //                "surveys": [
    //                    {
    //                        "id": "String",
    //                        "frequency": "Int",
    //                        "passthroughData": "String",
    //                        "perNumberCooldown": "Int",
    //                        "dynamicContentAccessKey": "String"
    //                    }
    //                ],
    //                "commentsStats": {
    //                    "showComments": "Boolean"
    //                },
    //                "manualCallerIdPrompt": "Boolean",
    //                "ns": "Int"
    //            }
    //        ],
    //        "provider": "String",
    //        "stats": {
    //            "sourceStats": []
    //        }
    //    }
    REQUEST_QUOTA_EXCEEDED,
    ERROR
}
