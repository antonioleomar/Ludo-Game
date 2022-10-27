package ludo.classes

import utils.Arquivo
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.*
import java.io.File
import java.io.IOException
import javax.swing.JPanel
import javax.swing.Timer

class GameMoves : JPanel(), KeyListener, ActionListener, MouseListener {
    var la: Layout
    var p: Build_Player
    var time: Timer? = null
    var delay = 10
    var current_player: Int
    var dice //valor do dado	
            : Int
    var roll: Int
    var kill = 0 //Serve apenas para break no "for", caso uma peça coma outra.
    var flag =
        0 //valor 1 = sinaliza que pode selecionar alguma peça com o mouseClicked (Situações de dado 0 ou 9 e/ou alguma peça fora da origem já)
    var flagFinished =
        0 //flag para indicar que peao chegou na meta e tem direito de avançar 10 posições em qualquer outro peão	
    var contGame = 0 //numero de jogadas 	
    var timeHuman = 0
    var arquivo: File

    init {
        focusTraversalKeysEnabled = false
        requestFocus()
        current_player = 0 //começa vermelho
        la = Layout(80, 50)
        p = Build_Player(la.height, la.width)
        dice = 0
        flag = 0
        roll = 0
        kill = 0
        arquivo = File("arq/arquivo.txt")
    }

    override fun paint(g: Graphics) {
        la.draw(g as Graphics2D) //desenha tela
        p.draw(g) //desenha peças nas posições iniciais
        if (p.pl[current_player]!!.coin == 4) { //Se o jogador X tiver 4 peças na casa final
            g.setColor(Color.LIGHT_GRAY)
            g.fillRect(550, 50, 360, 130)
            if (current_player == 0) {
                g.setColor(Color.RED)
                g.setFont(Font("Segoe UI", Font.BOLD, 30))
                g.drawString("Máquina" + " Venceu!", 560, 100)
                g.drawString("Nº jogadas: $contGame", 560, 150) //Parabéns! venceu o jogo
            } else if (current_player == 1) {
                g.setColor(Color.YELLOW)
                g.setFont(Font("Segoe UI", Font.BOLD, 30))
                g.drawString("Você" + " Venceu!", 560, 100)
                g.drawString("Nº jogadas: $contGame", 560, 150) //Parabéns! venceu o jogo
                try {
                    Arquivo.criarArquivo(arquivo)
                    Arquivo.escreverNoArquivo(Arquivo.criarDado(contGame), arquivo)
                    Arquivo.atualizarArquivo(arquivo)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            //Reseta todos os valores
            current_player = 0
            la = Layout(80, 50)
            p = Build_Player(la.height, la.width)
            dice = 0
            flag = 0
            roll = 0
            kill = 0
            contGame = 0
        } else {
            g.setColor(Color.LIGHT_GRAY)
            g.fillRect(550, 50, 360, 130)
            if (current_player == 0) {
                g.setColor(Color.RED)
            } else if (current_player == 1) {
                g.setColor(Color.YELLOW)
            }
            if (contGame >= 0) {
                g.setFont(Font("Segoe UI", Font.BOLD, 30))
                // Label com informacoes do jogo
                if (current_player == 1) {
                    g.drawString("Jogador 1", 560, 100);
                } else {
                    g.drawString("Jogador IA", 560, 100);
                }
                g.drawString("Número do dado: $dice", 560, 150)
            }
            g.setColor(Color.LIGHT_GRAY)
            g.fillRect(550, 200, 360, 50)
            g.setColor(Color.BLACK)
            g.setFont(Font("Segoe UI", Font.BOLD, 20))
            g.drawString("Nº de jogadas: $contGame", 560, 230)
        }

        //Passa a vez para outro jogador: se flag = 0 (todas as peças na origem) e dado diferente de 0 e 9
        if (flag == 0 && kill == 0) {
            current_player = (current_player + 1) % 2
        }
        kill = 0
    }

    override fun keyPressed(e: KeyEvent) {


        //Aperta Enter
        if (e.keyCode == KeyEvent.VK_ENTER && flag == 0) {
            roll = 0
            dice = (Math.random() * 10).toInt() //valor randomico entre 1 <= x <= 9
            repaint()

            //------------------------------------------------------------------------------------------------------------------------
            // IA computer: máquina é current_player == 0
            if (current_player == 0) {


                //Condições prioritárias para movimento: 1-capturar adversário | 2-Chegar a meta | 3-Sair da base | 4-Andar no tabuleiro
                val tableCondition = arrayOf(
                    booleanArrayOf(false, false, false, false),
                    booleanArrayOf(false, false, false, false),
                    booleanArrayOf(false, false, false, false),
                    booleanArrayOf(false, false, false, false)
                ) //linha: peão | coluna: condições atendida = true;
                for (i in 0..3) {
                    //verificar se peça não excede meta 
                    if (p.pl[0]!!.pa[i]!!.current + dice <= 56) {
                        val PredictionPositionPath =
                            p.pl[0]!!.pa[i]!!.current + dice //pega posição no path da peça selecionada soma mais dado

                        //Capturar adversário
                        if (PredictionPositionPath % 13 != 0 && PredictionPositionPath % 13 != 8 && PredictionPositionPath < 51 && p.pl[0]!!.pa[i]!!.current != -1) {
                            val tem1 = Path.ax[0]!![PredictionPositionPath]
                            val tem2 = Path.ay[0]!![PredictionPositionPath]
                            for (m in 0..3) { //humano
                                if (p.pl[1]!!.pa[m]!!.x == tem1 && p.pl[1]!!.pa[m]!!.y == tem2) {
                                    tableCondition[i][0] = true
                                }
                            }
                        }

                        //Chegar na meta
                        if (PredictionPositionPath == 56 && p.pl[0]!!.pa[i]!!.current != 56) {
                            tableCondition[i][1] = true
                        } //fim chegar na meta			        	

                        //sair da base
                        if ((dice == 0 || dice == 9) && p.pl[0]!!.pa[i]!!.current == -1) {
                            tableCondition[i][2] = true
                        }

                        //avançar normal
                        if (PredictionPositionPath < 56 && p.pl[0]!!.pa[i]!!.current != -1) {
                            tableCondition[i][3] = true
                        }
                    }
                }

                //Executando IA:		        	
                var outFor = false
                for (condition in 0..3) {
                    for (pawn in 0..3) {
                        if (tableCondition[pawn][condition] == true) {

                            //captura adversário	
                            if (condition == 0) {
                                p.pl[0]!!.pa[pawn]!!.current += dice
                                for (n in 0..3) {
                                    if (p.pl[1]!!.pa[n]!!.current != -1) { //verificar apenas peões fora da casa
                                        val tem1 = Path.ax[0]!![p.pl[0]!!.pa[pawn]!!.current]
                                        val tem2 = Path.ay[0]!![p.pl[0]!!.pa[pawn]!!.current]
                                        if (Path.ax[1]!![p.pl[1]!!.pa[n]!!.current] == tem1 && Path.ay[1]!![p.pl[1]!!.pa[n]!!.current] == tem2) {
                                            p.pl[1]!!.pa[n]!!.current = -1
                                            for (pMachine in 0..3) {
                                                if (p.pl[0]!!.pa[pMachine]!!.current + 10 <= 56 && p.pl[1]!!.pa[pMachine]!!.current != -1) {
                                                    p.pl[0]!!.pa[pMachine]!!.current += 10
                                                    break
                                                }
                                            }
                                            break
                                        }
                                    }
                                } //
                                flag = 0
                            } else if (condition == 1) {
                                //chegar na meta
                                p.pl[0]!!.pa[pawn]!!.current += dice
                                p.pl[0]!!.coin++
                                //avançar 10 casas em qualquer peão:
                                for (pMachine in 0..3) {
                                    if (p.pl[0]!!.pa[pMachine]!!.current + 10 <= 56 && p.pl[0]!!.pa[pMachine]!!.current != -1) {
                                        p.pl[0]!!.pa[pMachine]!!.current += 10
                                        break
                                    }
                                }
                                flag = 0
                            } else if (condition == 2) {
                                // sair da base
                                p.pl[0]!!.pa[pawn]!!.current = 0
                                flag = 0
                            } else if (condition == 3) {
                                //andar no tabuleiro
                                p.pl[0]!!.pa[pawn]!!.current += dice
                                flag = 0
                            }
                            outFor = true
                            break
                        }
                    }
                    if (outFor == true) {
                        break
                    }
                }
                //}//fim iteração dos peões máquina 
                repaint()
            } //fim do current_player == 0       

            //------------------------------------------------------------------------------------------------------------------------
            if (current_player == 1) {
                contGame++
                for (i in 0..3) {
                    //Checar se tem alguma peça fora da origem e na meta
                    if (p.pl[current_player]!!.pa[i]!!.current != -1 && p.pl[current_player]!!.pa[i]!!.current != 56 && p.pl[current_player]!!.pa[i]!!.current + dice <= 56) {
                        flag = 1 //libera mouseClicked
                        break
                    }
                }

                //Se alguma peça está na origem e o dado for 0 ou 9:
                if (flag == 0 && (dice == 0 || dice == 9)) {
                    for (i in 0..3) {
                        if (p.pl[current_player]!!.pa[i]!!.current == -1) {
                            flag = 1 //libera mouseClicked
                            break
                        }
                    }
                }
            }
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        if (flag == 1 && current_player == 1) {
            //pega as coordenadas do click na tela
            var x = e.x
            var y = e.y

            //calcula qual célula foi selecionada no tabuleiro (Lembrando que 30 é a dimensão da célula)
            x = x - 80
            y = y - 50
            x = x / 30
            y = y / 30
            var value = -1 //-1 a posição atual da [peça + dado] ultrapassa a meta		
            for (i in 0..3) {
                //Se a peça selecionada não chegou ao final
                if (p.pl[current_player]!!.pa[i]!!.x == x && p.pl[current_player]!!.pa[i]!!.y == y && p.pl[current_player]!!.pa[i]!!.current + dice <= 56) {
                    value = i //Pega numero da peça
                    flag = 0 //desliga mouseClicked						
                    break
                }
            }
            if (dice == 0 || dice == 9) {

                // peça não ultrapassa a meta e não está na base
                if (value != -1 && p.pl[current_player]!!.pa[value]!!.current != -1) {
                    if (flagFinished == 0) {
                        p.pl[current_player]!!.pa[value]!!.current += dice //avança a posição da peça pelo valor do dado 
                    } else if (flagFinished == 1) {
                        p.pl[current_player]!!.pa[value]!!.current += 10
                        flagFinished = 0
                    }
                    if (p.pl[current_player]!!.pa[value]!!.current == 56) { //Se peça chegar no final
                        p.pl[1]!!.coin++ //acrescenta 1 peça a variavel de controle coin

                        //avançar 10 casas em qualquer peão:
                        for (cont in 0..3) {
                            if (p.pl[1]!!.pa[cont]!!.current + 10 <= 56 && p.pl[1]!!.pa[cont]!!.current != -1) {
                                p.pl[1]!!.pa[cont]!!.current += 10
                                break
                            }
                        }
                        flag = 1
                        flagFinished = 1
                    }
                    var k = 0
                    val hou = p.pl[current_player]!!.pa[value]!!.current //pega posição no path da peça selecionada
                    if (hou % 13 != 0 && hou % 13 != 8 && hou < 51) {
                        for (i in 0..1) {
                            //Verifica se peça corrente cai sobre outra
                            if (i != current_player) {
                                for (j in 0..3) {
                                    val tem1 = Path.ax[current_player]!![p.pl[current_player]!!.pa[value]!!.current]
                                    val tem2 = Path.ay[current_player]!![p.pl[current_player]!!.pa[value]!!.current]
                                    //Se cair sobre outra
                                    if (p.pl[i]!!.pa[j]!!.x == tem1 && p.pl[i]!!.pa[j]!!.y == tem2) {
                                        p.pl[i]!!.pa[j]!!.current = -1 //devolve para origem										

                                        //avançar 10 casas como bonus por capturar adversário, se peão não estiver na base ou o acrescimo não ultrapassar meta, 
                                        for (cont in 0..3) {
                                            if (p.pl[1]!!.pa[cont]!!.current + 10 <= 56 && p.pl[1]!!.pa[cont]!!.current != -1) {
                                                p.pl[1]!!.pa[cont]!!.current += 10
                                                break
                                            }
                                        }
                                        kill = 1
                                        k = 1
                                        break
                                    }
                                } //
                            }
                            if (k == 1) break
                        }
                    }
                } //fim da peça não está na base
                else {
                    for (i in 0..3) {
                        if (p.pl[current_player]!!.pa[i]!!.current == -1) {
                            p.pl[current_player]!!.pa[i]!!.current = 0 // coloca a peça na primeira célula do caminho
                            flag = 0
                            break
                        }
                    }
                }
            } //fim do dice==0 ou 9
            else if (dice != 0 || dice != 9) {
                //Peça válida
                if (value != -1) {
                    p.pl[current_player]!!.pa[value]!!.current += dice //adiciona valor do dado na posição
                    if (p.pl[current_player]!!.pa[value]!!.current == 56) { //Se a nova posição for 56 (Chegou no fim)
                        p.pl[1]!!.coin++ //incrementa o marcador coin						
                        //avançar 10 casas em qualquer peão:
                        for (cont in 0..3) {
                            if (p.pl[1]!!.pa[cont]!!.current + 10 <= 56 && p.pl[1]!!.pa[cont]!!.current != -1) {
                                p.pl[1]!!.pa[cont]!!.current += 10
                                break
                            }
                        }
                    }
                    var k = 0
                    val hou = p.pl[current_player]!!.pa[value]!!.current //nova posição 

                    //A peça a ser comida está protegida?
                    if (hou % 13 != 0 && hou % 13 != 8 && hou < 51) { //51 é a primeira célula dentro do caminho protegido
                        for (i in 0..1) {
                            if (i != current_player) {
                                for (j in 0..3) {
                                    //Pega a posição da peça no path do jogador corrente
                                    val tem1 = Path.ax[current_player]!![p.pl[current_player]!!.pa[value]!!.current]
                                    val tem2 = Path.ay[current_player]!![p.pl[current_player]!!.pa[value]!!.current]
                                    //Se cair sobre outra
                                    if (p.pl[i]!!.pa[j]!!.x == tem1 && p.pl[i]!!.pa[j]!!.y == tem2) {
                                        p.pl[i]!!.pa[j]!!.current = -1 //devolve para origem

                                        //avançar 10 casas como bonus por capturar adversário, se peão não estiver na base ou o acrescimo não ultrapassar meta, 
                                        for (cont in 0..3) {
                                            if (p.pl[1]!!.pa[cont]!!.current + 10 <= 56 && p.pl[1]!!.pa[cont]!!.current != -1) {
                                                p.pl[1]!!.pa[cont]!!.current += 10
                                                break
                                            }
                                        }
                                        kill = 1
                                        k = 1
                                        break
                                    }
                                }
                            }
                            if (k == 1) break
                        }
                    }
                }
            } //fim do else
            repaint()
        } //fim do flag == 1
    }

    override fun actionPerformed(e: ActionEvent) {
        // TODO Auto-generated method stub
    }

    override fun keyReleased(arg0: KeyEvent) {
        // TODO Auto-generated method stub
    }

    override fun keyTyped(arg0: KeyEvent) {
        // TODO Auto-generated method stub
    }

    override fun mouseEntered(arg0: MouseEvent) {
        // TODO Auto-generated method stub
    }

    override fun mouseExited(arg0: MouseEvent) {
        // TODO Auto-generated method stub
    }

    override fun mousePressed(e: MouseEvent) {
        // TODO Auto-generated method stub
    }

    override fun mouseReleased(arg0: MouseEvent) {
        // TODO Auto-generated method stub
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}