package com.example.smartbudget.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartbudget.domain.model.Category
import com.example.smartbudget.ui.theme.CategoryColors

@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val color = CategoryColors[category.name] ?: MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) color else color.copy(alpha = 0.15f)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${category.icon} ${category.name}",
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) Color.White else color
        )
    }
}