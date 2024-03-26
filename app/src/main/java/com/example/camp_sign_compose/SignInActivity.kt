package com.example.camp_sign_compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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

class SignInActivity : ComponentActivity() {

    companion object {
        const val ID_EXTRA: String = "ID_EXTRA"
    }

    private val idText = MutableLiveData("")
    private val pwText = MutableLiveData("")

    private val signUpActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultNullable ->
            resultNullable?.let { result ->
                Log.e("TAG", "result: $result")
                if (result.resultCode == RESULT_OK) {
                    result.data?.let {
                        idText.value = it.getStringExtra(SignUpActivity.ACTIVITY_RESULT_ID)
                        pwText.value = it.getStringExtra(SignUpActivity.ACTIVITY_RESULT_PW)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Camp_sign_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignInActivityScaffold(
                        idText = idText.observeAsState(initial = "").value,
                        onIdTextChange = { idText.value = it },
                        pwText = pwText.observeAsState(initial = "").value,
                        onPwTextChange = { pwText.value = it },
                        signInButtonClickListener = {
                            if (inputValidation(idText.value ?: "", pwText.value ?: "")) {
                                startActivity(
                                    Intent(
                                        this@SignInActivity,
                                        HomeActivity::class.java
                                    ).apply {
                                        putExtra(ID_EXTRA, idText.value!!.trim())
                                    }
                                )
                            } else {
                                // toast
                                Toast.makeText(
                                    this, ERROR_MESSAGE_SIGN_IN, Toast.LENGTH_SHORT).show()
                            }
                        },
                        signUpButtonClickListener = {
                            signUpActivityLauncher.launch(
                                Intent(this@SignInActivity, SignUpActivity::class.java)
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInActivityScaffold(
    idText: String,
    onIdTextChange: (String) -> Unit,
    pwText: String,
    onPwTextChange: (String) -> Unit,
    signInButtonClickListener: () -> Unit = {},
    signUpButtonClickListener: () -> Unit = {},
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(4f)
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
                    .weight(6f)
                    .fillMaxWidth()
                    .padding(DEFAULT_MARGIN.dp)
            ) {
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
                    onClick = signInButtonClickListener
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in_button_text_activity_sign_in),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.size(BUTTON_MARGIN.dp))
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
fun GreetingPreview() {
    Camp_sign_composeTheme {
        var idText by remember { mutableStateOf("") }
        var pwText by remember { mutableStateOf("") }
        SignInActivityScaffold(
            idText = idText,
            onIdTextChange = { idText = it },
            pwText = pwText,
            onPwTextChange = { pwText = it }
        )
    }
}
