package com.example.provacode

data class Produto(
    val nome: String,
    val categoria: String,
    val preco: Float,
    val quantEstoque: Int
){
    companion object{
        var listaProduto = listOf<Produto>()
    }
}