package com.example.flo.ui.signup

import com.example.flo.data.remote.AuthResponse

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(resp: AuthResponse)
}