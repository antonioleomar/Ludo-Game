package ludo.telas

import ludo.classes.GameMoves
import javax.swing.JFrame

class GameScreen {
    private val tela: JFrame
    private val gf: GameMoves

    init {
        tela = JFrame()
        gf = GameMoves()
    }

    fun criarJanela() {
        tela.setBounds(10, 10, 1000, 600)
        tela.title = "LUDO GAME"
        tela.setSize(950, 600)
        tela.isResizable = false
        tela.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        tela.isVisible = true
        gf.isFocusable = true
        gf.addKeyListener(gf)
        gf.addMouseListener(gf)
        tela.add(gf)
        tela.isVisible = true
    }
}