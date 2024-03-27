package com.mycompany.currencytracker.common

import gitignore.APIConfig.IMAGE_URL
import gitignore.APIConfig.REST_API_URL

object Constants {
    const val CURRENCY_TRACKER_REST_API_URL = REST_API_URL
    const val image_url = IMAGE_URL


    const val PARAM_CURRENCY_ID = "currencyId"
    const val PARAM_COIN_ID = "coinId"

    const val home = "home"
    const val search = "search"
    const val favorite = "favorite"
    const val notification_screen = "notification_screen"
    const val setting_screen = "setting_screen"
    const val my_account_screen = "my_account_screen"
    const val calculator_screen = "calculator_screen"
    const val select_main_currency_screen = "select_main_currency_screen"
    const val fiat_detail_screen = "fiat_detail_screen"
    const val coin_detail_screen = "coin_detail_screen"
}