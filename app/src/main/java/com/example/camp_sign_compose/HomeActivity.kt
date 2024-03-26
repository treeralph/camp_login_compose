package com.example.camp_sign_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.camp_sign_compose.ui.theme.Camp_sign_composeTheme
import kotlin.random.Random

class HomeActivity : ComponentActivity() {

    // For Editable
    val idText = MutableLiveData("")
    val nameText = MutableLiveData("")
    val ageText = MutableLiveData("")
    val mbtiText = MutableLiveData("")

    private val bannerImages = listOf(
        R.drawable.mango,
        R.drawable.user_mango_1,
        R.drawable.user_mango_2,
        R.drawable.user_mango_3,
        R.drawable.user_mango_4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val randomImageId = bannerImages[Random.nextInt(0, 5)]
        idText.value = intent.getStringExtra(SignInActivity.ID_EXTRA)
        nameText.value = MY_NAME
        ageText.value = MY_AGE
        mbtiText.value = MY_MBTI

        setContent {
            Camp_sign_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeActivityScaffold(
                        idText = idText.observeAsState(initial = "").value,
                        nameText = nameText.observeAsState(initial = "").value,
                        ageText = ageText.observeAsState(initial = "").value,
                        mbtiText = mbtiText.observeAsState(initial = "").value,
                        onMbtiTextChange = { mbtiText.value = it },
                        bannerImageResourceId = randomImageId,
                        buttonClickListener = { finish() }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivityScaffold(
    idText: String,
    onIdTextChange: (String) -> Unit = {},
    nameText: String,
    onNameTextChange: (String) -> Unit = {},
    ageText: String,
    onAgeTextChange: (String) -> Unit = {},
    mbtiText: String,
    onMbtiTextChange: (String) -> Unit = {},
    bannerImageResourceId: Int,
    buttonClickListener: () -> Unit,
) {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                Image(
                    modifier = Modifier
                        .size(HOME_BANNER_IMAGE_SIZE.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = bannerImageResourceId),
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoRow(title = stringResource(id = R.string.home_id_indc), description = idText)
                InfoRow(title = stringResource(id = R.string.home_name_indc), description = nameText)
                InfoRow(title = stringResource(id = R.string.home_age_indc), description = ageText)
                InfoRow(title = stringResource(id = R.string.home_mbti_indc), description = mbtiText)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = buttonClickListener
                ) {
                    Text(
                        text = stringResource(id = R.string.exit_button_text),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
) {
    Text(
        modifier = modifier.padding(12.dp),
        text = "$title: $description",
        style = MaterialTheme.typography.labelSmall
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Camp_sign_composeTheme {

    }
}