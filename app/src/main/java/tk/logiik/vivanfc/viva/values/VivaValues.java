package tk.logiik.vivanfc.viva.values;

import java.util.Map;
import java.util.TreeMap;

public class VivaValues {

    public static final VivaValues singleton = new VivaValues();

    public static final int TRANSITION_IN   = 1;
    public static final int TRANSITION_OUT  = 4;

    public static final int PERIOD_DAYS     = 265;
    public static final int PERIOD_MONTHS   = 266;

    public static final int OPERATOR_CARRIS = 1;
    public static final int OPERATOR_ML = 2;
    //TODO add other operators as constants

    public static final String OPERATOR_MULTIPLE = "MULTIPLE_OP";

    private Map<Integer, Operator>  operators;
    private Map<Integer, String> pointsOfSale;

    public VivaValues() {
        operators = new TreeMap<>();

        setupCarris();
        setupMetroLisboa();
        setupCP();
        setupTranstejo();
        setupTST();
        setupRodoviariaLisboa();
        setupSoflusa();
        setupTCB();
        setupVimeca();
        setupBarraqueiro();
        setupFertagus();
        setupMTS();
        setupMultipleOperators();

        pointsOfSale = new TreeMap<>();
        pointsOfSale.put(1,   "Metro Lisboa");
        pointsOfSale.put(2,   "Metro Lisboa");
        pointsOfSale.put(3,   "Metro Lisboa");
        pointsOfSale.put(6,   "Rodoviária de Lisboa");
        pointsOfSale.put(8,   "Vimeca");                      // or Transtejo ???
        pointsOfSale.put(9,   "Transtejo");
        pointsOfSale.put(10,  "Fertagus");
        pointsOfSale.put(11,  "Metro Transportes do Sul");    // or CP ???
        pointsOfSale.put(12,  "CP");
        pointsOfSale.put(14,  "CP");
        pointsOfSale.put(23,  "Multibanco");
        pointsOfSale.put(24,  "Multibanco");
    }

    private void setupCarris() {
        Operator operator = new Operator("Carris");
        operators.put(OPERATOR_CARRIS, operator);
    }

    private void setupMetroLisboa() {
        Operator operator = new Operator("Metro Lisboa");

        Line blueLine = new Line("Blue");
        blueLine.addStation(1,      "Amadora-Este");
        blueLine.addStation(2,      "Alfornelos");
        blueLine.addStation(3,      "Pontinha");
        blueLine.addStation(4,      "Carnide");
        blueLine.addStation(5,      "Colégio Militar/Luz");
        blueLine.addStation(6,      "Alto dos Moinhos");
        blueLine.addStation(7,      "Laranjeiras");
        blueLine.addStation(8,      "Jardim Zoológico");
        blueLine.addStation(9,      "Praça de Espanha");
        blueLine.addStation(10,     "São Sebastião");
        blueLine.addStation(11,     "Parque");
        blueLine.addStation(12,     "Marquês de Pombal");
        blueLine.addStation(13,     "Avenida");
        blueLine.addStation(14,     "Restauradores");
        blueLine.addStation(15,     "Terreiro do Paço");
        blueLine.addStation(16,     "Santa Apolónia");
        operator.addLine(4097, blueLine);

        Line yellowLine = new Line("Yellow");
        yellowLine.addStation(1,    "Rato");
        yellowLine.addStation(2,    "Marquês de Pombal");
        yellowLine.addStation(3,    "Picoas");
        yellowLine.addStation(4,    "Saldanha");
        yellowLine.addStation(5,    "Campo Pequeno");
        yellowLine.addStation(6,    "Entrecampos");
        yellowLine.addStation(7,    "Cidade Universitária");
        yellowLine.addStation(8,    "Campo Grande");
        yellowLine.addStation(9,    "Quinta das Conchas");
        yellowLine.addStation(10,   "Lumiar");
        yellowLine.addStation(11,   "Ameixoeira");
        yellowLine.addStation(12,   "Senhor Roubado");
        yellowLine.addStation(13,   "Odivelas");
        operator.addLine(8194, yellowLine);

        Line greenLine = new Line("Green");
        greenLine.addStation(1,     "Cais do Sodré");
        greenLine.addStation(2,     "Baixa-Chiado");
        greenLine.addStation(3,     "Rossio");
        greenLine.addStation(4,     "Martim Moniz");
        greenLine.addStation(5,     "Intendente");
        greenLine.addStation(6,     "Anjos");
        greenLine.addStation(7,     "Arroios");
        greenLine.addStation(8,     "Alameda");
        greenLine.addStation(9,     "Areeiro");
        greenLine.addStation(10,    "Roma");
        greenLine.addStation(11,    "Alvalade");
        greenLine.addStation(12,    "Campo Grande");
        greenLine.addStation(13,    "Telheiras");
        operator.addLine(12291, greenLine);

        Line redLine = new Line("Red");
        redLine.addStation(1,       "São Sebastião");
        redLine.addStation(2,       "Saldanha");
        redLine.addStation(3,       "Alameda");
        redLine.addStation(4,       "Olaias");
        redLine.addStation(5,       "Bela Vista");
        redLine.addStation(6,       "Chelas");
        redLine.addStation(7,       "Olivais");
        redLine.addStation(8,       "Cabo Ruivo");
        redLine.addStation(9,       "Oriente");
        redLine.addStation(10,      "Moscavide");
        redLine.addStation(11,      "Encarnação");
        redLine.addStation(12,      "Aeroporto");
        operator.addLine(16388, redLine);

        operators.put(OPERATOR_ML, operator);
    }

    private void setupCP() {
        Operator operator = new Operator("CP");
        operators.put(3, operator);
    }

    private void setupTranstejo() {
        Operator operator = new Operator("Transtejo");
        operators.put(4, operator);
    }

    private void setupTST() {
        Operator operator = new Operator("Transportes Sul do Tejo");
        operators.put(5, operator);
    }

    private void setupRodoviariaLisboa() {
        Operator operator = new Operator("Rodoviária de Lisboa");
        operators.put(6, operator);
    }

    private void setupSoflusa() {
        Operator operator = new Operator("Soflusa");
        operators.put(7, operator);
    }

    private void setupTCB() {
        Operator operator = new Operator("Transportes Colectivos do Barreiro");
        operators.put(8, operator);
    }

    private void setupVimeca() {
        Operator operator = new Operator("Vimeca");
        operators.put(9, operator);
    }

    private void setupBarraqueiro() {
        Operator operator = new Operator("Barraqueiro");
        operators.put(13, operator);
    }

    private void setupFertagus() {
        Operator operator = new Operator("Fertagus");

        Line line = new Line("Lisboa - Setúbal");
        line.addStation(1,  "Fogueteiro");
        line.addStation(2,  "Foros de Amora");
        line.addStation(3,  "Corroios");
        line.addStation(4,  "Pragal");
        line.addStation(5,  "Campolide");
        line.addStation(6,  "Sete-Rios");
        line.addStation(7,  "Entrecampos");
        line.addStation(8,  "Roma-Areeiro");
        line.addStation(9,  "Coina");
        line.addStation(11, "Penalva");
        line.addStation(12, "Pinhal Novo");
        line.addStation(13, "Venda do Alcaide");
        line.addStation(14, "Palmela");
        line.addStation(15, "Setúbal");
        operator.addLine(0, line);

        operator.addProduct(6150,   "Fertagus FRA-PRA (4_18/Sub23)");

        operators.put(15, operator);
    }

    private void setupMTS() {
        Operator operator = new Operator("Metro Transportes do Sul");

        Line line1 = new Line("1");
        line1.addStation(1,     "Cacilhas");
        line1.addStation(2,     "25 de Abril");
        line1.addStation(3,     "Gil Vicente");
        line1.addStation(4,     "S. João Baptista");
        line1.addStation(5,     "Almada");
        line1.addStation(6,     "Bento Gonçalves");
        line1.addStation(7,     "Cova da Piedade");
        line1.addStation(8,     "Parque da Paz");
        line1.addStation(9,     "António Gedeão");
        line1.addStation(10,    "Laranjeiro");
        line1.addStation(11,    "Santo Amaro");
        line1.addStation(12,    "Casa do Povo");
        line1.addStation(13,    "Corroios");
        operator.addLine(1, line1);

        Line line2 = new Line("2");
        line2.addStation(16,    "Pragal");
        line2.addStation(15,    "Ramalha");
        line2.addStation(7,     "Cova da Piedade");
        line2.addStation(8,     "Parque da Paz");
        line2.addStation(9,     "António Gedeão");
        line2.addStation(10,    "Laranjeiro");
        line2.addStation(11,    "Santo Amaro");
        line2.addStation(12,    "Casa do Povo");
        line2.addStation(13,    "Corroios");
        operator.addLine(2, line2);

        Line line3 = new Line("3");
        line3.addStation(1,     "Cacilhas");
        line3.addStation(2,     "25 de Abril");
        line3.addStation(3,     "Gil Vicente");
        line3.addStation(4,     "S. João Baptista");
        line3.addStation(5,     "Almada");
        line3.addStation(6,     "Bento Gonçalves");
        line3.addStation(15,    "Ramalha");
        line3.addStation(16,    "Pragal");
        line3.addStation(17,    "Boa Esperança");
        line3.addStation(18,    "Fomega");
        line3.addStation(19,    "Monte de Caparica");
        line3.addStation(20,    "Universidade");
        operator.addLine(3, line3);

        operator.addProduct(5,       "Complemento MTS");
        operator.addProduct(6100,    "Passe MTS (4_18/Sub23)");
        operator.addProduct(6101,    "Complemento MTS (4_18/Sub23)");

        operators.put(16, operator);
    }

    private void setupMultipleOperators() {
        Operator operator = new Operator(OPERATOR_MULTIPLE);

        operator.addProduct(1088,   "Fertagus PAL-LIS + Metro Lisboa");
        operator.addProduct(1116,   "Fertagus COR-LIS + Metro Lisboa");
        operator.addProduct(6352,   "Fertagus FRA-LIS + Metro Lisboa (4_18/Sub23)");
        operator.addProduct(6353,   "Fertagus COR-LIS + Metro Lisboa (4_18/Sub23)");
        operator.addProduct(6382,   "Fertagus FRA-LIS + CP (4_18/Sub23)");

        operators.put(30, operator);
        operators.put(31, operator);
    }


    public Operator getOperator(int operatorId) {
        return operators.get(operatorId);
    }

    public String getPointOfSale(int pointOfSaleId) {
        return pointsOfSale.get(pointOfSaleId);
    }

}
