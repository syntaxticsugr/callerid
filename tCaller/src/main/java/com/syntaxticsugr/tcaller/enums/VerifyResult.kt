package com.syntaxticsugr.tcaller.enums

enum class VerifyResult {
    BACKUP_FOUND,
    VERIFICATION_SUCCESSFUL,
    INVALID_OTP,
    RETRIES_LIMIT_EXCEEDED,
    ACCOUNT_SUSPENDED,
    ERROR
}
