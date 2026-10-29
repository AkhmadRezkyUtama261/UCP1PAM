package com.example.ucp1.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp1.R
import com.example.ucp1.model.TravelPlan

/**
 * Halaman Utama (Screen 1):
 * Menampilkan:
 * 1. Bagian profil pengguna (foto bulat - tidak diparsing, nama, alamat).
 * 2. Garis pembatas horizontal.
 * 3. Formulir perencanaan perjalanan ("Plan your adventure"):
 *    - origin (kota asal keberangkatan)
 *    - departure (tanggal keberangkatan)
 *    - arrival (tanggal kedatangan)
 *    - choose transport (bus, ship, train, plane) - single selection
 * 4. Tombol Submit dengan validasi isian.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanYourAdventureScreen(
    onSubmit: (TravelPlan) -> Unit,
    modifier: Modifier = Modifier
) {
    // -------------------------------------------------------------
    // STATE MANAGEMENT MENGGUNAKAN JETPACK COMPOSE
    // Setiap perubahan nilai input akan langsung direfleksikan ke UI
    // -------------------------------------------------------------
    var nama by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }
    var departure by remember { mutableStateOf("") }
    var arrival by remember { mutableStateOf("") }
    var selectedTransport by remember { mutableStateOf("") }

    // State untuk validasi error
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val transportOptions = listOf("Bus", "Ship", "Train", "Plane")
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Travel Planner",
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---------------------------------------------------------
            // 1. BAGIAN PROFIL PENGGUNA (SESUAI SKETSA GAMBAR)
            // ---------------------------------------------------------
            // Foto Profil Berbentuk Lingkaran (image - tidak diparsing)
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_photo),
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Input / Display: Nama Pengguna
            OutlinedTextField(
                value = nama,
                onValueChange = {
                    nama = it
                    errorMessage = null
                },
                label = { Text("Nama Pengguna") },
                placeholder = { Text("Masukkan nama lengkap Anda") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Input / Display: Alamat / Lokasi Tempat Tinggal
            OutlinedTextField(
                value = alamat,
                onValueChange = {
                    alamat = it
                    errorMessage = null
                },
                label = { Text("Lokasi Tempat Tinggal") },
                placeholder = { Text("Masukkan alamat / kota tempat tinggal") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Garis Pembatas Horizontal (sesuai sketsa garis hitam pemisah)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 18.dp),
                thickness = 1.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // ---------------------------------------------------------
            // 2. FORMULIR "Plan your adventure"
            // ---------------------------------------------------------
            Text(
                text = "Plan your adventure",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                textAlign = TextAlign.Start
            )

            // Input: Origin (Kota Asal Keberangkatan)
            OutlinedTextField(
                value = origin,
                onValueChange = {
                    origin = it
                    errorMessage = null
                },
                label = { Text("Origin") },
                placeholder = { Text("Kota asal keberangkatan") },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Input: Tanggal Departure & Arrival berdampingan (Row)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = departure,
                    onValueChange = {
                        departure = it
                        errorMessage = null
                    },
                    label = { Text("Departure") },
                    placeholder = { Text("Tgl Keberangkatan") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = arrival,
                    onValueChange = {
                        arrival = it
                        errorMessage = null
                    },
                    label = { Text("Arrival") },
                    placeholder = { Text("Tgl Kedatangan") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ---------------------------------------------------------
            // 3. SELEKSI TRANSPORTASI ("choose transport")
            // Sesuai sketsa: Kotak berbingkai dengan judul dan pilihan transportasi
            // ---------------------------------------------------------
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "choose transport",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        textAlign = TextAlign.Center
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        transportOptions.forEach { transport ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .selectable(
                                        selected = (selectedTransport == transport),
                                        onClick = {
                                            selectedTransport = transport
                                            errorMessage = null
                                        }
                                    )
                                    .padding(horizontal = 2.dp)
                            ) {
                                RadioButton(
                                    selected = (selectedTransport == transport),
                                    onClick = {
                                        selectedTransport = transport
                                        errorMessage = null
                                    }
                                )
                                Text(
                                    text = transport.lowercase(),
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedTransport == transport) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------------------------------------------------------
            // 4. PESAN ERROR VALIDASI (JIKA ADA DATA KOSONG)
            // ---------------------------------------------------------
            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    textAlign = TextAlign.Center
                )
            }

            // ---------------------------------------------------------
            // 5. TOMBOL SUBMIT
            // ---------------------------------------------------------
            Button(
                onClick = {
                    // Validasi sederhana: memastikan semua data telah diisi
                    when {
                        nama.isBlank() -> {
                            errorMessage = "Nama pengguna tidak boleh kosong!"
                        }
                        alamat.isBlank() -> {
                            errorMessage = "Lokasi tempat tinggal tidak boleh kosong!"
                        }
                        origin.isBlank() -> {
                            errorMessage = "Kota asal (origin) wajib diisi!"
                        }
                        departure.isBlank() -> {
                            errorMessage = "Tanggal keberangkatan (departure) wajib diisi!"
                        }
                        arrival.isBlank() -> {
                            errorMessage = "Tanggal kedatangan (arrival) wajib diisi!"
                        }
                        selectedTransport.isBlank() -> {
                            errorMessage = "Silakan pilih salah satu jenis transportasi!"
                        }
                        else -> {
                            errorMessage = null
                            val plan = TravelPlan(
                                nama = nama.trim(),
                                alamat = alamat.trim(),
                                origin = origin.trim(),
                                departure = departure.trim(),
                                arrival = arrival.trim(),
                                transport = selectedTransport.trim()
                            )
                            onSubmit(plan)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "submit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}