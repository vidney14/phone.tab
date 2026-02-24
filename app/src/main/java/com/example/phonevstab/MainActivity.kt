package com.example.phonevstab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.phonevstab.ui.theme.PhoneVStabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhoneVStabTheme {
                AdaptiveScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveScreen() {
    var selectedItem by remember { mutableStateOf("Item 1") }
    // Reduced to 8 items to improve UI look on phone
    val items = remember { List(8) { "Item ${it + 1}" } }

    Scaffold(
        topBar = {
            // M3 Component 1: CenterAlignedTopAppBar
            CenterAlignedTopAppBar(
                title = { Text("Adaptive Layout") }
            )
        },
        floatingActionButton = {
            // M3 Component 2: FloatingActionButton
            FloatingActionButton(onClick = { /* Action */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Determine layout based on width
            val isWide = this.maxWidth >= 600.dp

            if (isWide) {
                // WIDE SCREEN (Tablet/Landscape)
                Row(modifier = Modifier.fillMaxSize()) {
                    // M3 Component 3: NavigationRail
                    NavigationRail(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        NavigationRailItem(
                            selected = true,
                            onClick = {},
                            icon = { Icon(Icons.Default.Home, "Home") },
                            label = { Text("Home") }
                        )
                        NavigationRailItem(
                            selected = false,
                            onClick = {},
                            icon = { Icon(Icons.Default.Settings, "Settings") },
                            label = { Text("Settings") }
                        )
                    }

                    // Left pane: navigation/options list (Column)
                    Column(
                        modifier = Modifier
                            .weight(1f) // weight() used to allocate space
                            .fillMaxHeight() // fillMaxHeight() used appropriately
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Options",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                        // Demonstrate LazyColumn
                        LazyColumn {
                            items(items) { item ->
                                // M3 Component 4: ListItem
                                ListItem(
                                    headlineContent = { Text(item) },
                                    modifier = Modifier.fillMaxWidth(),
                                    trailingContent = {
                                        // M3 Component 5: RadioButton
                                        RadioButton(
                                            selected = selectedItem == item,
                                            onClick = { selectedItem = item }
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // Right Pane: detail content (Box + Column mixed)
                    Box(
                        modifier = Modifier
                            .weight(2f) // weight() used to allocate space
                            .fillMaxHeight() // fillMaxHeight() used appropriately
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // M3 Component 6: ElevatedCard
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            ) {
                                Column(modifier = Modifier.padding(24.dp)) {
                                    Text(
                                        text = "Detail View",
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "You selected: $selectedItem",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                            // M3 Component 7: Button
                            Button(onClick = { /* Action */ }) {
                                Icon(Icons.Default.Info, contentDescription = null)
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text("View More")
                            }
                        }
                    }
                }
            } else {
                // NARROW SCREEN (Phone)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Selection List",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    // LazyColumn on phone layout
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(items) { item ->
                            ListItem(
                                headlineContent = { Text(item) },
                                supportingContent = { Text("Sub-details for $item") },
                                modifier = Modifier.fillMaxWidth(),
                                trailingContent = {
                                    Button(onClick = { selectedItem = item }) {
                                        Text("Select")
                                    }
                                }
                            )
                        }
                    }
                    
                    // M3 Component 8: HorizontalDivider
                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                    
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Currently selected item:", style = MaterialTheme.typography.labelLarge)
                            Text(text = selectedItem, style = MaterialTheme.typography.headlineSmall)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Phone Layout", widthDp = 400)
@Composable
fun PhonePreview() {
    PhoneVStabTheme {
        AdaptiveScreen()
    }
}

@Preview(showBackground = true, name = "Tablet Layout", widthDp = 900)
@Composable
fun TabletPreview() {
    PhoneVStabTheme {
        AdaptiveScreen()
    }
}
