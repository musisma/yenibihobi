package com.example.yenibihobi.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation{
    if (email.isEmpty())
        return RegisterValidation.Failed("Email boş bırakılamaz")
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Yanlış e-posta adresi")
    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("Şifre boş bırakılamaz")

    if(password.length<6)
        return RegisterValidation.Failed("Şifren en az 6 karekter olmalı")

    return RegisterValidation.Success
}