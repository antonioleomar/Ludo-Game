package utils

import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.JOptionPane

object Arquivo {
    fun criarDado(pontuacao: Int): String {
        val nome = JOptionPane.showInputDialog("Nome ?")
        val dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy,HH:mm"))
        return String.format("%s,%s,%s", nome, dataHora, pontuacao)
    }

    @Throws(IOException::class)
    fun criarArquivo(arquivo: File) {
        // Caso arquivo nao exista, cria um arquivo vazio
        if (!arquivo.exists()) {
            arquivo.createNewFile()
        }
    }

    @Throws(IOException::class)
    fun escreverNoArquivo(texto: String?, arquivo: File?) {
        val fw = FileWriter(arquivo, true)
        val bw = BufferedWriter(fw)
        bw.write(texto)
        bw.newLine()
        bw.close()
        fw.close()
    }

    @Throws(IOException::class)
    fun lerArquivo(arquivo: File): Array<Array<String?>> {
        atualizarArquivo(arquivo)
        val fr = FileReader(arquivo)
        val br = BufferedReader(fr)
        val dados: MutableList<String> = ArrayList()
        while (br.ready()) {
            val linha = br.readLine()
            dados.add(linha)
        }
        val retorno = Array(dados.size) { arrayOfNulls<String>(4) }
        for (i in dados.indices) {
            val quebra = dados[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (j in 0..3) {
                retorno[i][j] = quebra[j]
            }
        }
        br.close()
        fr.close()
        return retorno
    }

    @Throws(IOException::class)
    fun atualizarArquivo(arquivo: File) {
        val dadosOrd = arquivoOrdenado(arquivo)

        // Exclui o arquivo
        arquivo.delete()

        // Cria novamente o arquivo vazio
        criarArquivo(arquivo)
        for (dado in dadosOrd) {
            escreverNoArquivo(dado, arquivo)
        }
    }

    @Throws(IOException::class)
    fun arquivoOrdenado(arquivo: File?): List<String> {
        val fr = FileReader(arquivo)
        val br = BufferedReader(fr)
        val dados: MutableList<String> = ArrayList()
        while (br.ready()) {
            val linha = br.readLine()
            dados.add(linha)
        }
        br.close()
        fr.close()
        for (i in dados.indices) {
            for (j in 0 until dados.size - i - 1) {
                val temp = dados[j]
                val temp2 = dados[j + 1]
                if (temp.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()[3].toInt() > temp2.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()[3].toInt()) {
                    dados[j] = temp2
                    dados[j + 1] = temp
                }
            }
        }
        val retorno: MutableList<String> = ArrayList()
        var i = 0
        while (i < 5 && i < dados.size) {
            retorno.add(dados[i])
            i++
        }
        return retorno
    }
}