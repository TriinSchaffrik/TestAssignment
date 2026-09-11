package ee.topgravity.android.testassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ee.topgravity.android.testassignment.ui.navigation.AppNavigation
import ee.topgravity.android.testassignment.ui.theme.TestAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAssignmentTheme {
                AppNavigation()
            }
        }
    }
}