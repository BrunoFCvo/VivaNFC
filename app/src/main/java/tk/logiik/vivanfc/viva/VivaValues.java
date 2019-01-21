package tk.logiik.vivanfc.viva;

import java.util.Map;
import java.util.TreeMap;

public class VivaValues {

    public static final VivaValues singleton = new VivaValues();

    public static final int TRANSITION_IN   = 1;
    public static final int TRANSITION_OUT  = 4;

    private Map<Integer, Operator>  operators;

    public VivaValues() {

        // OPERATORS

        operators = new TreeMap<>();

        Map<Integer, String> carrisProducts = new TreeMap<>();
        Operator carris = new Operator("Carris", carrisProducts);
        operators.put(1, carris);

        Map<Integer, String> mlProducts = new TreeMap<>();
        Operator ml = new Operator("Metro Lisboa", mlProducts);
        operators.put(2, ml);

        Map<Integer, String> cpProducts = new TreeMap<>();
        Operator cp = new Operator("CP", cpProducts);
        operators.put(3, cp);

        Map<Integer, String> transtejoProducts = new TreeMap<>();
        Operator transtejo = new Operator("Transtejo", transtejoProducts);
        operators.put(4, transtejo);

        Map<Integer, String> tstProducts = new TreeMap<>();
        Operator tst = new Operator("Transportes Sul do Tejo", tstProducts);
        operators.put(5, tst);

        Map<Integer, String> rlProducts = new TreeMap<>();
        Operator rl = new Operator("Rodoviária de Lisboa", rlProducts);
        operators.put(6, rl);

        Map<Integer, String> soflusaProducts = new TreeMap<>();
        Operator soflusa = new Operator("Soflusa", soflusaProducts);
        operators.put(7, soflusa);

        Map<Integer, String> tcbProducts = new TreeMap<>();
        Operator tcb = new Operator("Transportes Colectivos do Barreiro", tcbProducts);
        operators.put(8, tcb);

        Map<Integer, String> vimecaProducts = new TreeMap<>();
        Operator vimeca = new Operator("Vimeca", vimecaProducts);
        operators.put(9, vimeca);

        Map<Integer, String> barraqueiroProducts = new TreeMap<>();
        Operator barraqueiro = new Operator("Barraqueiro", barraqueiroProducts);
        operators.put(13, barraqueiro);

        Map<Integer, String> fertagusProducts = new TreeMap<>();
        fertagusProducts.put(6150,  "Fertagus FRA-PRA (4_18/Sub23)");
        Operator fertagus = new Operator("Fertagus", fertagusProducts);
        operators.put(15, fertagus);

        Map<Integer, String> mtsProducts = new TreeMap<>();
        mtsProducts.put(5,      "Metro Transportes do Sul - Complemento");
        mtsProducts.put(6100,   "Metro Transportes do Sul (4_18/Sub23)");
        mtsProducts.put(6101,   "Metro Transportes do Sul - Complemento (4_18/Sub23)");
        Operator mts = new Operator("Metro Transportes do Sul", mtsProducts);
        operators.put(16, mts);

        Map<Integer, String> multipleProducts = new TreeMap<>();
        multipleProducts.put(1088,  "Fertagus PAL-LIS + Metro Lisboa");
        multipleProducts.put(1116,  "Fertagus COR-LIS + Metro Lisboa");
        multipleProducts.put(6352,  "Fertagus FRA-LIS + Metro Lisboa (4_18/Sub23)");
        multipleProducts.put(6353,  "Fertagus COR-LIS + Metro Lisboa (4_18/Sub23)");
        multipleProducts.put(6382,  "Fertagus FRA-LIS + CP (4_18/Sub23)");
        Operator multiple = new Operator(null, multipleProducts);
        operators.put(30, multiple);
        operators.put(31, multiple);

    }

    public String getOperatorName(int id) {
        Operator operator = operators.get(id);
        if(operator == null) {
            return null;
        }

        return operator.getName();
    }

    private class Operator {

        private String name;
        private Map<Integer, String> products;

        public Operator(String name, Map<Integer, String> products) {
            this.name = name;
            this.products = products;
        }

        public String getName() {
            return name;
        }

        public Map<Integer, String> getProducts() {
            return products;
        }

    }

}
