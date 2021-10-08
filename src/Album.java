import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta classe implementa um álbum (de figurinhas, selos, etc.) online, permitindo
 * colecionar itens que possuem uma posição específica no álbum.
 *
 * Itens são acrescentados ao álbum por meio de "pacotinhos" contendo uma quantidade
 * fixa, pré-determinada de itens.
 *
 * Itens já existentes no álbum e recebidos novamente em pacotinhos posteriores são
 * armazenados para eventuais trocas com outro usuários.
 *
 * @param <T> o tipo de objeto colecionável que o álbum irá armazenar
 */
public class Album<T extends Colecionavel> {


    private HashMap<Integer , T> Album = new HashMap<>();
    private int tamanhoDoAlbum;
    private int quantItensPorPacotinho;
    private HashMap<Integer,Integer> quantidadeByPosicao = new HashMap<>();
    private int quantItensColodos = 0;
    private int QuantItensRepetidos = 0;

    /**
     * Construtor.
     *
     * @param tamanhoDoAlbum O tamanho do álbum sendo criado (note que os itens que serão colecionados
     *                       terão posições entre 1 e o tamanho do álbum)
     *
     * @param quantItensPorPacotinho A quantidade de itens em cada pacotinho adquirido para este álbum
     */
    public Album(int tamanhoDoAlbum, int quantItensPorPacotinho) {
        this.tamanhoDoAlbum = tamanhoDoAlbum;
        this.quantItensPorPacotinho = quantItensPorPacotinho;

    }

    /**
     * Recebe novos itens que serão imediatamente "colados" ao álbum, se inéditos,
     * ou guardados para troca, se repetidos.
     *
     * @param pacotinho Um pacotinho de itens, que poderão ser inéditos ou repetidos
     *
     * @throws PacotinhoInvalidoException se o pacotinho contiver uma quantidade errada de itens,
     *                                    ou se contiver algum item que não pertença ao álbum
     *                                    (por indicar uma posição menor que 1 ou maior que seu tamanho)
     */
    public void receberNovoPacotinho(Pacotinho<T> pacotinho) throws PacotinhoInvalidoException {
        if (quantItensPorPacotinho != pacotinho.size()) {
            throw new PacotinhoInvalidoException();
        }
        for (T item : pacotinho) {
            int posicao = item.getPosicao();
            if (posicao > tamanhoDoAlbum || posicao < 1) {
                throw new PacotinhoInvalidoException();
            }
        }
        for (T item : pacotinho) {
            int posicao = item.getPosicao();
            if (!(Album.containsKey(posicao))) {
                Album.put(posicao, item);
                quantItensColodos += 1;
            }
            else if (quantidadeByPosicao.containsKey(posicao)) {
                quantidadeByPosicao.put(posicao, quantidadeByPosicao.get(posicao) + 1);
                QuantItensRepetidos += 1;
            }else {
                quantidadeByPosicao.put(posicao,1);
                QuantItensRepetidos += 1;
            }

        }
    }

    /**
     * @return a quantidade total de figurinhas que este álbum apresenta quando se encontra completo
     */
    public int getTamanho() {
        return tamanhoDoAlbum;
    }

    /**
     * @return a quantidade total de itens que estão "colados" no álbum,
     * isto é, o total de itens distintos que foram recebidos até o momento
     */
    public int getQuantItensColados() {
        return quantItensColodos;
    }

    /**
     * @return o total de itens repetidos que foram acumulados até o momento
     */
    public int getQuantItensRepetidos() {
        return QuantItensRepetidos;
    }

    public boolean possuiItemColado(int posicao) {
        return Album.containsKey(posicao);
    }

    public boolean possuiItemRepetido(int posicao) {
        return quantidadeByPosicao.containsKey(posicao);
    }

    /**
     * @return a quantidade de itens que faltam para o álbum ficar completo
     */
    public int getQuantItensFaltantes() {
        return tamanhoDoAlbum - quantItensColodos;
    }

    /**
     * @param posicao a posição do iten desejado
     * @return o item que está colado na posição especificada, se houver; null, caso contrário
     */
    public T getItemColado(int posicao) {
        return Album.getOrDefault(posicao, null);
    }
}
