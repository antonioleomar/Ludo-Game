package ludo.telas

import utils.Arquivo
import java.awt.Font
import java.awt.GridLayout
import java.io.File
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTable

class RankingScreen : JFrame() {
    var painelFundo: JPanel? = null
    var tabela: JTable? = null
    var barraRolagem: JScrollPane? = null
    var arquivo: File
    var dados: Array<Array<String?>>
    var colunas = arrayOf("Nome", "Data", "Horario", "Numero de rodadas")

    init {
        arquivo = File("arq/arquivo.txt")
        Arquivo.criarArquivo(arquivo)
        dados = Arquivo.lerArquivo(arquivo)
    }

    fun criaJanela() {
        painelFundo = JPanel()
        painelFundo!!.layout = GridLayout(1, 1)
        tabela = JTable(dados, colunas)
        barraRolagem = JScrollPane(tabela)
        painelFundo!!.add(barraRolagem)
        title = "RANKING"
        tabela!!.font = Font("Segoe UI", Font.BOLD, 13)
        contentPane.add(painelFundo)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(500, 142)
        setBounds(700, 350, 500, 142)
        isVisible = true
        isResizable = false
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}