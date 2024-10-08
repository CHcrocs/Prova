package com.example.provacode

import androidx.compose.material3.Text

class Estoque {
    companion object {
        var listaEstoque = listOf<Produto>()

        fun adicionarProdutos(nome: String, categoria: String, preco: Float, quant: Int) {
            listaEstoque += listOf(Produto(nome, categoria, preco, quant))
        }

        fun calcularValorTotalEstoque(): Float {
            var precoTotal = 0.0f
            var quant = 0

            listaEstoque.forEach { precoTotal += it.preco}
            listaEstoque.forEach { quant += it.quantEstoque}

            return precoTotal * quant
        }

        fun quantidadeTotalProdutos(): Int{
            var quant = 0

            listaEstoque.forEach { quant += it.quantEstoque}
            return quant
        }
    }
}