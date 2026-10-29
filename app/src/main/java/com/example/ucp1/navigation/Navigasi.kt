package com.example.ucp1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp1.model.TravelPlan
import com.example.ucp1.ui.view.DetailDataScreen
import com.example.ucp1.ui.view.PlanYourAdventureScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Enum class untuk mendefinisikan rute halaman pada aplikasi.
 */
enum class Halaman {
    Form,
    Detail
}

/**
 * NavHost utama aplikasi Travel Planner yang mengelola alur navigasi
 * dan mekanisme parsing data antarhalaman menggunakan Navigation Compose.
 */
@Composable
fun TravelPlannerApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Halaman.Form.name
    ) {
        // -------------------------------------------------------------
        // 1. RUTE HALAMAN FORMULIR ("Plan your adventure")
        // -------------------------------------------------------------
        composable(route = Halaman.Form.name) {
            PlanYourAdventureScreen(
                onSubmit = { plan ->
                    // Encode argumen string agar aman terhadap spasi, tanda miring (tanggal), dan karakter khusus
                    val encNama = URLEncoder.encode(plan.nama, StandardCharsets.UTF_8.toString())
                    val encAlamat = URLEncoder.encode(plan.alamat, StandardCharsets.UTF_8.toString())
                    val encOrigin = URLEncoder.encode(plan.origin, StandardCharsets.UTF_8.toString())
                    val encDeparture = URLEncoder.encode(plan.departure, StandardCharsets.UTF_8.toString())
                    val encArrival = URLEncoder.encode(plan.arrival, StandardCharsets.UTF_8.toString())
                    val encTransport = URLEncoder.encode(plan.transport, StandardCharsets.UTF_8.toString())

                    // Navigasi ke halaman detail dengan passing data melalui argumen rute
                    navController.navigate(
                        "${Halaman.Detail.name}/$encNama/$encAlamat/$encOrigin/$encDeparture/$encArrival/$encTransport"
                    )
                }
            )
        }

        // -------------------------------------------------------------
        // 2. RUTE HALAMAN DETAIL ("detail data") DENGAN ARGUMEN
        // -------------------------------------------------------------
        composable(
            route = "${Halaman.Detail.name}/{nama}/{alamat}/{origin}/{departure}/{arrival}/{transport}",
            arguments = listOf(
                navArgument("nama") { type = NavType.StringType },
                navArgument("alamat") { type = NavType.StringType },
                navArgument("origin") { type = NavType.StringType },
                navArgument("departure") { type = NavType.StringType },
                navArgument("arrival") { type = NavType.StringType },
                navArgument("transport") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Mengambil dan mendecode seluruh data yang dikirim dari halaman form
            val rawNama = backStackEntry.arguments?.getString("nama") ?: ""
            val rawAlamat = backStackEntry.arguments?.getString("alamat") ?: ""
            val rawOrigin = backStackEntry.arguments?.getString("origin") ?: ""
            val rawDeparture = backStackEntry.arguments?.getString("departure") ?: ""
            val rawArrival = backStackEntry.arguments?.getString("arrival") ?: ""
            val rawTransport = backStackEntry.arguments?.getString("transport") ?: ""

            val plan = TravelPlan(
                nama = URLDecoder.decode(rawNama, StandardCharsets.UTF_8.toString()),
                alamat = URLDecoder.decode(rawAlamat, StandardCharsets.UTF_8.toString()),
                origin = URLDecoder.decode(rawOrigin, StandardCharsets.UTF_8.toString()),
                departure = URLDecoder.decode(rawDeparture, StandardCharsets.UTF_8.toString()),
                arrival = URLDecoder.decode(rawArrival, StandardCharsets.UTF_8.toString()),
                transport = URLDecoder.decode(rawTransport, StandardCharsets.UTF_8.toString())
            )

            DetailDataScreen(
                travelPlan = plan,
                onBack = {
                    // Tombol kembali: popBackStack untuk kembali ke halaman formulir
                    navController.popBackStack()
                }
            )
        }
    }
}