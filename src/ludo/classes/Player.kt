package ludo.classes

import java.awt.Graphics2D

class Player(height: Int, width: Int) {
    var height = 0
    var width = 0
    var status: Int
    var coin: Int
    var pa = arrayOfNulls<Pawn>(4) //cada jogador tem 4 peça

    init {
        status = -1
        coin = 0
        for (i in 0..3) {
            if (i == 0) {
                pa[i] = Pawn(height, width, 0)
            } else {
                pa[i] = Pawn(height, width, -1)
            }
        }
    } //fim do player

    fun draw(g: Graphics2D?) {}
}