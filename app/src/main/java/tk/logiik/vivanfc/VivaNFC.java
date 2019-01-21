package tk.logiik.vivanfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import tk.logiik.vivanfc.apdu.APDUOperationFailedException;
import tk.logiik.vivanfc.viva.VivaCard;
import tk.logiik.vivanfc.viva.VivaInterface;

public class VivaNFC extends AppCompatActivity {

    public static final String CARD_PARCELABLE = "CURRENT_CARD";

    private TextView textView;

    private NfcAdapter nfcAdapter;
    private PendingIntent nfcPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.main_text_view);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Check if the device supports nfc
        if (nfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
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
    }

    @Override
    protected void onPause() {
        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
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

                VivaCard card = VivaInterface.fetchData(isoDep);

                Intent activityIntent = new Intent(this, CardInfoActivity.class);
                activityIntent.putExtra(CARD_PARCELABLE, card);
                startActivity(activityIntent);

            } catch (APDUOperationFailedException e) {
                textView.setText(e.getMessage());
                e.printStackTrace();
            } catch(Exception e) {
                textView.setText("Contents could not be read!");
                e.printStackTrace();
            }
        }
    }

}
