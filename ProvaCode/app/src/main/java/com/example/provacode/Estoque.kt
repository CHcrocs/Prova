package com.example.provacode

import androidx.compose.material3.Text

class Estoque {
    companion object {
        var listaEstoque = listOf<Produto>()
        lateinit var estoqueTotal: Produto

        fun adicionarProdutos(nome: String, categoria: String, preco: Float, quant: Int) {
            listaEstoque += listOf(Produto(nome, categoria, preco, quant))
        }

        fun calcularValorTotalEstoque(): Float {
            listaEstoque.forEach { estoqueTotal.preco += it.preco}
            listaEstoque.forEach { estoqueTotal.quantEstoque += it.quantEstoque}

            return estoqueTotal.preco * estoqueTotal.quantEstoque
        }
    }
}