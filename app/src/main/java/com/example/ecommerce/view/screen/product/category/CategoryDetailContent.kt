package com.example.ecommerce.view.screen.product.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerce.R
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.view.component.responsiveTextSize
import com.example.ecommerce.view.screen.home.ProductItemView
import com.example.ecommerce.view.theme.darker_grey


@Composable
fun CardProductDetailContent(
    modifier : Modifier = Modifier,
    onProductClick: (Int) -> Unit,
    productItem : List<Product>
) {

        Column(
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 4.dp)
        ) {

            Text(
                modifier = Modifier.padding(8.dp),
                text =  stringResource(R.string.product),
                style =
                    TextStyle.Default
                        .responsiveTextSize(baseFontSize = 4f)
                        .copy(
                            textAlign = TextAlign.Center,
                        ),
            )


            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(items = productItem) { _, product ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        colors =
                            CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                    ) {
                        ProductItemView(
                            image = product.imageUrl,
                            title = product.title,
                            price = product.price,
                            rating = product.ratingDetails.rate,
                            count = product.ratingDetails.count,
                            onProductClick = {
                                onProductClick(product.id)
                            },
                        )
                    }
                }
            }
        }

}