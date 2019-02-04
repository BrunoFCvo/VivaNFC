package tk.logiik.vivanfc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import tk.logiik.vivanfc.apdu.APDUOperationFailedException;
import tk.logiik.vivanfc.viva.VivaCard;
import tk.logiik.vivanfc.viva.VivaInterface;

public class VivaNFC extends AppCompatActivity {

    public static final String CARD_PARCELABLE = "CURRENT_CARD";
    private static final String FILE_CARD_LIST = "SAVED_CARDS_LIST";

    private LayoutInflater inflater;
    private TableLayout cardListView;
    private TextView textView;

    private NfcAdapter nfcAdapter;
    private PendingIntent nfcPendingIntent;

    private Map<String, VivaCard> savedCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = LayoutInflater.from(getApplicationContext());
        cardListView = findViewById(R.id.card_list_view);
        textView = findViewById(R.id.main_text_view);

        // Check if the device supports nfc
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Get card objects from storage
        try {
            InputStream is = openFileInput(FILE_CARD_LIST);
            ObjectInputStream ois = new ObjectInputStream(is);
            savedCards = (Map<String, VivaCard>) ois.readObject();
            ois.close();
            is.close();
        } catch (FileNotFoundException e) {
            savedCards = new TreeMap<>();
        } catch (Exception e) {
            savedCards = new TreeMap<>();
            Toast.makeText(this, "Couldn't load card list.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        // Check if nfc is enabled
        if (!nfcAdapter.isEnabled()) {
            textView.setText("NFC is disabled.");
            return;
        }

        textView.setText("Ready");

        // Setup pending intent
        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        nfcPendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);

        // Intent listener
        onNewIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, null, null);
        }

        appendAllCards();
    }

    @Override
    protected void onPause() {
        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        try {
            OutputStream os = openFileOutput(FILE_CARD_LIST, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(savedCards);
            oos.close();
            os.close();
        } catch (Exception e) {
            Toast.makeText(this, "Couldn't save card list.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    protected void handleIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            IsoDep isoDep = IsoDep.get(tag);

            try {

                // Save card
                VivaCard card = VivaInterface.fetchData(isoDep);
                savedCards.put(card.getVivaCardId(), card);

            } catch (APDUOperationFailedException e) {
                textView.setText(e.getMessage());
                e.printStackTrace();
            } catch(Exception e) {
                textView.setText("Contents could not be read!");
                e.printStackTrace();
            }
        }
    }

    private void appendCard(final VivaCard card) {
        View cardView = inflater.inflate(R.layout.view_card, cardListView, false);

        TextView vivaIdView = cardView.findViewById(R.id.viva_id);
        vivaIdView.setText(String.valueOf(card.getVivaCardId()));

        TextView holderNameView = cardView.findViewById(R.id.holder_name);
        holderNameView.setText(card.getName());

        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(VivaNFC.this, CardInfoActivity.class);
                activityIntent.putExtra(CARD_PARCELABLE, (Parcelable) card);
                startActivity(activityIntent);
            }

        });

        cardListView.addView(cardView);
    }

    private void appendAllCards() {
        cardListView.removeAllViews();

        Iterator<Map.Entry<String, VivaCard>> it = savedCards.entrySet().iterator();
        while (it.hasNext()) {
            VivaCard card = it.next().getValue();
            appendCard(card);
        }
    }

}
