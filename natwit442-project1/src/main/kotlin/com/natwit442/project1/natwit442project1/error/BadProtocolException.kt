package com.natwit442.project1.natwit442project1.error

class BadProtocolException(s: String) : Throwable() {

    override val message: String?
        get() = "Target must start with http protocol"
}
