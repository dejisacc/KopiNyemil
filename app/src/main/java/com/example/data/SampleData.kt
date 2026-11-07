package com.example.data

import com.example.R
import com.example.model.ProductCategory
import com.example.model.ProductItem
import com.example.model.User

object SampleData {
    val initialProducts: List<ProductItem> = listOf(
        ProductItem(
            id = "prod_2",
            name = "Donat Cokelat",
            description = "Donat empuk dengan glaze cokelat",
            price = 12000,
            drawableRes = R.drawable.donat_coklat,
            category = ProductCategory.MAKANAN
        ),
        ProductItem(
            id = "prod_3",
            name = "Sourdough Loaf",
            description = "Roti fermentasi alami",
            price = 35000,
            drawableRes = R.drawable.sourdough_loaf,
            category = ProductCategory.MAKANAN
        ),

        ProductItem(
            id = "prod_4",
            name = "Cappuccino",
            description = "Espresso dengan foam susu lembut",
            price = 25000,
            drawableRes = R.drawable.cappuccino,
            category = ProductCategory.MINUMAN
        ),
        ProductItem(
            id = "prod_1",
            name = "Kopi Susu Gula Aren",
            description = "Espresso, susu, gula aren",
            price = 22000,
            drawableRes = R.drawable.kopi_aren,
            category = ProductCategory.MINUMAN
        ),
        ProductItem(
            id = "prod_5",
            name = "Matcha Latte",
            description = "Matcha Jepang dengan susu segar",
            price = 27000,
            drawableRes = R.drawable.matcha_latte,
            category = ProductCategory.MINUMAN
        )
    )

    val defaultUsers: List<User> = listOf(
        User(
            username = "budilagiaje",
            fullName = "Budi Santoso",
            password = "123",
            email = "budi.santoso@email.com",
            phone = "081234567890"
        ),
        User(
            username = "kopilover",
            fullName = "Siti Rahma",
            password = "ngopiheula",
            email = "siti.rahma@email.com",
            phone = "089876543210"
        )
    )
}
