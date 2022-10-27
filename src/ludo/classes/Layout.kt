package ludo.classes

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

class Layout     //coord X inicial do tabuleiro
//coord Y inicial do tabuleiro
//largura das células
//altura das células
    (var x: Int, var y: Int) {
    var width = 30
    var height = 30
    fun draw(g: Graphics2D) {

        //Fundo branco retangular do tabuleiro (serve para pintar as células brancas)
        g.color = Color.WHITE //células brancas
        g.fillRect(
            x,
            y,
            15 * width,
            15 * height
        ) //X e Y são as coordenadas iniciais. width e height são largura e altura do retangulo
        for (i in 0..5) {
            //pintura vermelha 
            g.color = Color.LIGHT_GRAY
            //pintura vermelha da casa top
            g.fillRect(x + i * width, y, width, height)
            //pintura vermelha da casa left
            g.fillRect(x, y + i * height, width, height)
            //pintura vermelha da casa bottom
            g.fillRect(x + i * width, y + 5 * height, width, height)
            //pintura vermelha da casa right
            g.fillRect(x + 5 * width, y + i * height, width, height)
            g.color = Color.RED
            g.fillRect(x + (i + 9) * width, y, width, height)
            g.fillRect(x + 9 * width, y + i * height, width, height)
            g.fillRect(x + (i + 9) * width, y + 5 * height, width, height)
            g.fillRect(x + 14 * width, y + i * height, width, height)
            g.color = Color.LIGHT_GRAY
            g.fillRect(x + (i + 9) * width, y + 9 * height, width, height)
            g.fillRect(x + 9 * width, y + (i + 9) * height, width, height)
            g.fillRect(x + (i + 9) * width, y + 14 * height, width, height)
            g.fillRect(x + 14 * width, y + (i + 9) * height, width, height)
            g.color = Color.YELLOW
            g.fillRect(x + i * width, y + 9 * height, width, height)
            g.fillRect(x, y + (i + 9) * height, width, height)
            g.fillRect(x + i * width, y + 14 * height, width, height)
            g.fillRect(x + 5 * width, y + (i + 9) * height, width, height)
        }
        for (i in 1..5) {
            //Pintura vermelha do caminho horizontal
            g.color = Color.LIGHT_GRAY
            g.fillRect(x + i * width, y + 7 * height, width, height)
            //Pintura amarela do caminho horizontal
            g.color = Color.LIGHT_GRAY
            g.fillRect(x + (8 + i) * width, y + 7 * height, width, height)
            //Pintura verde do caminho vertical
            g.color = Color.RED
            g.fillRect(x + 7 * width, y + i * height, width, height)
            //Pintura azul do caminho vertical
            g.color = Color.YELLOW
            g.fillRect(x + 7 * width, y + (8 + i) * height, width, height)
        }
        //1 Célula de saída da casa vermelha
        g.color = Color.LIGHT_GRAY
        g.fillRect(x + 1 * width, y + 6 * height, width, height)
        //1 Célula de saída da casa amarela
        g.color = Color.LIGHT_GRAY
        g.fillRect(x + 13 * width, y + 8 * height, width, height)
        //1 Célula de saída da casa verde
        g.color = Color.RED
        g.fillRect(x + 8 * width, y + 1 * height, width, height)
        //1 Célula de saída da casa azul
        g.color = Color.YELLOW
        g.fillRect(x + 6 * width, y + 13 * height, width, height)
        val temp1 = x + 45
        val temp2 = y + 45
        for (i in 0..1) {
            for (j in 0..1) {
                //Pintura vermelha das 4 casas dos jogadores
                g.color = Color.LIGHT_GRAY
                g.fillRect(temp1 + 2 * i * width, temp2 + 2 * j * height, width, height)
                //Pintura amarela das 4 casas dos jogadores
                g.color = Color.LIGHT_GRAY
                g.fillRect(temp1 + 2 * i * width + 9 * width, temp2 + 2 * j * height + 9 * height, width, height)
                //Pintura verde das 4 casas dos jogadores
                g.color = Color.RED
                g.fillRect(temp1 + 2 * i * width + 9 * width, temp2 + 2 * j * height + 0 * height, width, height)
                //Pintura azul das 4 casas dos jogadores
                g.color = Color.YELLOW
                g.fillRect(temp1 + 2 * i * width + 0 * width, temp2 + 2 * j * height + 9 * height, width, height)
            }
        }
        //Pintura dos triangulos de chegada - cor vermelha
        g.color = Color.LIGHT_GRAY
        val xpoints0 = intArrayOf(x + 6 * width, x + 6 * width, x + 15 + 7 * width)
        val ypoints0 = intArrayOf(y + 6 * height, y + 9 * height, y + 15 + 7 * width)
        val npoints = 3
        g.fillPolygon(xpoints0, ypoints0, npoints)
        //Pintura dos triangulos de chegada - cor amarela
        g.color = Color.LIGHT_GRAY
        val xpoints1 = intArrayOf(x + 9 * width, x + 9 * width, x + 15 + 7 * width)
        val ypoints1 = intArrayOf(y + 6 * height, y + 9 * height, y + 15 + 7 * width)
        val npoints1 = 3
        g.fillPolygon(xpoints1, ypoints1, npoints1)
        //Pintura dos triangulos de chegada - cor verde
        g.color = Color.RED
        val xpoints2 = intArrayOf(x + 6 * width, x + 9 * width, x + 15 + 7 * width)
        val ypoints2 = intArrayOf(y + 6 * height, y + 6 * height, y + 15 + 7 * width)
        val npoints2 = 3
        g.fillPolygon(xpoints2, ypoints2, npoints2)
        //Pintura dos triangulos de chegada - cor azul
        g.color = Color.YELLOW
        val xpoints3 = intArrayOf(x + 6 * width, x + 9 * width, x + 15 + 7 * width)
        val ypoints3 = intArrayOf(y + 9 * height, y + 9 * height, y + 15 + 7 * width)
        val npoints3 = 3

        //Grid dos caminhos
        g.fillPolygon(xpoints3, ypoints3, npoints3)
        g.stroke = BasicStroke(2f)
        g.color = Color.BLACK
        for (i in 0..2) {
            for (j in 0..5) {
                //Grid top
                g.drawRect(x + (i + 6) * width, y + j * height, width, height)
                //Grid left
                g.drawRect(x + j * width, y + (i + 6) * height, width, height)
                //Grid bottom
                g.drawRect(x + (i + 6) * width, y + (j + 9) * height, width, height)
                //Grid rigth
                g.drawRect(x + (j + 9) * width, y + (i + 6) * height, width, height)
            }
        }

        //Retangulo preto cerca os 4 jogadores vermelhos
        g.drawRect(x + 1 * width, y + 1 * height, 4 * width, 4 * height)
        //Retangulo preto cerca os 4 jogadores verde
        g.drawRect(x + 10 * width, y + 1 * height, 4 * width, 4 * height)
        //Retangulo preto cerca os 4 jogadores azul
        g.drawRect(x + 1 * width, y + 10 * height, 4 * width, 4 * height)
        //Retangulo preto cerca os 4 jogadores amarelo
        g.drawRect(x + 10 * width, y + 10 * height, 4 * width, 4 * height)
        g.drawRect(x, y, 15 * width, 15 * height)
        for (i in 0..1) {
            for (j in 0..1) {
                //Retangulo preto cerca cada jogador vermelho
                g.drawRect(temp1 + 2 * i * width, temp2 + 2 * j * height, width, height)
                //Retangulo preto cerca cada jogador amarelo
                g.drawRect(temp1 + 2 * i * width + 9 * width, temp2 + 2 * j * height + 9 * height, width, height)
                //Retangulo preto cerca cada jogador verde
                g.drawRect(temp1 + 2 * i * width + 9 * width, temp2 + 2 * j * height + 0 * height, width, height)
                //Retangulo preto cerca cada jogador azul
                g.drawRect(temp1 + 2 * i * width + 0 * width, temp2 + 2 * j * height + 9 * height, width, height)
            }
        }

        //Circulos pretos pré-desenhadas nos caminhos
        g.drawPolygon(xpoints0, ypoints0, npoints)
        g.drawPolygon(xpoints1, ypoints1, npoints1)
        g.drawPolygon(xpoints2, ypoints2, npoints2)
        g.drawPolygon(xpoints3, ypoints3, npoints3)
        g.drawOval(x + 5 + 6 * width, y + 5 + 2 * height, width - 10, height - 10)
        g.drawOval(x + 5 + 12 * width, y + 5 + 6 * height, width - 10, height - 10)
        g.drawOval(x + 5 + 8 * width, y + 5 + 12 * height, width - 10, height - 10)
        g.drawOval(x + 5 + 2 * width, y + 5 + 8 * height, width - 10, height - 10)


        //Fonte das letras
        g.font = Font("Segoe UI", Font.BOLD, 20) //Fonte 
        g.drawString("", 90, 35) //nome, coord X, coord Y
        g.drawString("Máquina", 400, 35)
        g.drawString("Você", 130, 530)
        g.drawString("", 370, 540)
        g.drawString("Instruções:", 550, 390)
        g.drawString("1. Pressione Enter para rolar o dado", 550, 420)
        g.drawString("2. Clique no peão para mover", 550, 450)
    }
}