import model.*;
import model.persistence.*;
import model.repository.*;
import service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void menuLocadora() {
        System.out.println();
        System.out.println(" -- LOCADORA GAMES 3.0 -- ");
        System.out.println("\nMenu");
        System.out.println("1 - Cadastro de clientes" +
                "\n2 - Cadastro de jogos" +
                "\n3 - Locação de jogos" +
                "\n4 - Locação de consoles" +
                "\n5 - Relatórios e histórico" +
                "\n6 - Reserva de jogos ");
        System.out.println("\nSelecione a opção desejada: ");
    }

    public static void voltarMenu() {
        System.out.println("\nPressione Enter para voltar ao menu...");
        new Scanner(System.in).nextLine();
    }

    public static void main(String[] args) {

        Locale.setDefault(new Locale("en", "US"));
        ClienteRepository clienteRepository = ClientePersistence.getInstance();
        JogoRepository jogoRepository = JogoPersistence.getInstance();
        JogoPlataformaRepository jogoPlataformaRepository = JogoPlataformaPersistence.getInstance();
        PlataformaRepository plataformaRepository = PlataformaPersistence.getInstance();
        LocacaoRepository locacaoRepository = LocacaoPersistence.getInstance();
        ItemLocacaoRepository itemLocacaoRepository = ItemLocacaoPersistence.getInstance();
        ConsoleRepository consoleRepository = ConsolePersistence.getInstance();
        UtilizacaoDoConsolePeloClienteRepository utilizacaoDoConsolePeloClienteRepository = UtilizacaoDoConsolePeloClientePersistence.getInstance();
        ReservaRepository reservaRepository = ReservaPersistence.getInstance();

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            menuLocadora();
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    System.out.println("Informe o nome: ");
                    String nome = scanner.nextLine();
                    System.out.println("Informe o CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.println("Informe o e-mail: ");
                    String email = scanner.nextLine();
                    System.out.println("Informe o telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.println("Informe a senha: ");
                    String senha = scanner.nextLine();

                    Cliente cliente = new Cliente();
                    cliente.setNome(nome);
                    cliente.setEmail(email);
                    cliente.setTelefone(telefone);
                    cliente.setSenha(senha);

                    clienteRepository.salvar(cliente);
                    voltarMenu();
                    break;

                case 2:
                    String resp = "";
                    do {
                        String nomePlataforma = "";
                        double preco = 0;
                        System.out.println("Informe o nome do jogo: ");
                        String nomeJogo = scanner.nextLine();
                        System.out.println("Informe a plataforma: ");
                        nomePlataforma = scanner.nextLine();
                        System.out.println("Informe o preço da diária: ");
                        preco = scanner.nextDouble();

                        Jogo jogo = new Jogo();
                        jogo.setTitulo(nomeJogo);

                        jogoRepository.salvar(jogo);

                        Plataforma plataforma = new Plataforma();
                        plataforma.setNome(nomePlataforma);

                        JogoPlataforma jogoPlataforma = new JogoPlataforma();
                        jogoPlataforma.setPlataforma(plataforma);
                        jogoPlataforma.setPrecoDiario(new BigDecimal(preco));
                        jogoPlataforma.setJogo(jogo);

                        plataformaRepository.salvar(plataforma);
                        jogoPlataformaRepository.salvar(jogoPlataforma);

                        jogo.getJogoPlataformas().add(jogoPlataforma);
                        scanner.nextLine();
                        System.out.println("Desejar cadastrar outro jogo?");
                        resp = scanner.nextLine();
                    } while (resp.equalsIgnoreCase("sim"));
                    voltarMenu();
                    break;

                case 3:
                    Cliente cliente2 = new Cliente();
                    Locacao locacao = new Locacao();
                    List<ItemLocacao> itensLocacao = new ArrayList<>();
                    BigDecimal precoLocacao = BigDecimal.ZERO;
                    String respostaAlugarMaisJogos = "sim";

                    while (respostaAlugarMaisJogos.equalsIgnoreCase("sim")) {
                        System.out.println("Informe o nome do jogo: ");
                        String nomeJogo = scanner.nextLine();

                        List<Jogo> jogos = jogoRepository.buscarInformacoesJogoPorNome(nomeJogo, LocalDate.now());

                        if (jogos.isEmpty()) {
                            System.out.println("\nO jogo não se encontra disponível.");
                            respostaAlugarMaisJogos = "sim";
                        } else {
                            System.out.println();
                            for (Jogo jogo : jogos) {
                                System.out.println("Título: " + jogo.getTitulo());
                                for (JogoPlataforma jogoPlataforma : jogo.getJogoPlataformas()) {
                                    System.out.println("Plataforma: " + jogoPlataforma.getPlataforma().getNome());
                                    System.out.println("Preço Diário: " + jogoPlataforma.getPrecoDiario());
                                }
                                System.out.println("--------------------------------------");
                            }

                            System.out.println("Informe a plataforma desejada: ");
                            String nomePlataforma = scanner.nextLine();

                            JogoPlataforma jogoPlataforma = jogoPlataformaRepository.obterJogoPlataforma(nomeJogo, nomePlataforma);

                            if (jogoPlataforma != null) {
                                LocalDate dataDesejada = LocalDate.now();
                                boolean jogoLocado = locacaoRepository.isJogoLocadoEmData(jogoPlataforma, dataDesejada);
                                System.out.println();
                                if (jogoLocado) {
                                    System.out.println("O jogo está locado na data informada.");
                                } else {
                                    System.out.println("O jogo não está locado na data informada.");

                                    // Cadastrar a locação
                                    System.out.println("Informe o email do cliente: ");
                                    String email2 = scanner.nextLine();

                                    cliente2 = clienteRepository.obterClientePorEmail(email2);
                                    if (cliente2 == null) {
                                        System.out.println("Cliente não encontrado.");
                                        return;
                                    }

                                    System.out.println("Informe a quantidade de dias para a locação: ");
                                    int quantidadeDias = scanner.nextInt();
                                    scanner.nextLine(); // Consumir a quebra de linha

                                    // Criar o item de locação
                                    ItemLocacao itemLocacao = new ItemLocacao();
                                    itemLocacao.setCalculadoraPrecoLocacao(new CalculadoraPrecoLocacaoPorTempo());
                                    precoLocacao = itemLocacao.calcularPrecoLocacao(jogoPlataforma, quantidadeDias);

                                    itemLocacao.setJogoPlataforma(jogoPlataforma);
                                    itemLocacao.setDias(quantidadeDias);
                                    itemLocacao.setQuantidade(1); // Pode ser ajustado para permitir mais de uma cópia do jogo
                                    itemLocacao.setValorLocacaoJogo(precoLocacao);
                                    itensLocacao.add(itemLocacao);
                                }
                            } else {
                                System.out.println("Jogo e plataforma não encontrados.");
                            }

                            System.out.println("Deseja alugar mais algum jogo? (Sim/Não)");
                            respostaAlugarMaisJogos = scanner.nextLine();
                        }
                    }

                    if (!itensLocacao.isEmpty()) {
                        locacao.setCliente(cliente2);
                        locacao.setDataLocacao(LocalDate.now());
                        locacao.setItensLocacao(itensLocacao);

                        System.out.println("\nResumo da locação:");
                        System.out.println("Cliente: " + locacao.getCliente().getNome());
                        System.out.println("Data de Locação: " + locacao.getDataLocacao());
                        System.out.println("Itens de Locação: ");
                        for (ItemLocacao itemLocacao : locacao.getItensLocacao()) {
                            System.out.println("- Jogo: " + itemLocacao.getJogoPlataforma().getJogo().getTitulo() +
                                    " - Plataforma: " + itemLocacao.getJogoPlataforma().getPlataforma().getNome() +
                                    " - Valor: " + itemLocacao.getValorLocacaoJogo());
                        }

                        System.out.println("Confirma o cadastro da locação? (Sim/Não)");
                        String confirmacaoCadastro = scanner.nextLine();
                        if (confirmacaoCadastro.equalsIgnoreCase("sim")) {
                            locacaoRepository.salvar(locacao);
                            for (ItemLocacao itemLocacao : itensLocacao) {
                                itemLocacao.setLocacao(locacao);
                                itemLocacaoRepository.salvar(itemLocacao);
                                precoLocacao = precoLocacao.add(itemLocacao.getValorLocacaoJogo());
                            }

                            System.out.println("Locação cadastrada com sucesso!");
                            System.out.println("Preço Total: " + precoLocacao);
                        } else {
                            System.out.println("Cadastro da locação cancelado.");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Informe o console que deseja alugar: ");
                    String nomeConsole = scanner.nextLine();

                    List<Console> consoles = consoleRepository.buscarInformacoesJogoPorNome(nomeConsole);

                    if (consoles.isEmpty()) {
                        System.out.println("\nNenhum console encontrado com o nome informado.");
                        voltarMenu();
                    } else {
                        System.out.println();
                        for (Console console : consoles) {
                            System.out.println("TAG: " + console.getId());
                            System.out.println("Console: " + console.getNome());
                            System.out.println("Preço diária: " + console.getPrecoPorHora());
                            List<Acessorio> acessorios = console.getAcessorios();
                            for (Acessorio acessorio : acessorios) {
                                System.out.println("  - Acessório: " + acessorio.getNome());
                            }
                            System.out.println("-------------------------");
                        }

                        System.out.println("Selecione o TAG do console:");
                        Long tagConsole = scanner.nextLong();
                        System.out.println("Selecione o TAG do acessorio:");
                        Long tagAcessorio = scanner.nextLong();

                        Console consoleDisponivel = consoleRepository.verificarDisponibilidadeConsoleAcessorio(tagConsole, tagAcessorio);

                        if (consoleDisponivel == null) {
                            System.out.println("O console não está disponível para locação.");
                            voltarMenu();
                        } else {
                            // Cadastrar a locação
                            scanner.nextLine();
                            System.out.println("Informe o email do cliente: ");
                            String email3 = scanner.nextLine();

                            Cliente cliente3 = clienteRepository.obterClientePorEmail(email3);
                            if (cliente3 == null) {
                                System.out.println("Cliente não encontrado.");
                                return;
                            } else {
                                System.out.println("Informe a data de devolução: ");
                                String dataTerminoStr = scanner.next();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDateTime dataTermino = LocalDate.parse(dataTerminoStr, formatter).atStartOfDay();
                                scanner.nextLine();
                                System.out.println("Informe o tempo de uso (horas ou frações):");
                                String tempoUso = scanner.nextLine();

                                UtilizacaoDoConsolePeloCliente utilizacao = new UtilizacaoDoConsolePeloCliente();
                                utilizacao.setCliente(cliente3);
                                utilizacao.setConsole(consoleDisponivel);
                                utilizacao.setDataInicio(LocalDateTime.now().toLocalDate().atStartOfDay());
                                utilizacao.setDataFim(dataTermino); // Preencha com a data de término adequada

                                if (tempoUso.equalsIgnoreCase("horas")) {
                                    utilizacao.setCalculadoraValorLocacao(new CalculadoraValorLocacaoPorHora());
                                } else if (tempoUso.equalsIgnoreCase("frações")) {
                                    utilizacao.setCalculadoraValorLocacao(new CalculadoraValorLocacaoPorFracaoHora());
                                } else {
                                    System.out.println("Opção inválida. Tempo de uso não suportado.");
                                    return;
                                }

                                BigDecimal valorLocacao = utilizacao.calcularValorLocacao();
                                utilizacao.setValorLocacaoConsole(valorLocacao);
                                // Salve a utilização do console pelo cliente
                                utilizacaoDoConsolePeloClienteRepository.salvar(utilizacao);

                                System.out.println("\nLocação cadastrada com sucesso!");
                                System.out.println("Valor da locação: " + valorLocacao);


                                scanner.nextLine();
                                voltarMenu();
                            }
                        }
                    }
                    break;

                case 5:
                    /*List<JogoPlataforma> locacoesJogos = jogoPlataformaRepository.obterLocacoesRealizadasJogos();
                    RelatorioLocacao relatorioJogos = new RelatorioLocacaoJogo(locacoesJogos);
                    String relatorioJogosFormatado = relatorioJogos.gerarRelatorio();
                    System.out.println(relatorioJogosFormatado);*/

                    List<ItemLocacao> locacoesJogos = itemLocacaoRepository.obterLocacoesRealizadasJogos();
                    RelatorioLocacao relatorioJogos = new RelatorioLocacaoJogo(locacoesJogos);
                    String relatorioJogosFormatado = relatorioJogos.gerarRelatorio();
                    System.out.println(relatorioJogosFormatado);

                    List<UtilizacaoDoConsolePeloCliente> locacoesConsoles = utilizacaoDoConsolePeloClienteRepository.obterLocacoesRealizadasConsoles();
                    RelatorioLocacao relatorioConsoles = new RelatorioLocacaoConsole(locacoesConsoles);
                    String relatorioConsolesFormatado = relatorioConsoles.gerarRelatorio();
                    System.out.println(relatorioConsolesFormatado);
                    /*System.out.println("TAM:" +locacoesJogos.size());
                    for (JogoPlataforma jogoPlataforma : locacoesJogos) {*/

                        // Acesse os atributos ou métodos relevantes da locação de jogos
                        //System.out.println("Cliente: " + jogoPlataforma.getCliente().getNome());
                        //System.out.println("Data de Locação: " + locacao.getDataLocacao());

                        // Acesse outras informações relevantes da locação de jogos
                        /*for (ItemLocacao itemLocacao : jogoPlataforma.getItensLocacao()) {
                            System.out.println("Jogo: " + itemLocacao.getJogoPlataforma().getJogo().getTitulo());
                            System.out.println("Plataforma: " + itemLocacao.getJogoPlataforma().getPlataforma().getNome());
                            System.out.println("Preço Diária: " + itemLocacao.getJogoPlataforma().getPrecoDiario());
                            System.out.println("Dias: " + itemLocacao.getDias());
                            System.out.println("Quantidade: " + itemLocacao.getQuantidade());
                            System.out.println("Valor Locação: " + itemLocacao.getValorLocacaoJogo());
                            System.out.println("--------------------------------------");
                        }
                    }

                        System.out.println("--------------------------------------");*/
                    /*List<UtilizacaoDoConsolePeloCliente> utilizacoesConsoles = utilizacaoDoConsolePeloClienteRepository.obterUtilizacoesConsolesRealizadas();

                    RelatorioLocacao relatorioJogos = new RelatorioLocacaoJogo(locacoesJogos);
                    String relatorioJogosFormatado = relatorioJogos.gerarRelatorio();
                    System.out.println(relatorioJogosFormatado);

                    RelatorioLocacao relatorioConsoles = new RelatorioLocacaoConsole(utilizacoesConsoles);
                    String relatorioConsolesFormatado = relatorioConsoles.gerarRelatorio();
                    System.out.println(relatorioConsolesFormatado);*/

                    break;

                case 6:

                    /*Cliente cliente4 = new Cliente();
                    List<JogoPlataforma> jogosPlataformas = new ArrayList<>();
                    LocalDate dataReserva = null;
                    String respostaReservarMaisJogos = "sim";

                    System.out.println("Informe o email do cliente: ");
                    String email4 = scanner.nextLine();

                    cliente4 = clienteRepository.obterClientePorEmail(email4);
                    if (cliente4 == null) {
                        System.out.println("Cliente não encontrado.");
                        return;
                    }

                    do {
                        System.out.println("Informe o nome do jogo: ");
                        String nomeJogo = scanner.nextLine();

                        List<Jogo> jogos = jogoRepository.buscarInformacoesJogoPorNome(nomeJogo);

                        if (jogos.isEmpty()) {
                            System.out.println("\nNenhum jogo encontrado com o nome informado.");
                        } else {
                            System.out.println();
                            for (Jogo jogo : jogos) {
                                System.out.println("Título: " + jogo.getTitulo());
                                for (JogoPlataforma jogoPlataforma : jogo.getJogoPlataformas()) {
                                    System.out.println("Plataforma: " + jogoPlataforma.getPlataforma().getNome());
                                    System.out.println("Preço Diário: " + jogoPlataforma.getPrecoDiario());
                                }
                                System.out.println("--------------------------------------");
                            }

                            System.out.println("Informe a plataforma desejada: ");
                            String nomePlataforma = scanner.nextLine();

                            JogoPlataforma jogoPlataforma = jogoPlataformaRepository.obterJogoPlataforma(nomeJogo, nomePlataforma);

                            if (jogoPlataforma != null) {
                                LocalDate dataDesejada = LocalDate.now();
                                boolean jogoLocado = locacaoRepository.isJogoLocadoEmData(jogoPlataforma, dataDesejada);
                                System.out.println();
                                if (jogoLocado) {
                                    System.out.println("O jogo está locado na data informada.");
                                } else {
                                    System.out.println("O jogo não está locado na data informada.");
                                    System.out.println("Informe a data da reserva (formato: dd/MM/yyyy): ");
                                    String dataReservaStr = scanner.nextLine();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    dataReserva = LocalDate.parse(dataReservaStr, formatter);

                                    jogosPlataformas.add(jogoPlataforma);
                                }
                            } else {
                                System.out.println("Jogo e plataforma não encontrados.");
                            }
                        }

                        System.out.println("Deseja reservar mais algum jogo? (Sim/Não)");
                        respostaReservarMaisJogos = scanner.nextLine();
                    } while (respostaReservarMaisJogos.equalsIgnoreCase("sim"));

                    if (!jogosPlataformas.isEmpty()) {
                        Reserva reserva = new Reserva();
                        reserva.setCliente(cliente4);
                        reserva.setDataReserva(dataReserva);
                        reserva.setJogosPlataformas(jogosPlataformas);

                        reservaRepository.salvar(reserva);

                        System.out.println("\nResumo da reserva:");
                        System.out.println("Cliente: " + reserva.getCliente().getNome());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String dataReservaFormatada = reserva.getDataReserva().format(formatter);
                        System.out.println("Data da Reserva: " + dataReservaFormatada);
                        System.out.println("Jogos Reservados: ");
                        for (JogoPlataforma jogoPlataforma : reserva.getJogosPlataformas()) {
                            System.out.println("Título: " + jogoPlataforma.getJogo().getTitulo());
                            System.out.println("Plataforma: " + jogoPlataforma.getPlataforma().getNome());
                            System.out.println("Preço Diário: " + jogoPlataforma.getPrecoDiario());
                            System.out.println("--------------------------------------");
                        }
                    }*/
                    break;
                default:
                    break;
            }

        }while (opcao != 0);
    }
}
