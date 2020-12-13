package com.marco.scmexc.models.auth

//import javax.validation.constraints.NotBlank

data class ChangePasswordPayload (
//    @NotBlank
    val oldPassword: String,

//    @NotBlank
    val newPassword: String,

//    @NotBlank
    val newPasswordConfirm: String
)
