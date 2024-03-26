package com.example.camp_sign_compose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.camp_sign_compose.ui.composable.UserTextField
import com.example.camp_sign_compose.ui.theme.Camp_sign_composeTheme
import com.example.camp_sign_compose.ui.theme.PlaceHolderTextColor

class SignUpActivity : ComponentActivity() {

    companion object {
        const val ACTIVITY_RESULT_ID = "id"
        const val ACTIVITY_RESULT_PW = "pw"
    }

    private val nameText = MutableLiveData("")
    private val idText = MutableLiveData("")
    private val pwText = MutableLiveData("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Camp_sign_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpActivityScaffold(
                        nameText = nameText.observeAsState(initial = "").value,
                        onNameTextChange = { nameText.value = it },
                        idText = idText.observeAsState(initial = "").value,
                        onIdTextChange = { idText.value = it },
                        pwText = pwText.observeAsState(initial = "").value,
                        onPwTextChange = { pwText.value = it },
                        signUpButtonClickListener = {
                            if (
                                inputValidation(
                                    nameText.value ?: "",
                                    idText.value ?: "",
                                    pwText.value ?: ""
                                )
                            ) {
                                setResult(RESULT_OK,
                                    Intent().apply {
                                        putExtra(ACTIVITY_RESULT_ID, idText.value)
                                        putExtra(ACTIVITY_RESULT_PW, pwText.value)
                                    }
                                )
                                finish()
                            } else Toast
                                .makeText(
                                    this,
                                    "입력되지 않은 정보가 있습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpActivityScaffold(
    nameText: String,
    onNameTextChange: (String) -> Unit,
    idText: String,
    onIdTextChange: (String) -> Unit,
    pwText: String,
    onPwTextChange: (String) -> Unit,
    signUpButtonClickListener: () -> Unit = {},
) {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .size(BANNER_IMAGE_SIZE.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.mango),
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(DEFAULT_MARGIN.dp)
            ) {
                Text(
                    modifier = Modifier.padding(SIGN_IN_INDIC_MARGIN.dp),
                    text = stringResource(id = R.string.name_text_indic),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
                UserTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nameText,
                    onValueChange = onNameTextChange,
                    textStyle = MaterialTheme.typography.labelLarge,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.name_edit_text_indic),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Light,
                            color = PlaceHolderTextColor
                        )
                    }
                )
                Spacer(modifier = Modifier.size(DEFAULT_MARGIN.dp))
                Text(
                    modifier = Modifier.padding(SIGN_IN_INDIC_MARGIN.dp),
                    text = stringResource(id = R.string.id_text_indic),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
                UserTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = idText,
                    onValueChange = onIdTextChange,
                    textStyle = MaterialTheme.typography.labelLarge,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.id_edit_text_indic),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Light,
                            color = PlaceHolderTextColor
                        )
                    }
                )
                Spacer(modifier = Modifier.size(DEFAULT_MARGIN.dp))
                Text(
                    modifier = Modifier.padding(SIGN_IN_INDIC_MARGIN.dp),
                    text = stringResource(id = R.string.pw_text_indic),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
                UserTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = pwText,
                    onValueChange = onPwTextChange,
                    textStyle = MaterialTheme.typography.labelLarge,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.pw_edit_text_indic),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Light,
                            color = PlaceHolderTextColor
                        )
                    }
                )
                Spacer(modifier = Modifier.size(BOTTOM_THRESHOLD.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = signUpButtonClickListener
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up_button_text_activity_sign_in),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    Camp_sign_composeTheme {
        var nameText by remember { mutableStateOf("") }
        var idText by remember { mutableStateOf("") }
        var pwText by remember { mutableStateOf("") }
        SignUpActivityScaffold(
            nameText = nameText,
            onNameTextChange = { nameText = it },
            idText = idText,
            onIdTextChange = { idText = it },
            pwText = pwText,
            onPwTextChange = { pwText = it },
        )
    }
}