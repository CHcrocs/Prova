package com.example.provacode

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.provacode.ui.theme.ProvaCodeTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}

@Composable
fun Main() {
    var navController = rememberNavController()

    NavHost(navController = navController, startDestination = "cadastro") {
        composable("cadastro") { CadastroProduto(navController) }
        composable("lista") { ListaProdutos(navController) }
        composable("detalhes/{produtoJson}") { backStackEntry ->
            val produtoJson = backStackEntry.arguments?.getString("produtoJson")
            val produto = Gson().fromJson(produtoJson, Produto::class.java)

            DetalhesProduto(navController, produto)
        }
        composable("estatisticas") { Estatisticas(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroProduto(navController: NavController) {

    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var quantEstoque by remember { mutableStateOf("") }

    val context = LocalContext.current

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
            } else if (quantEstoque.toInt() < 1) {
                Toast.makeText(context, "Quantidade não pode ser menor que 1!", Toast.LENGTH_SHORT)
                    .show()
            } else if (preco.toFloat() < 0) {
                Toast.makeText(context, "Preço não pode ser menor que 0!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Estoque.adicionarProdutos(nome, categoria, preco.toFloat(), quantEstoque.toInt())

                navController.navigate("lista")
            }
        }) {
            Text(text = "Cadastrar")
        }
    }
}

@Composable
fun ListaProdutos(navController: NavController) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn() {
            items(Estoque.listaEstoque) { produto ->
                Text(
                    text = "${produto.nome} - (${produto.quantEstoque} unidades)",
                    fontSize = 15.sp
                )
                Button(onClick = {
                    val produtoJson = Gson().toJson(produto)
                    navController.navigate("detalhes/$produtoJson")
                }) {
                    Text(text = "Detalhes")
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))

        Button(onClick = {
            navController.navigate("estatisticas")
        }) {
            Text(text = "Estatísticas")
        }

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Cadastrar outro produto")
        }

    }
}

@Composable
fun DetalhesProduto(navController: NavController, produto: Produto) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nome: ${produto.nome}", fontSize = 20.sp)
        Text(text = "Categoria: ${produto.categoria}", fontSize = 20.sp)
        Text(text = "Preço: ${produto.preco}", fontSize = 20.sp)
        Text(text = "Quantidade: ${produto.quantEstoque}", fontSize = 20.sp)

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Voltar")
        }
    }
}

@Composable
fun Estatisticas(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${Estoque.calcularValorTotalEstoque()} é o valor total do estoques", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Atualmente possuimos ${Estoque.quantidadeTotalProdutos()} produtos", fontSize = 20.sp)

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Voltar")
        }
    }
}