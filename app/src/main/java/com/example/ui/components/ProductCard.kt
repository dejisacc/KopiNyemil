package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProductItem
import com.example.ui.theme.CardCyan
import com.example.ui.theme.DarkNeutral
import com.example.ui.theme.InteractiveAccent
import com.example.ui.theme.LibreBaskervilleFontFamily
import com.example.ui.theme.TextDarkSecondary
import com.example.ui.theme.White

@Composable
fun ProductCard(
    product: ProductItem,
    quantityInCart: Int,
    onAddToCart: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardCyan
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("product_card_${product.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Photo / Thumbnail (Left)
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE8D5C4))
                    .clickable(onClick = {
                        if (quantityInCart == 0) onAddToCart() else onIncrement()
                    }),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.drawableRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(88.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details (Right)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.name,
                    fontFamily = LibreBaskervilleFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = DarkNeutral
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.description,
                    fontSize = 13.sp,
                    color = TextDarkSecondary,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.formattedPrice,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = DarkNeutral
                    )

                    if (quantityInCart == 0) {
                        // Quick Add button
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(InteractiveAccent)
                                .clickable(onClick = onAddToCart)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("btn_add_${product.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AddShoppingCart,
                                    contentDescription = "Pesan",
                                    tint = White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Pesan",
                                    color = White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        // Stepper for multiple product quantities
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkNeutral)
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            IconButton(
                                onClick = onDecrement,
                                modifier = Modifier
                                    .size(26.dp)
                                    .testTag("btn_dec_${product.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Kurangi",
                                    tint = White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Text(
                                text = "$quantityInCart",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = White,
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .testTag("qty_${product.id}")
                            )
                            IconButton(
                                onClick = onIncrement,
                                modifier = Modifier
                                    .size(26.dp)
                                    .testTag("btn_inc_${product.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Tambah",
                                    tint = White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
