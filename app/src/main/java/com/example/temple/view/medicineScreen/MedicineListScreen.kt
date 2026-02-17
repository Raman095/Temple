package com.example.temple.view.medicineScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.temple.dataClasses.MedicineDataClass
import com.example.temple.viewModels.MedicineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineListScreen(
    navController: NavController,
    medicineViewModel: MedicineViewModel
) {
    val medicines by medicineViewModel.filteredMedicines.collectAsStateWithLifecycle()
    val search by medicineViewModel.searchQuery.collectAsStateWithLifecycle()
    val categories by medicineViewModel.allCategories.collectAsStateWithLifecycle()
    val selectedCategory by medicineViewModel.selectedCategory.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Medicines",
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
        OutlinedTextField(
            value = search,
            onValueChange = medicineViewModel::onSearchChange,
            placeholder = { Text(text = "Search medicines...", color = Color.Black) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(56.dp)
                .background(
                    color = Color.White
                ),
            shape = RoundedCornerShape(16.dp),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.LightGray,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color.Black
            ),
            singleLine = true
        )
        FilterChips(
            categories = categories,
            selected = selectedCategory,
            onSelected = medicineViewModel::onCategorySelected
        )
        LazyColumn {
            items(medicines) { medicine ->
                MedicineCard(
                    navController,
                    medicine = medicine,
                    medicineViewModel = medicineViewModel
                )
            }
        }
    }
}

@Composable
fun FilterChips(
    categories: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {

        items(categories) { category ->

            FilterChip(
                selected = category == selected,
                onClick = { onSelected(category) },
                label = { Text(category) },
                modifier = Modifier.padding(horizontal = 6.dp)
            )
        }
    }
}


@Composable
fun MedicineCard(
    navController: NavController,
    medicine: MedicineDataClass,
    medicineViewModel: MedicineViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp)
            .clickable {
                medicineViewModel.selectedMedicine(medicine.medicineName)
                navController.navigate("medicine_detail")
            },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(Color(0xFFf3f7fe))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = medicine.medicineName,
                    fontSize = 20.sp,
                    color = Color(0xFF135bec),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = medicine.description,
                    fontSize = 14.sp,
                    color = Color(0xFF686f89),
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF135bec),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )
        }
    }
}

