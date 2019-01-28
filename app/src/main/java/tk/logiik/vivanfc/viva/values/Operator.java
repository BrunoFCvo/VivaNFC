package tk.logiik.vivanfc.viva.values;

import java.util.Map;
import java.util.TreeMap;

public class Operator {

    private String name;
    private Map<Integer, String> products;
    private Map<Integer, Line> lines;

    Operator(String name) {
        this.name = name;
        this.products = new TreeMap<>();
        this.lines = new TreeMap<>();
    }


    // name
    public String getName() {
        return name;
    }

    // products
    void addProduct(int productId, String productName) {
        products.put(productId, productName);
    }

    public String getProduct(int productId) {
        return products.get(productId);
    }

    // lines
    void addLine(int lineId, Line line) {
        lines.put(lineId, line);
    }

    public Line getLine(int lineId) {
        return lines.get(lineId);
    }

}
