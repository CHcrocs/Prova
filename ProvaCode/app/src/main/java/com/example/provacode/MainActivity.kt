package com.example.provacode

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.provacode.ui.theme.ProvaCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutMain()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMain() {

    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var quantEstoque by remember { mutableStateOf("") }

    val context = LocalContext.current

    var listaProduto by remember { mutableStateOf(listOf<Produto>()) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text(text = "Nome") })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text(text = "Categoria") })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = preco,
            onValueChange = { preco = it },
            label = { Text(text = "Preço") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = quantEstoque,
            onValueChange = { quantEstoque = it },
            label = { Text(text = "Quantidade Estoque") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(onClick = {
            // Verificação de parametros
            if (nome.isBlank() || categoria.isBlank() || preco.isBlank() || quantEstoque.isBlank()) {
                Toast.makeText(context, "Preencher todos os campos!", Toast.LENGTH_SHORT).show()
            }
            else {
                // Passagem de dados para uma lista
                listaProduto += Produto(nome, categoria, preco.toFloat(), quantEstoque.toInt())

                // Limpeza de variaveis
                nome = ""
                categoria = ""
                preco = ""
                quantEstoque = ""
            }
        }) {
            Text(text = "Cadastrar")
        }
    }
}