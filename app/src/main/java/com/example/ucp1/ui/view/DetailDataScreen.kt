package com.example.ucp1.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp1.model.TravelPlan

/**
 * Halaman Kedua (Screen 2 - Detail Data):
 * Menampilkan:
 * 1. Judul halaman ("detail data").
 * 2. Card besar ("semua data yang di parsing") yang berisi ringkasan perjalanan:
 *    - Nama Pengguna
 *    - Alamat / Lokasi
 *    - Origin (Kota Asal)
 *    - Tanggal Departure
 *    - Tanggal Arrival
 *    - Pilihan Transportasi
 * 3. Tombol Kembali ("kembali") untuk navigasi kembali ke halaman utama.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDataScreen(
    travelPlan: TravelPlan,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "detail data",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Judul Halaman Sesuai Sketsa: "detail data"
                Text(
                    text = "detail data",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                // ---------------------------------------------------------
                // KOTAK BESAR: "semua data yang di parsing"
                // ---------------------------------------------------------
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Ringkasan Informasi Perjalanan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        // 1. Data Profil yang Diparsing: Nama
                        ItemDataRow(
                            label = "Nama Pengguna",
                            value = travelPlan.nama
                        )

                        // 2. Data Profil yang Diparsing: Lokasi / Alamat
                        ItemDataRow(
                            label = "Lokasi Tempat Tinggal",
                            value = travelPlan.alamat
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        // 3. Data Formulir: Origin
                        ItemDataRow(
                            label = "Kota Asal (Origin)",
                            value = travelPlan.origin
                        )

                        // 4. Data Formulir: Departure
                        ItemDataRow(
                            label = "Tanggal Keberangkatan",
                            value = travelPlan.departure
                        )

                        // 5. Data Formulir: Arrival
                        ItemDataRow(
                            label = "Tanggal Kedatangan",
                            value = travelPlan.arrival
                        )

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        // 6. Data Formulir: Transportasi Terpilih
                        ItemDataRow(
                            label = "Jenis Transportasi",
                            value = travelPlan.transport
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------------------------------------------------
            // TOMBOL KEMBALI
            // ---------------------------------------------------------
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "kembali",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Komponen pembantu untuk menampilkan baris data berupa Label dan Nilai dengan rapi.
 */
@Composable
private fun ItemDataRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = if (value.isNotBlank()) value else "-",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}