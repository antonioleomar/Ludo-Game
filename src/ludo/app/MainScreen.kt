package ludo.app

import ludo.telas.GameScreen
import ludo.telas.RankingScreen
import java.awt.AWTException
import java.awt.Font
import java.io.IOException
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

object MainScreen : JFrame() {
    @JvmStatic
    fun main(args: Array<String>) {
        val textoInicial = JLabel("LUDO GAME")
        textoInicial.setSize(200, 100)
        textoInicial.font = Font("Segoe UI", Font.BOLD, 30)
        textoInicial.setLocation(170, 50)
        textoInicial.isVisible = true
        val botaoJogar = JButton("Jogar")
        val ranking = JButton("Ranking")
        val sair = JButton("Sair")
        botaoJogar.setSize(100, 40)
        botaoJogar.setLocation(200, 150)
        ranking.setSize(100, 40)
        ranking.setLocation(200, 200)
        sair.setSize(100, 40)
        sair.setLocation(200, 250)
        val tela = JFrame()
        tela.setBounds(700, 200, 1000, 600)
        tela.title = "LUDO GAME"
        tela.setSize(500, 500)
        tela.isResizable = false
        tela.defaultCloseOperation = EXIT_ON_CLOSE
        tela.isVisible = true
        tela.layout = null
        tela.add(textoInicial)
        tela.add(botaoJogar)
        tela.add(ranking)
        tela.add(sair)
        botaoJogar.addActionListener {
            val game: GameScreen
            try {
                game = GameScreen()
                game.criarJanela()
                tela.extendedState = ICONIFIED
            } catch (e1: AWTException) {
                e1.printStackTrace()
            }
        }
        ranking.addActionListener {
            val rs: RankingScreen
            try {
                rs = RankingScreen()
                rs.criaJanela()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }
        sair.addActionListener { System.exit(0) }
    }
}