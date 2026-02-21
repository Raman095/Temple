package com.example.temple.view.medicineScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.temple.viewModels.MedicineViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.temple.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailScreen(
    navController: NavController,
    medicineViewModel: MedicineViewModel
) {
    val medicineDetail by medicineViewModel.selectedMedicine.collectAsStateWithLifecycle()

    medicineDetail ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf1f3f5))
            .statusBarsPadding()
    ) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .padding(start = 20.dp, end = 15.dp)
                )
            },
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 75.dp)
                ) {
                    Text(
                        text = "Medicine Detail",
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
        LazyColumn {

            item { MedicineName(name = medicineDetail!!.medicineName) }

            item { CategorySection(category = medicineDetail!!.category) }

            item { DescriptionSection(description = medicineDetail!!.description) }

            item { UsesSection(uses = medicineDetail!!.uses) }

            item {
                HowToUseSection(
                    howToUse = medicineDetail!!.howToUse,
                    accentColor = Color(0xFF06b6d4)
                )
            }

            item { SideEffectsSection(sideEffects = medicineDetail!!.sideEffects) }

            item { PrecautionsSection( precautions = medicineDetail!!.precautions) }

            item { InteractionsSection( interactions = medicineDetail!!.interactions) }

            item { StorageSection( storage = medicineDetail!!.storageInstructions ) }

            item { WarningSection( warning = medicineDetail!!.warnings ) }
        }
    }
}

// Medicine name
@Composable
fun MedicineName(name: String) {
    Text(
        text = name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 12.dp),
        color = Color.Black,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )
}

// Category
@Composable
fun CategorySection(category: List<String>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(category) {
            CategoryCard(it)
        }
    }
}

@Composable
fun CategoryCard(category: String) {
    Column(
        modifier = Modifier
            .wrapContentWidth()  // auto adjust width according to content
            .background(
                Color(0xFFe0f3ef),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
    ) {
        Text(
            text = category,
            color = Color(0xFF10b981),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Description
@Composable
fun DescriptionSection(description: String) {
    Card(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "DESCRIPTION ",
                color = Color(0xFF16ba84),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                color = Color(0xFF536173),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// Uses
@Composable
fun UsesSection(uses: List<String>) {
    Text(
        text = "USES",
        color = Color(0xFF06b6d4),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp)
    )
    uses.forEach {
        UsesUI(it)
    }
}

@Composable
fun UsesUI(uses: String) {
    Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(7.dp, 7.dp)
                .background(Color(0xFF10b981), shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = uses,
            color = Color(0xFF364357),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

// How to Use
@Composable
fun HowToUseSection(
    howToUse: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {

    val shape = RoundedCornerShape(20.dp)

    Box(
        modifier = modifier
            .padding(horizontal = 18.dp, vertical = 30.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Accent Strip         // here the strip is straight line, in UI it is curved line because of the box clip,
        // the parent box is clipped, so the strip will also have to follow the curve of box.
        Box(
            modifier = Modifier
                .matchParentSize()     // this thing here -> column me hamne hamara content likha hai, to vo content kitna bhi bada ho skta hai
            // to box uss hisab se bada, chota hoga, to matchParentSize box ki height ko measure karta hai only when sare content
            // box ke andar aa gya hai, that will be the final height, and tab jake strip ke height decide hoti hai
            // phir, strip ne apni height leli, par hamara box curve hai, to hamari line ko bhi curve hona padta hai.
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(accentColor)
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = accentColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "HOW TO USE",
                    color = accentColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = howToUse,
                color = Color(0xFF364357),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// Side effects
@Composable
fun SideEffectsSection(sideEffects: List<String>) {
    Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = Color(0xFF06b6d4)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "SIDE EFFECTS",
            color = Color(0xFF06b6d4),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
    sideEffects.forEach {
        SideEffectsUI(it)
    }
}

@Composable
fun SideEffectsUI(sideEffects: String) {
    Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(7.dp, 7.dp)
                .background(Color(0xFF10b981), shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = sideEffects,
            color = Color(0xFF364357),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

// Precautions
@Composable
fun PrecautionsSection(precautions: List<String>) {
    Text(
        text = "PRECAUTIONS",
        color = Color(0xFF06b6d4),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(start = 18.dp, top = 30.dp)
    )
    precautions.forEach {
        PrecautionUI(it)
    }
}

@Composable
fun PrecautionUI(precaution: String) {
    Row(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(7.dp, 7.dp)
                .background(Color(0xFF10b981), shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = precaution,
            color = Color(0xFF364357),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

// Interactions
@Composable
fun InteractionsSection(interactions: List<String>) {
    Text(
        text = "INTERACTION",
        color = Color(0xFF06b6d4),
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(start = 18.dp, top = 30.dp)
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(interactions) {
            InteractionCard(it)
        }
    }
}

@Composable
fun InteractionCard(interaction: String) {
    Column(
        modifier = Modifier
            .wrapContentWidth()  // auto adjust width according to content
            .background(
                Color(0xFFe0f3ef),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
    ) {
        Text(
            text = interaction,
            color = Color(0xFF10b981),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Storage
@Composable
fun StorageSection(storage: String) {
    Card(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp),
        colors = CardDefaults.cardColors( containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.low_temperature),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Storage",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = storage,
                color = Color(0xFF536173),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// Warnings
@Composable
fun WarningSection(warning: String) {
    Card(
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 30.dp, bottom = 12.dp),
        colors = CardDefaults.cardColors( containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFF06b6d4),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Warnings",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = warning,
                color = Color(0xFF536173),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}