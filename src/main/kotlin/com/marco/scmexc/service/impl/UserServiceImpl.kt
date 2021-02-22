package com.marco.scmexc.service.impl

import com.marco.scmexc.models.domain.Role
import com.marco.scmexc.models.domain.SmxUser
import com.marco.scmexc.models.dto.UserDto
import com.marco.scmexc.models.exceptions.user.UserAlreadyExistsException
import com.marco.scmexc.models.exceptions.user.UserNotFoundException
import com.marco.scmexc.repository.SmxUserRepository
import com.marco.scmexc.security.UserPrincipal
import com.marco.scmexc.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.SortDefault
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val repository: SmxUserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

//TODO IMPLEMENT EDIT OF USER BY ADMIN

    //    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    override fun createUser(newUser: UserDto): SmxUser {
        repository.findSmxUserByEmailOrUsername(newUser.email, newUser.username).ifPresent { throw UserAlreadyExistsException() }
        var role = Role.BASIC
        if (newUser.role != null) {
            role = Role.valueOf(newUser.role)
        }
        val encryptedPassword = passwordEncoder.encode(newUser.password)
        val user = SmxUser()
        user.username = newUser.username
        user.email = newUser.email
        user.password = encryptedPassword
        user.firstName = newUser.firstName
        user.lastName = newUser.lastName
        user.role = role

        return repository.save(user)
    }

    override fun getUserByEmail(email: String): SmxUser {
        return repository.findSmxUserByEmail(email).orElseThrow(::UserNotFoundException)
    }

    override fun saveUser(user: SmxUser): SmxUser {
        return repository.save(user)
    }

    override fun getAllUsersPaged(pageable:Pageable, searchQuery: String): Page<SmxUser> {
        return when {
            searchQuery.isEmpty() -> repository.findAll(pageable)
            else -> repository.getPagedUsersByNameContains(searchQuery, pageable)
        }
    }

    override fun modifyUser(userPrincipal: UserPrincipal, modifiedUser: UserDto): SmxUser {
        val user = getUserById(userPrincipal.id)
        user.email = modifiedUser.email
        user.firstName = modifiedUser.firstName
        user.lastName = modifiedUser.lastName
        return repository.save(user)
    }

    override fun modifyUser(userId: Long, modifiedUser: UserDto): SmxUser {
        val user = getUserById(userId)
        user.email = modifiedUser.email
        user.firstName = modifiedUser.firstName
        user.lastName = modifiedUser.lastName
        user.role = modifiedUser.role?.let { Role.valueOf(it) }
        return repository.save(user)
    }

    override fun getUserById(id: Long): SmxUser = repository.findById(id).orElseThrow(::UserNotFoundException)

}
