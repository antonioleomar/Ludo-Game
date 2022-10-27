package ludo.classes

import java.awt.Graphics2D

class Build_Player(height: Int, width: Int) {
    var pl = arrayOfNulls<Player>(2) // 2 jogadores

    init {
        for (i in 0..1) {
            pl[i] = Player(height, width)
        }
    }

    // Desenha as peças dos jogadores na tela nas posições iniciais
    fun draw(g: Graphics2D) {
        for (i in 0..1) {
            for (j in 0..3) {
                pl[i]!!.pa[j]!!
                    .draw(g, Path.initialx[i]!![j], Path.initialy[i]!![j], i)
            }
        }
    }
}