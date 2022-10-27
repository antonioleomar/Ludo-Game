package ludo.classes

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D

class Pawn(
    h: Int, w: Int, // posi��o corrente no array do Path (x e y da pe�a)
    var current: Int
) {
    var x: Int
    var y // posi��o na tela
            : Int
    var height: Int
    var width // tamanho das c�lulas. EX.: 30, 30
            : Int

    init {
        x = -1
        y = -1
        height = h
        width = w
    }

    fun draw(
        g: Graphics2D,
        i: Int,
        j: Int,
        play: Int
    ) { // usado no bluid_player: i e j = posi��o inicial da bolinha. play � o jogador: 1,2...

        //Posi��o inicial
        if (current == -1) {
            val temp1 = 80 + height / 2
            val temp2 = 50 + width / 2
            x = i
            y = j
            if (play == 0) {
                g.color = Color.RED // pinta a bolinha interna do jogador com verde
            } else if (play == 1) {
                g.color = Color.YELLOW
            }
            g.fillOval(
                temp1 + 5 + i * width,
                temp2 + 5 + j * height,
                width - 10,
                height - 10
            ) // posi��o da cor interna da bolinha																										
            g.stroke = BasicStroke(2f)
            g.color = Color.BLACK // cor da borda dos c�rculos das pe�as
            g.drawOval(
                temp1 + 5 + i * width,
                temp2 + 5 + j * height,
                width - 10,
                height - 10
            ) // posi��o da borda das bolinhas																						
        } else {
            val temp1 = 80
            val temp2 = 50
            x = Path.ax[play]!![current]
            y = Path.ay[play]!![current]
            if (play == 0) {
                g.color = Color.RED
            } else if (play == 1) {
                g.color = Color.YELLOW
            }

            // Pe�as deslocando no tabuleiro
            g.fillOval(temp1 + 5 + x * width, temp2 + 5 + y * height, width - 10, height - 10)
            g.stroke = BasicStroke(2f)
            g.color = Color.BLACK
            g.drawOval(temp1 + 5 + x * width, temp2 + 5 + y * height, width - 10, height - 10)
        }
    }
}