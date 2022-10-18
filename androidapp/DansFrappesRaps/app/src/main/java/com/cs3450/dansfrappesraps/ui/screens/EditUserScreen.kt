import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs3450.dansfrappesraps.ui.components.SignButton
import com.cs3450.dansfrappesraps.ui.components.SignTextInput
import com.cs3450.dansfrappesraps.ui.navigation.Routes
import com.cs3450.dansfrappesraps.ui.viewmodels.CreateNewUserScreenState
import com.cs3450.dansfrappesraps.ui.viewmodels.CreateNewUserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(navHostController: NavHostController, id: String?) {
    val viewModel: CreateNewUserViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    LaunchedEffect(state.signUpSuccess) {
        if (state.signUpSuccess) {
            navHostController.navigate(Routes.manageUsers.route) {
                popUpTo(Routes.manageUsers.route) {
                    inclusive = true
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.setupInitialState(id)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                if (id == null || id == "new") {
                    Text(
                        text = "Create New User",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                } else {
                    Text(
                        text = "Edit User",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = state.errorMessage,
                style = TextStyle(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            SignTextInput(
                value = state.name,
                onValueChange = { state.name = it },
                placeholder = { Text("Full Name") },
                error = state.nameError
            )
            SignTextInput(
                value = state.email,
                onValueChange = { state.email = it },
                placeholder = { Text("Email") },
                error = state.emailError
            )
            Box() {
                OutlinedTextField(
                    value = state.userType,
                    onValueChange = {},
                    modifier = Modifier
                        .clickable {
                            state.dropdownExpanded = true
                        },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    },
                    enabled = false,
                    label = { Text("User Type", color = MaterialTheme.colorScheme.primary) },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimaryContainer),
                )
                DropdownMenu(
                    expanded = state.dropdownExpanded,
                    onDismissRequest = {state.dropdownExpanded = false }) {
                    listOf(
                        CreateNewUserScreenState.CUSTOMER,
                        CreateNewUserScreenState.EMPLOYEE,
                        CreateNewUserScreenState.MANAGER
                    ).forEach {
                        DropdownMenuItem(
                            onClick = {
                                state.userType = it
                            },
                            text = { Text(it) })
                    }

                }
            }
        }
        Spacer(modifier = Modifier.padding(6.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (id == null || id == "new") {
                SignButton(
                    text = "Create User",
                    onClick = {
                        scope.launch {
                            viewModel.signUp()
                        }
                    }
                )
            } else {
                SignButton(
                    text = "Save User",
                    onClick = {
                        scope.launch {
                            viewModel.updateUser()
                        }
                    }
                )
            }
        }
    }
}
