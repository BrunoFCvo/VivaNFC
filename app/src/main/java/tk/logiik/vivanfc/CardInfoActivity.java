package tk.logiik.vivanfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import tk.logiik.vivanfc.viva.VivaCard;
import tk.logiik.vivanfc.viva.VivaLog;
import tk.logiik.vivanfc.viva.VivaValues;

public class CardInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        VivaValues vivaValues = VivaValues.singleton;
        VivaCard card = getIntent().getParcelableExtra(VivaNFC.CARD_PARCELABLE);

        TextView nameView = findViewById(R.id.card_holder_name);
        nameView.setText(card.getName());

        TextView holderBirthDateView = findViewById(R.id.card_holder_birthdate);
        Date holderBirthdate = card.getHolderBirthdate();
        holderBirthDateView.setText(formatDate(holderBirthdate));

        TextView vivaCardIdView = findViewById(R.id.card_viva_card_id);
        vivaCardIdView.setText(card.getVivaCardId());

        TextView issuerView = findViewById(R.id.card_issuer);
        issuerView.setText(vivaValues.getOperatorName(card.getIssuerId()));

        TextView issueDateView = findViewById(R.id.card_issue_date);
        issueDateView.setText(formatDate(card.getIssueDate()));

        TextView expirationDateView = findViewById(R.id.card_expiration_date);
        expirationDateView.setText(formatDate(card.getExpirationDate()));


        TableLayout logsContainerLayout = findViewById(R.id.logs_container);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        Iterator<VivaLog> logIterator = card.getLogIterator();
        int logId = 1;
        while(logIterator.hasNext()) {
            VivaLog log = logIterator.next();
            View logView = inflater.inflate(R.layout.view_log, logsContainerLayout, false);

            TextView logIdView = logView.findViewById(R.id.log_id);
            logIdView.setText(""+logId);

            TextView logDateView = logView.findViewById(R.id.log_date);
            logDateView.setText(formatDateTime(log.getDate()));

            TextView logTransitionView = logView.findViewById(R.id.log_transition);
            switch (log.getTransitionId()) {
                case VivaValues.TRANSITION_IN:
                    logTransitionView.setText(R.string.value_transition_in);
                    break;
                case VivaValues.TRANSITION_OUT:
                    logTransitionView.setText(R.string.value_transition_out);
                    break;
                default:
                    logTransitionView.setText(R.string.value_n_a);
            }

            TextView logOperatorView = logView.findViewById(R.id.log_operator);
            logOperatorView.setText(vivaValues.getOperatorName(log.getOperatorId()));

            logsContainerLayout.addView(logView);
            logId++;
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

        return String.format(Locale.getDefault(), "%02d:%02d:%02d %02d/%02d/%04d",
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
        );
    }

}
