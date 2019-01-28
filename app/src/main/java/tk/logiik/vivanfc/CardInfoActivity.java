package tk.logiik.vivanfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import tk.logiik.vivanfc.viva.VivaCard;
import tk.logiik.vivanfc.viva.VivaContract;
import tk.logiik.vivanfc.viva.VivaLog;
import tk.logiik.vivanfc.viva.values.Line;
import tk.logiik.vivanfc.viva.values.Operator;
import tk.logiik.vivanfc.viva.values.Station;
import tk.logiik.vivanfc.viva.values.VivaValues;

public class CardInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        VivaValues vivaValues = VivaValues.singleton;
        VivaCard card = getIntent().getParcelableExtra(VivaNFC.CARD_PARCELABLE);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        // name
        TextView nameView = findViewById(R.id.card_holder_name);
        nameView.setText(card.getName());

        // birthdate
        TextView holderBirthDateView = findViewById(R.id.card_holder_birthdate);
        Date holderBirthdate = card.getBirthDate();
        holderBirthDateView.setText(formatDate(holderBirthdate));

        // viva card id
        TextView vivaCardIdView = findViewById(R.id.card_viva_card_id);
        vivaCardIdView.setText(card.getVivaCardId());

        // issuer
        TextView issuerView = findViewById(R.id.card_issuer);
        int issuerId = card.getIssuerId();
        Operator issuer = vivaValues.getOperator(issuerId);
        issuerView.setText(formatOperator(issuerId, issuer));

        // issue date
        TextView issueDateView = findViewById(R.id.card_issue_date);
        issueDateView.setText(formatDate(card.getIssueDate()));

        // expiration date
        TextView expirationDateView = findViewById(R.id.card_expiration_date);
        expirationDateView.setText(formatDate(card.getExpirationDate()));

        // logs
        TableLayout logsContainerLayout = findViewById(R.id.logs_container);

        Iterator<VivaLog> logIterator = card.getLogIterator();
        while (logIterator.hasNext()) {
            VivaLog log = logIterator.next();
            View logView = inflater.inflate(R.layout.view_log, logsContainerLayout, false);

            // date
            TextView logDateView = logView.findViewById(R.id.log_date);
            logDateView.setText(formatDateTime(log.getDate()));

            // contract id
            TextView logContractView = logView.findViewById(R.id.logs_contract);
            logContractView.setText(String.valueOf(log.getContractId()));

            // transition
            TextView logTransitionView = logView.findViewById(R.id.log_transition);
            logTransitionView.setText(formatTransition(log.getTransitionId()));

            // operator
            TextView logOperatorView = logView.findViewById(R.id.log_operator);
            int operatorId = log.getOperatorId();
            Operator operator = vivaValues.getOperator(operatorId);
            logOperatorView.setText(formatOperator(operatorId, operator));

            // reader id
            TextView readerIdView = logView.findViewById(R.id.log_reader_id);
            readerIdView.setText(String.valueOf(log.getReaderId()));

            // line
            TextView logLineView = logView.findViewById(R.id.log_line);
            int lineId = log.getLineId();
            Line line = operator != null ? operator.getLine(lineId) : null;
            logLineView.setText(formatLine(lineId, line));

            // station
            TextView logStationView = logView.findViewById(R.id.log_station);
            int stationId = log.getStationId();
            Station station = line != null ? line.getStation(stationId) : null;
            logStationView.setText(formatStation(stationId, station));

            logsContainerLayout.addView(logView);
        }

        // contracts
        TableLayout contractsContainerLayout = findViewById(R.id.contracts_container);

        Iterator<VivaContract> contractIterator = card.getContractIterator();
        while (contractIterator.hasNext()) {
            VivaContract contract = contractIterator.next();
            View contractView = inflater.inflate(R.layout.view_contract, contractsContainerLayout, false);

            // operator
            TextView contractOperatorView = contractView.findViewById(R.id.contract_operator);
            int operatorId = contract.getOperatorId();
            Operator operator = vivaValues.getOperator(operatorId);
            contractOperatorView.setText(formatOperator(operatorId, operator));

            // product
            TextView contractProductView = contractView.findViewById(R.id.contract_product);
            int productId = contract.getProductId();
            contractProductView.setText(formatProduct(productId, operator));

            // start date
            TextView contractStartDateView = contractView.findViewById(R.id.contract_start_date);
            contractStartDateView.setText(formatDate(contract.getStartDate()));

            // point of sale
            TextView contractPointOfSaleView = contractView.findViewById(R.id.contract_point_of_sale);
            contractPointOfSaleView.setText(formatPointOfSale(contract.getPointOfSaleId()));

            // end date
            TextView contractEndDateView = contractView.findViewById(R.id.contract_end_date);
            contractEndDateView.setText(formatDate(contract.getEndDate()));

            contractsContainerLayout.addView(contractView);
        }
    }

    private String formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return String.format(Locale.getDefault(), "%02d/%02d/%04d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
        );
    }

    private String formatDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return String.format(Locale.getDefault(), "%02d/%02d/%04d - %02d:%02d:%02d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
        );
    }

    private String formatTransition(int transitionId) {
        switch (transitionId) {
            case VivaValues.TRANSITION_IN:
                return getString(R.string.value_transition_in);
            case VivaValues.TRANSITION_OUT:
                return getString(R.string.value_transition_out);
            default:
                return getString(R.string.value_n_a);
        }
    }

    private String formatOperator(int operatorId, Operator operator) {
        if (operator == null) {
            return formatUnknown(operatorId);
        }

        String name = operator.getName();
        if (name.equals(VivaValues.OPERATOR_MULTIPLE)) {
            name = getString(R.string.value_multiple_operators);
        }

        return name;
    }

    private String formatProduct(int productId, Operator operator) {
        if (operator == null) {
            return formatUnknown(productId);
        }

        String name = operator.getProduct(productId);
        if (name == null) {
            return formatUnknown(productId);
        }

        return name;
    }

    private String formatLine(int lineId, Line line) {
        if (line == null) {
            return formatUnknown(lineId);
        }

        return line.getName();
    }

    private String formatStation(int stationId, Station station) {
        if (station == null) {
            return formatUnknown(stationId);
        }

        return station.getName();
    }

    private String formatPointOfSale(int pointOfSaleId) {
        String res = VivaValues.singleton.getPointOfSale(pointOfSaleId);
        if (res == null) {
            return formatUnknown(pointOfSaleId);
        }

        return res;
    }

    private String formatUnknown(int unknownId) {
        return String.format(getString(R.string.value_unknown_id), unknownId);
    }

}
