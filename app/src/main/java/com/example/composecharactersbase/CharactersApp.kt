package com.example.composecharactersbase

import CharacterViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview
@Composable
fun CharacterApp() {
    // Função principal que inicia a tela do aplicativo.
    // Aqui chamamos a tela que lista os personagens.
    CharacterListScreen()
}

@Composable
fun CharacterListScreen(viewModel: CharacterViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getCharacters()
    }

    val characters = viewModel.characters
    val error = viewModel.errorMessage

    if (error != null) {
        Text("Erro ao carregar: $error", color = Color.Red)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(characters) { character ->
                CharacterCard(character)
            }
        }
    }
}


@Composable
fun CharacterCard(character: CharacterModel) {
    // Estado que controla se o personagem é favorito ou não.
    var isFavorite by remember { mutableStateOf(false) }

    // Card é um componente que cria um contêiner com elevação e bordas arredondadas.
    Card(
        modifier = Modifier
            .fillMaxWidth() // Preenche toda a largura disponível.
            .height(140.dp), // Define a altura do cartão.
        shape = RoundedCornerShape(16.dp), // Define bordas arredondadas.
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Define a elevação do cartão.
    ) {
        // Row organiza os elementos horizontalmente.
        Row(
            modifier = Modifier
                .fillMaxSize() // Preenche todo o espaço disponível no cartão.
                .padding(12.dp), // Adiciona um espaçamento interno de 12dp.
            verticalAlignment = Alignment.CenterVertically // Alinha os elementos verticalmente ao centro.
        ) {

            // Espaçamento horizontal entre a imagem e o texto.
            Spacer(modifier = Modifier.width(16.dp))

            // Coluna para organizar os textos verticalmente.
            Column(
                modifier = Modifier
                    .weight(1f) // Faz com que a coluna ocupe o espaço restante.
                    .fillMaxHeight(), // Preenche toda a altura disponível.
                verticalArrangement = Arrangement.Center // Alinha os textos verticalmente ao centro.
            ) {
                // Exibe o nome do personagem.
                Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                // Exibe o status do personagem.
                Text(text = character.gender, style = MaterialTheme.typography.titleMedium)
                // Exibe a espécie do personagem.
                Text(text = character.age.toString(), style = MaterialTheme.typography.titleMedium)
            }

            // Botão para marcar/desmarcar o personagem como favorito.
            IconButton(
                onClick = {
                    isFavorite = !isFavorite // Alterna o estado de favorito.
                    // TODO: Salvar ou remover dos favoritos usando SharedPreferences.
                }
            ) {
                // Ícone que muda dependendo do estado de favorito.
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Favorito", // Descrição do ícone para acessibilidade.
                    tint = if (isFavorite) Color.Yellow else Color.Gray // Cor do ícone.
                )
            }
        }
    }
}
