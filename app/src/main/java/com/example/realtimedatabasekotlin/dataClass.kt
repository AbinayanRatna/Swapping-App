package com.example.realtimedatabasekotlin

data class categoryMain(
    var id: Int? = null,
    var name: String? = null,

    )

data class productClass(
    var id: String? = "",
    var image: String? = "",
    var nameOfProduct: String? = "",
    var priceOfProduct: String? = "",
    var districtOfProduct: String? = "",
    var dateOfProduct: String? = "",
)

data class swapClass(
    var swapId: String? = "",
    var image: String? = "",
    var productImage: String? = "", //swap
    var productName: String? = "", //swap
    var productPrice: String? = "",  //swap
    var productAddress: String? = "",  //swap
    var nameOfProduct: String? = "",
    var priceOfProduct: String? = "",
    var districtOfProduct: String? = "",
    var dateOfProduct: String? = "",
)
