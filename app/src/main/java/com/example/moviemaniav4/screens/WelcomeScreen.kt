package com.example.moviemaniav4.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviemaniav4.R

@Composable
fun WelcomeScreen(navController: NavHostController, context: Context) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    Box(modifier = Modifier.fillMaxSize()) {
        if (isNetworkAvailable(context)) {
            Image(
                painter = painterResource(id = R.drawable.pocetna_v1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = if (isTablet) 64.dp else 0.dp),
                horizontalAlignment = if (isTablet) Alignment.Start else Alignment.CenterHorizontally,
            ) {
                // Naslov
                Text(
                    text = stringResource(id = R.string.app_title),
                    color = Color.Black,
                    fontSize = if (isTablet) 50.sp else 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                )

                // O aplikaciji
                Text(
                    text = stringResource(id = R.string.app_description),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = if (isTablet) 28.sp else 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Button(
                    onClick = { navController.navigate("Popular movie screen") },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.get_started),
                        fontSize = if (isTablet) 20.sp else 16.sp,
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Internet Connection",
                    fontSize = 24.sp,
                    color = Color.Black
                )
                Button(
                    onClick = {
                        if (isNetworkAvailable(context)) {
                            navController.navigate("Popular movie screen")
                        } else {
                            Toast.makeText(context, "Still No Internet Connection", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

private fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}
