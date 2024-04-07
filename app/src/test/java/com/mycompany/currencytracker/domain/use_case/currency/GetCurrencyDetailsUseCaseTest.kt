package com.mycompany.currencytracker.domain.use_case.currency

//class GetCurrencyDetailsUseCaseTest {
//    private val mockkCurrenciesRepository = mockk<CurrenciesRepository>()
//    private lateinit var getCurrencyDetailsUseCase: GetCurrencyDetailsUseCase
//
//    @Before
//    fun setup() {
//        getCurrencyDetailsUseCase = GetCurrencyDetailsUseCase(mockkCurrenciesRepository)
//    }
//    @Test
//    fun `invoke returns success when repository fetches data successfully`() = runTest {
//        val fakeCurrencyDto = CurrencyDto(1, "USD", 1234567890L, 1.0, "Dollar", 0.1, 0.2, 0.3)
//        val expected = fakeCurrencyDto.toCurrency()
//        coEvery { mockkCurrenciesRepository.getLatestBySymbol(any()) } returns fakeCurrencyDto
//
//        val res = getCurrencyDetailsUseCase.invoke("USD").toList()
//
//        assert(res[0] is Resource.Loading)
//        assert(res[1].data == expected)
//    }
//
//    @Test
//    fun `invoke returns IOException when repository fetches data`() = runTest {
//        coEvery { mockkCurrenciesRepository.getLatestBySymbol(any()) } throws IOException()
//
//        val res = getCurrencyDetailsUseCase.invoke("USD").toList()
//
//        assert(res[0] is Resource.Loading)
//        assert(res[1] is Resource.Error)
//    }
//
//    @Test
//    fun `invoke returns 404 when repository fetches data`() = runTest {
//        coEvery { mockkCurrenciesRepository.getLatestBySymbol(any()) } throws HttpException(Response.error<Any>(404, ResponseBody.create(null, "")))
//
//        val res = getCurrencyDetailsUseCase.invoke("USD").toList()
//
//        assert(res[0] is Resource.Loading)
//        assert(res[1].message == "404")
//    }
//
//
//}