package com.marco.scmexc.service.impl

import com.marco.scmexc.models.domain.SmxUser
import com.marco.scmexc.models.exceptions.user.InvalidPasswordException
import com.marco.scmexc.security.JwtTokenProvider
import com.marco.scmexc.security.UserPrincipal
import com.marco.scmexc.service.UserAuthenticationService
import com.marco.scmexc.service.UserService
import com.marco.scmexc.models.auth.ChangePasswordPayload
import com.marco.scmexc.models.auth.JwtAuthenticationResponse
import com.marco.scmexc.models.auth.LoginPayload
import com.marco.scmexc.models.dto.UserDto
import com.marco.scmexc.models.response.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class JwtUserAuthenticationService(
        private val userService: UserService,
        private val authenticationManager: AuthenticationManager,
        private val passwordEncoder: PasswordEncoder,
        private val jwtTokenProvider: JwtTokenProvider
) : UserAuthenticationService {

    override fun authenticateUser(loginPayload: LoginPayload): JwtAuthenticationResponse {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginPayload.email,
                loginPayload.password
            )
        )
        //add is activated check or exception

        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtTokenProvider.generateToken(authentication)

        return JwtAuthenticationResponse(jwt)
    }

    override fun checkPassword(userPrincipal: UserPrincipal, password: String): Boolean {
        val user = userService.getUserById(userPrincipal.id)
        return (passwordEncoder.matches(password, user.password))
    }

    override fun changePassword(userPrincipal: UserPrincipal, password: ChangePasswordPayload): SmxUser {
        val user = userService.getUserById(userPrincipal.id)

        if(!passwordEncoder.matches(password.oldPassword, user.password)) throw InvalidPasswordException()

        val newEncryptedPassword = passwordEncoder.encode(password.newPassword)
        user.password = newEncryptedPassword
        return userService.saveUser(user)
    }

    override fun registerUser(newUser: UserDto): UserResponse {
        val user: SmxUser = userService.createUser(newUser)
        //send email for activation??
        return UserResponse.of(user.username, user.firstName, user.lastName, user.email);
    }

}
