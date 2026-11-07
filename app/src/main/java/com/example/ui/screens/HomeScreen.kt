package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BottomNavTab
import com.example.model.ProductCategory
import com.example.model.ProductItem
import com.example.model.User
import com.example.ui.components.KopiBottomNavBar
import com.example.ui.components.ProductCard
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.KopiGreen
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.White
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(
    currentUser: User?,
    products: List<ProductItem>,
    cart: Map<String, Int>,
    onAddToCart: (ProductItem) -> Unit,
    onIncrementItem: (String) -> Unit,
    onDecrementItem: (String) -> Unit,
    onGoToOrder: () -> Unit,
    activeTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalItems = cart.values.sum()
    val totalPrice = products.sumOf { (cart[it.id] ?: 0) * it.price }
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
    val formattedTotal = "Rp ${formatter.format(totalPrice)}"

    val makananList = products.filter { it.category == ProductCategory.MAKANAN }

    val minumanList = products.filter { it.category == ProductCategory.MINUMAN }

    Scaffold(
        bottomBar = {
            KopiBottomNavBar(
                selectedTab = activeTab,
                onTabSelected = onTabSelected
            )
        },
        containerColor = KopiGreen,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .testTag("home_product_list"),
                    contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header: "Halo, [Nama]!"
                    item {
                        val displayName = currentUser?.fullName?.ifBlank { "Tamu" } ?: "Budi"
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Halo, $displayName!",
                                fontFamily = LibreBaskervilleFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = White,
                                modifier = Modifier.testTag("home_user_greeting")
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Mau ngopi dan nyemil apa hari ini?",
                                fontSize = 14.sp,
                                color = White.copy(alpha = 0.8f)
                            )
                        }
                    }

                    // Section 1: "Makanan" (Berurutan: Donat Cokelat, Sourdough Loaf)
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Makanan",
                            fontFamily = LibreBaskervilleFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = White,
                            modifier = Modifier.testTag("home_section_makanan")
                        )
                    }

                    items(makananList, key = { it.id }) { product ->
                        ProductCard(
                            product = product,
                            quantityInCart = cart[product.id] ?: 0,
                            onAddToCart = { onAddToCart(product) },
                            onIncrement = { onIncrementItem(product.id) },
                            onDecrement = { onDecrementItem(product.id) }
                        )
                    }

                    // Section 2: "Minuman" (Berurutan: Cappuccino, Kopi Susu, Matcha Latte)
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Minuman",
                            fontFamily = LibreBaskervilleFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = White,
                            modifier = Modifier.testTag("home_section_minuman")
                        )
                    }

                    items(minumanList, key = { it.id }) { product ->
                        ProductCard(
                            product = product,
                            quantityInCart = cart[product.id] ?: 0,
                            onAddToCart = { onAddToCart(product) },
                            onIncrement = { onIncrementItem(product.id) },
                            onDecrement = { onDecrementItem(product.id) }
                        )
                    }
                }

                // Multi-product bottom order bar
                if (totalItems > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(8.dp, RoundedCornerShape(18.dp))
                                .clip(RoundedCornerShape(18.dp))
                                .background(DarkNeutral)
                                .clickable(onClick = onGoToOrder)
                                .padding(horizontal = 18.dp, vertical = 14.dp)
                                .testTag("home_cart_summary_bar"),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(InteractiveAccent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingBag,
                                        contentDescription = "Keranjang",
                                        tint = White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "$totalItems Produk Dipilih",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = White
                                    )
                                    Text(
                                        text = formattedTotal,
                                        fontSize = 13.sp,
                                        color = InteractiveAccent,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Lihat Order",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Lanjut",
                                    tint = White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
