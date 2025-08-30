package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.AddCharacterDialog
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinBackground
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.Black
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.Primary
import com.shatytskyi.munchcounter.ui.theme.White
import com.shatytskyi.munchcounter.viewmodel.CharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel,
    onCharacterClick: (Long) -> Unit,
    onFightClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    val characters by viewModel.characters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var showResetAllDialog by remember { mutableStateOf(false) }
    var showRemoveAllDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    MunchkinBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Munchkin Counter",
                            style = MaterialTheme.typography.titleLarge,
                            color = White
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (characters.isNotEmpty()) {
                                    showResetAllDialog = true
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Сбросить всех"
                            )
                        }

                        IconButton(
                            onClick = {
                                if (characters.isNotEmpty()) {
                                    showRemoveAllDialog = true
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Удалить всех"
                            )
                        }

                        IconButton(onClick = { showDiceDialog = true }) {
                            Icon(
                                Icons.Default.Casino,
                                contentDescription = "Кубик"
                            )
                        }

                        IconButton(onClick = { viewModel.shuffleCharacters() }) {
                            Icon(
                                Icons.Default.Shuffle,
                                contentDescription = "Перемешать"
                            )
                        }

                        IconButton(onClick = { showInfoDialog = true }) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Информация"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = Primary,
                    contentColor = White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_add),
                        contentDescription = "Добавить игрока",
                        modifier = Modifier.size(Dimens.iconSizeSmall)
                    )
                }
            },
            modifier = modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)),
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    characters.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .size(300.dp, 360.dp)
                                .align(Alignment.Center)
                        ) {
                            // Character images in grid
                            Row(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.pic_wizard),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(Dimens.paddingExtraLarge)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.pic_knight),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(Dimens.paddingExtraLarge)
                                    )
                                }
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.pic_witch),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(Dimens.paddingExtraLarge)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.pic_knight_fem),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(Dimens.paddingExtraLarge)
                                    )
                                }
                            }

                            // Center text
                            MunchkinCard(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 40.dp)
                            ) {
                                Text(
                                    text = "Нет игроков",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Black,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(Dimens.paddingMedium)
                                )
                            }
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = Dimens.paddingMedium,
                                bottom = Dimens.listBottomPadding
                            ),
                            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
                        ) {
                            items(characters) { character ->
                                CharacterListItem(
                                    character = character,
                                    onClick = { onCharacterClick(character.id) },
                                    onLevelChange = { delta ->
                                        viewModel.changeLevel(character.id, delta)
                                    },
                                    onPowerChange = { delta ->
                                        viewModel.changePower(character.id, delta)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialogs
    if (showAddDialog) {
        AddCharacterDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name ->
                viewModel.addCharacter(name)
                showAddDialog = false
            }
        )
    }

    if (showResetAllDialog) {
        WarningDialog(
            title = "Сбросить всех игроков?",
            message = "Все игроки будут сброшены до 1 уровня с 0 силы",
            onDismiss = { showResetAllDialog = false },
            onConfirm = {
                viewModel.resetAllCharacters()
                showResetAllDialog = false
            }
        )
    }

    if (showRemoveAllDialog) {
        WarningDialog(
            title = "Удалить всех игроков?",
            message = "Все игроки будут безвозвратно удалены",
            onDismiss = { showRemoveAllDialog = false },
            onConfirm = {
                viewModel.removeAllCharacters()
                showRemoveAllDialog = false
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { showDiceDialog = false }
        )
    }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = { Text("Информация") },
            text = { Text(stringResource(R.string.info)) },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Show error if any
    error?.let { errorMessage ->
        LaunchedEffect(errorMessage) {
            // Here you could show a Snackbar or Toast
        }
    }
}

@Composable
private fun DiceDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var result by remember { mutableStateOf<Int?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Бросить кубик") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (result != null) {
                    Text(
                        text = "Результат: $result",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(Dimens.paddingLarge))
                }

                Button(
                    onClick = { result = (1..6).random() }
                ) {
                    Icon(
                        Icons.Default.Casino,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(Dimens.paddingMedium))
                    Text("Бросить")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        },
        modifier = modifier
    )
}
