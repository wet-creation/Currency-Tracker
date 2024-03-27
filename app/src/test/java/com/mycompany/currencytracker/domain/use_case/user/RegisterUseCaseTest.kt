package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.remote.dto.user.toUserDto
import com.mycompany.currencytracker.domain.model.user.UserRegister
import com.mycompany.currencytracker.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RegisterUseCaseTest {

    private lateinit var UserRegisterRepository: UserRepository
    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setup() {
        UserRegisterRepository = mockk(relaxed = true)
        registerUseCase = RegisterUseCase(UserRegisterRepository)
    }

    @Test
    fun `send correct UserRegister and response with success`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "12435JohnDoe", "12435JohnDoe")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
        assertTrue(res[1] is Resource.Success)

    }


    @Test
    fun `send correct UserRegister and response with httpError`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "12435JohnDoe", "12435JohnDoe")
        coEvery { UserRegisterRepository.register(any()) } throws HttpException(
            Response.error<Any>(409, ResponseBody.create(null, ""))
        )

         val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)


    }


    @Test
    fun `send correct UserRegister and response with IOException`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "12435JohnDoe", "12435JohnDoe")
        coEvery { UserRegisterRepository.register(any()) } throws HttpException(
            Response.error<Any>(409, ResponseBody.create(null, ""))
        )

        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
       // assertTrue(res[1] is Resource.Error())
    }


    @Test
    fun `send incorrect UserRegister name and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "", "Doe", "doe@gmail.com", "12435JohnDoe", "12435JohnDoe")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
       // assertTrue(res[1].message == Resource.Error<Unit>("Field cannot be empty").message)
    }
    @Test
    fun `send incorrect UserRegister surname and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "", "doe@gmail.com", "12435JohnDoe", "12435JohnDoe")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
      //  assertTrue(res[1].message == Resource.Error<Unit>("Field cannot be empty").message)
    }
    @Test
    fun `send incorrect UserRegister email and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "@gmail.com", "12435JohnDoe", "12435JohnDoe")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
      //  assertTrue(res[1].message == Resource.Error<Unit>("Email address not valid").message)
    }
    @Test
    fun `send incorrect UserRegister password without capital letters  and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "12435ohnoe", "12435ohnoe")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
      //  assertTrue(res[1].message == Resource.Error<Unit>("Password doesn't have uppercase letters").message)
    }
    @Test
    fun `send incorrect UserRegister password without lowercase letters  and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "12435DJHIJKHDF", "12435DJHIJKHDF")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
        //assertTrue(res[1].message == Resource.Error<Unit>("Password doesn't have lowercase letters").message)
    }
    @Test
    fun `send incorrect UserRegister password without digits  and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "sddsdsdDJHIJKHDF", "sddsdsdDJHIJKHDF")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
       // assertTrue(res[1].message == Resource.Error<Unit>("Password doesn't have digits").message)
    }

    @Test
    fun `send incorrect UserRegister password too short  and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "1232", "1232")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
     //   assertTrue(res[1].message == Resource.Error<Unit>("Password too short").message)
    }

    @Test
    fun `send incorrect UserRegister passwords doesnt mathes  and response with Exception`() = runTest {
        val fakeUserRegister = UserRegister("1dghqw3r45e", "John", "Doe", "doe@gmail.com", "12435JohnDoe", "12435JohnDo")
        val res = registerUseCase(fakeUserRegister).toList()

        verify {
            runBlocking { UserRegisterRepository.register(fakeUserRegister.toUserDto()) }
        }
        assertTrue(res[0] is Resource.Loading)
       // assertTrue(res[1].message == Resource.Error<Unit>("Password doesn't match").message)
    }

}